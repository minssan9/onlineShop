package com.minssan9.shop.qnas;

import com.minssan9.shop.accounts.Account;
import com.minssan9.shop.accounts.AccountAdapter;
import com.minssan9.shop.accounts.AccountRoles;
import com.minssan9.shop.items.Item;
import com.minssan9.shop.items.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;


@RestController
@RequestMapping("/qna")
public class QnAController {

    @Autowired
    QnARepository qnARepository;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping
    @Description("관리자 - 전체 QnA 페이징")
    public ResponseEntity getManagerQnA(@AuthenticationPrincipal AccountAdapter account, Pageable pageable) {

        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (!account.getAccount().getRoles().contains(AccountRoles.ADMIN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity(qnARepository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    @Description("해당 아이템의 QnA를 페이징")
    public ResponseEntity getQnA(@PathVariable Long itemId, Pageable pageable) {

        return new ResponseEntity(qnARepository.findByItem_Id(itemId, pageable), HttpStatus.OK);
    }

    @PostMapping("/{itemId}")
    @Description("해당 아이템에 QnA 작성하기")
    public void addQnA(@AuthenticationPrincipal AccountAdapter account, @PathVariable Long itemId, @RequestBody QnADto qnADto) {

        Item Item = itemRepository.findById(itemId).get();

        QnA qna = QnA.builder()
                .writer(account.getAccount())
                .title(qnADto.getTitle())
                .content(qnADto.getContent())
                .qnaCreated(LocalDateTime.now())
                .item(Item)
                .build();

        qnARepository.save(qna);
    }

    @PutMapping("/{itemId}")
    @Description("관리자 - 답변달기")
    public ResponseEntity updateAnswer(@AuthenticationPrincipal AccountAdapter account, @PathVariable Long itemId, @RequestBody QnADto qnaDto) {

        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if (!account.getAccount().getRoles().contains(AccountRoles.ADMIN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        QnA qna = qnARepository.findById(itemId).get();

        if (qna != null) {
            qna.setAnswer(qnaDto.getContent());
            qna.setStatus(1);      // 답변완료

            qnARepository.save(qna);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/{qnaId}")
    public ResponseEntity deleteQnA(@AuthenticationPrincipal AccountAdapter account, @PathVariable Long qnaId) {

        Optional<QnA> optionalQnA = qnARepository.findById(qnaId);

        if (!optionalQnA.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (!account.getAccount().getRoles().contains(AccountRoles.ADMIN)) {         // 관리자가아니면
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        QnA qna = optionalQnA.orElseThrow(() -> new EntityNotFoundException(qnaId + ""));

        qnARepository.delete(qna);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/manager/{option}/{keyword}")
    public ResponseEntity getManagerSearchQnA(@AuthenticationPrincipal AccountAdapter account, @PathVariable int option, @PathVariable String keyword, Pageable pageable) {

        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (!account.getAccount().getRoles().contains(AccountRoles.ADMIN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Page<QnA> pages;

        if (option == 0) {  // 상품명
            pages = qnARepository.findByItem_Title(keyword, pageable);
        } else if (option == 1) {    // 제목
            pages = qnARepository.findByTitleContains(keyword, pageable);
        } else if (option == 2) {    // 작성자
            pages = qnARepository.findByWriter(keyword, pageable);
        } else if (option == 3) {    // 답변상태

            String arr[] = {"답변대기", "답변완료"};

            int status = Arrays.asList(arr).indexOf(keyword);

            if (status == -1)
                return new ResponseEntity(HttpStatus.NO_CONTENT);

            pages = qnARepository.findByStatus(status, pageable);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        if (pages.getTotalElements() == 0)
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity(pages, HttpStatus.OK);
    }
}


