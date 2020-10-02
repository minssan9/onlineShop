package com.minssan9.shop.reviews;

import com.minssan9.shop.accounts.AccountAdapter;
import com.minssan9.shop.accounts.AccountRepository;
import com.minssan9.shop.accounts.AccountRoles;
import com.minssan9.shop.items.Item;
import com.minssan9.shop.items.ItemRepository;
import com.minssan9.shop.qnas.QnA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ReviewFileRepository reviewFileRepository;

    @GetMapping
    public ResponseEntity getManagerReviews(Pageable pageable) {

        return new ResponseEntity(reviewRepository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity getReviews(@PathVariable Long itemId, Pageable pageable) {

        return new ResponseEntity(reviewRepository.findByItem_Id(itemId, pageable), HttpStatus.OK);
    }

    @PostMapping("/{itemId}")
    public ResponseEntity addReview(@AuthenticationPrincipal AccountAdapter account, @PathVariable Long itemId, @RequestBody ReviewDto reviewDto) {

        Item item = itemRepository.findById(itemId).get();

        Review review = Review.builder()
                .account(account.getAccount())
                .item(item)
                .reviewCreated(LocalDateTime.now())
                .content(reviewDto.getContent())
                .likeStatus(reviewDto.getLikeStatus())
                .build();


        Review newReview = reviewRepository.save(review);

        ReviewFile reviewFile = ReviewFile.builder()
                .review(newReview)
                .uuid(reviewDto.getReviewFile().getUuid())
                .fileName(reviewDto.getReviewFile().getFileName())
                .uploadPath(reviewDto.getReviewFile().getUploadPath())
                .build();


        reviewFileRepository.save(reviewFile);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/files")
    public ResponseEntity addFiles(@RequestBody MultipartFile[] uploadFile) {


        System.out.println("=========================");

        System.out.println(uploadFile);
        ReviewFile reviewFile = new ReviewFile();

        String uploadFolder = "C:\\Users\\kim\\git\\Market\\project\\src\\main\\resources\\templates\\uploads";

        for (MultipartFile multipartFile : uploadFile) {

            String uploadFileName = multipartFile.getOriginalFilename();

            reviewFile.setFileName(uploadFileName);            // 오리지날 이름저장

            UUID uuid = UUID.randomUUID();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            uploadFileName = uuid.toString() + "_" + uploadFileName;

            java.io.File saveFile = new java.io.File(uploadFolder, uploadFileName);

            reviewFile.setUuid(uuid.toString());                // uuid 저장
            reviewFile.setUploadPath(uploadFolder);             // 경로

            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {

            }
        }
        return new ResponseEntity(reviewFile, HttpStatus.OK);
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(String fileName) {

        Resource resource = new FileSystemResource("C:\\Users\\kim\\git\\Market\\project\\src\\main\\resources\\templates\\uploads\\" + fileName);

        return new ResponseEntity(resource, HttpStatus.OK);
    }

    @GetMapping("/manager/{option}/{keyword}")
    public ResponseEntity getManagerSearchReview(@AuthenticationPrincipal AccountAdapter account, @PathVariable int option, @PathVariable String keyword, Pageable pageable) {

        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (!account.getAccount().getRoles().contains(AccountRoles.ADMIN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Page<Review> pages;

        if (option == 0) {  // 상품명으로
            pages = reviewRepository.findByItem_Title(keyword, pageable);
        } else if (option == 1) {    // 리뷰작성자로
            pages = reviewRepository.findByAccount_AccountId(keyword, pageable);
        } else if (option == 2) {    // 작성내용으로
            pages = reviewRepository.findByContentContains(keyword, pageable);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        if (pages.getTotalElements() == 0)
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity(pages, HttpStatus.OK);
    }
}
