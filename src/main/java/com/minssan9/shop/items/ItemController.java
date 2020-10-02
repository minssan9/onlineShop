package com.minssan9.shop.items;

import com.minssan9.shop.accounts.AccountAdapter;
import com.minssan9.shop.accounts.AccountRoles;
import com.minssan9.shop.qnas.QnARepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemFileRepository itemFileRepository;

    @Autowired
    QnARepository qnARepository;

    @Autowired
    ItemOptionRepository itemOptionRepository;

    @GetMapping("/{id}")
    public ResponseEntity getItem(@PathVariable Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);

        if (!itemOptional.isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Item item = itemOptional.orElseThrow(() -> new UsernameNotFoundException(id + ""));

        return new ResponseEntity(item, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getItems(Pageable pageable) {
        return new ResponseEntity(itemRepository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity getItems(Pageable pageable, @PathVariable String keyword) {
        Page<Item> items = itemRepository.findByTitleContains(keyword, pageable);
        return new ResponseEntity(items, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addItem(@RequestBody Item item) {

        item.setSavings((int) ((item.getPrice() - item.getDiscount()) * 0.10));
        item.setItemCreated(LocalDateTime.now());

        Item newItem = itemRepository.save(item);

        for (ItemOption option : item.getOptions()) {
            ItemOption itemOption = ItemOption.builder()
                    .options(option.getOptions())
                    .item(newItem)
                    .build();

            itemOptionRepository.save(itemOption);
        }

        for (int i = 0; i < item.getItemFileList().size(); i++) {
            ItemFile itemFile = item.getItemFileList().get(i);
            itemFile.setItem(newItem);
            itemFileRepository.save(itemFile);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateItem(@PathVariable Long id, @RequestBody Item newItem) {
        Item item = itemRepository.findById(id).get();

        item.setContent(newItem.getContent());
        item.setPrice(newItem.getPrice());
        item.setDiscount(newItem.getDiscount());
        item.setDeliveryDate(newItem.getDeliveryDate());
        item.setContent(newItem.getContent());
        item.setSavings((int) ((newItem.getPrice() - newItem.getDiscount()) * 0.10));
        item.setTitle(newItem.getTitle());
        item.setItemCreated(LocalDateTime.now());
        // 파일리스트 수정하기

        itemRepository.save(item);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@AuthenticationPrincipal AccountAdapter account, @PathVariable Long id) {

        if (!account.getAccount().getRoles().contains(AccountRoles.ADMIN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Optional<Item> optionalItem = itemRepository.findById(id);

        if (!optionalItem.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Item item = optionalItem.orElseThrow(() -> new UsernameNotFoundException(id + ""));

        // 파일삭제
        for (ItemFile itemFile : item.getItemFileList()) {

            String path = itemFile.getUploadPath() + "\\" + itemFile.getUuid() + "_" + itemFile.getFileName();

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

        itemRepository.delete(item);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/files")
    public ResponseEntity addFiles(@RequestBody MultipartFile[] uploadFile) {

        List<ItemFile> itemFileList = new ArrayList<>();

        String uploadFolder = "C:\\Users\\kim\\git\\Market\\project\\src\\main\\resources\\templates\\uploads";


        for (MultipartFile multipartFile : uploadFile) {

            ItemFile itemFile = new ItemFile();

            String uploadFileName = multipartFile.getOriginalFilename();
            itemFile.setFileName(uploadFileName);            // 오리지날 이름저장

            UUID uuid = UUID.randomUUID();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            uploadFileName = uuid.toString() + "_" + uploadFileName;

            java.io.File saveFile = new java.io.File(uploadFolder, uploadFileName);

            itemFile.setUuid(uuid.toString());                // uuid 저장
            itemFile.setUploadPath(uploadFolder);             // 경로

            try {
                multipartFile.transferTo(saveFile);
                itemFileList.add(itemFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity(itemFileList, HttpStatus.OK);
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(String fileName) {

        Resource resource = new FileSystemResource("C:\\Users\\kim\\git\\Market\\project\\src\\main\\resources\\templates\\uploads\\" + fileName);

        return new ResponseEntity(resource, HttpStatus.OK);
    }


}
