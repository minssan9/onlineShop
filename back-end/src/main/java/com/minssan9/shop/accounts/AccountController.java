package com.minssan9.shop.accounts;

import com.minssan9.shop.chats.Chat;
import com.minssan9.shop.chats.ChatRepository;
import com.minssan9.shop.items.ItemFile;
import com.minssan9.shop.items.ItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountFileRepository accountFileRepository;

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    AccountService service;

    @Autowired
    ItemRepository itemRepository;

    @PostMapping("/join/check")
    @Description("회원가입 아이디 중복여부 확인")
    public ResponseEntity accountDoubleCheck(@RequestBody AccountDto accountDto) {

        Optional<Account> optionalAccount = accountRepository.findByAccountId(accountDto.getAccountId());

        if (optionalAccount.isPresent()) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    @Description("로그인 사용자 정보가져오기")
    public ResponseEntity<Account> getAccount(@AuthenticationPrincipal AccountAdapter adapter) {

        if (adapter == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Optional<Account> optionalAccount = accountRepository.findByAccountId(adapter.getAccount().getAccountId());
        Account account = optionalAccount.get();

        return ResponseEntity.ok().body(account);
    }

    @GetMapping("/manager")
    @Description("관리자기능 - 모든 회원정보 조회")
    public ResponseEntity getManagerAccounts(@AuthenticationPrincipal AccountAdapter adapter, Pageable pageable) {

        if (adapter == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (!adapter.getAccount().getRoles().contains(AccountRoles.ADMIN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Page<Account> pages = accountRepository.findAll(pageable);

        return new ResponseEntity(pages, HttpStatus.OK);
    }

    @GetMapping("/manager/{option}/{keyword}")
    @Description("관리자기능 - 유저 검색기능")
    public ResponseEntity getManagerSearchAccounts(@AuthenticationPrincipal AccountAdapter adapter, @PathVariable int option, @PathVariable String keyword, Pageable pageable) {

        if (adapter == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (!adapter.getAccount().getRoles().contains(AccountRoles.ADMIN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Page<Account> pages;

        if (option == 0) {  // 아이디
            pages = accountRepository.findByAccountId(keyword, pageable);
        } else if (option == 1) {    // 이름
            pages = accountRepository.findByName(keyword, pageable);
        } else if (option == 2) {    // 이메일
            pages = accountRepository.findByAddress(keyword, pageable);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        if (pages.getTotalElements() == 0)
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity(pages, HttpStatus.OK);
    }

    @PostMapping
    @Description("회원가입")
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDto) {

        Optional<Account> byAccountId = accountRepository.findByAccountId(accountDto.getAccountId());

        // 이미 존재하는 아이디
        if (byAccountId.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account account = Account.builder()
                .accountId(accountDto.getAccountId())
                .password(accountDto.getPassword())
                .address(accountDto.getAddress())
                .name(accountDto.getName())
                .phone(accountDto.getPhone())
                .email(accountDto.getEmail())
                .build();

        Account savedAccount = service.createAccount(account);

        Chat chat1 = Chat.builder()
                .account(savedAccount)
                .build();

        chatRepository.save(chat1);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Description("회원정보 수정")
    public ResponseEntity<Account> updateAccount(@AuthenticationPrincipal AccountAdapter adapter, @RequestBody AccountDto accountDto) {

        if (adapter == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        adapter.getAccount().setName(accountDto.getName());
        adapter.getAccount().setPassword(accountDto.getPassword());
        adapter.getAccount().setAddress(accountDto.getAddress());
        adapter.getAccount().setName(accountDto.getName());
        adapter.getAccount().setEmail(accountDto.getEmail());
        adapter.getAccount().setPhone(accountDto.getPhone());

        service.createAccount(adapter.getAccount());

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/files/{id}")
    @Description("계정 이미지파일 추가")
    public ResponseEntity addFiles(@PathVariable Long id, @RequestBody MultipartFile[] uploadFile) {

        System.out.println("check:" + id + " " + Arrays.toString(uploadFile));
        Optional<Account> accountOptional = accountRepository.findById(id);
        Account account = accountOptional.orElseThrow(() -> new EntityNotFoundException());

        String uploadFolder = "C:\\Users\\kim\\git\\Market\\project\\src\\main\\resources\\templates\\uploads";

        for (MultipartFile multipartFile : uploadFile) {

            AccountFile accountFile = new AccountFile();

            String uploadFileName = multipartFile.getOriginalFilename();
            accountFile.setFileName(uploadFileName);            // 오리지날 이름저장

            UUID uuid = UUID.randomUUID();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            uploadFileName = uuid.toString() + "_" + uploadFileName;

            java.io.File saveFile = new java.io.File(uploadFolder, uploadFileName);

            accountFile.setUuid(uuid.toString());                // uuid 저장
            accountFile.setUploadPath(uploadFolder);             // 경로
            accountFile.setAccount(account);

            try {
                multipartFile.transferTo(saveFile);
                List<AccountFile> accountFiles = accountFileRepository.findAll();
                if (accountFiles != null) {
                    // 파일삭제
                    for (AccountFile deleteFile : accountFiles) {

                        String path = deleteFile.getUploadPath() + "\\" + deleteFile.getUuid() + "_" + deleteFile.getFileName();

                        File file = new File(path);
                        if (file.exists()) {
                            if (file.delete()) {
                                System.out.println("파일삭제 성공");
                            } else {
                                System.out.println("파일삭제 실패");
                            }
                        } else {
                            System.out.println("파일이 존재하지 않습니다.");
                        }
                    }
                }
                accountFileRepository.deleteAll();
                AccountFile save = accountFileRepository.save(accountFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/download")
    @Description("계정 이미지파일 다운로드")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(String fileName) {

        Resource resource = new FileSystemResource("C:\\Users\\kim\\git\\Market\\project\\src\\main\\resources\\templates\\uploads\\" + fileName);

        return new ResponseEntity(resource, HttpStatus.OK);
    }

}

