package com.minssan9.shop.orders;

import com.minssan9.shop.ShopApplication;
import com.minssan9.shop.accounts.AccountAdapter;
import com.minssan9.shop.accounts.AccountRepository;
import com.minssan9.shop.accounts.AccountRoles;
import com.minssan9.shop.dto.OperationStatus;
import com.minssan9.shop.items.Item;
import com.minssan9.shop.items.ItemRepository;
import com.minssan9.shop.qnas.QnARepository;
import com.minssan9.shop.reviews.ReviewRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    private Logger logger= LoggerFactory.getLogger(ShopApplication.class);

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    QnARepository qnARepository;
    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping
    public ResponseEntity getOrders(@AuthenticationPrincipal AccountAdapter account, Pageable pageable) {

        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        Page<Order> pages = orderRepository.findByAccount(account.getAccount(), pageable);
        return new ResponseEntity(pages, HttpStatus.OK);
    }


    @GetMapping("/manager")
    public ResponseEntity getManagerOrders(@AuthenticationPrincipal AccountAdapter account, Pageable pageable) {

        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (!account.getAccount().getRoles().contains(AccountRoles.ADMIN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Page<Order> pages = orderRepository.findAll(pageable);

        return new ResponseEntity(pages, HttpStatus.OK);
    }

    @GetMapping("/manager/{option}/{keyword}")
    public ResponseEntity getManagerSearchOrders(@AuthenticationPrincipal AccountAdapter account, @PathVariable int option, @PathVariable String keyword, Pageable pageable) {

        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (!account.getAccount().getRoles().contains(AccountRoles.ADMIN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Page<Order> pages;

        if (option == 0) {      // 상품명 으로 조회
            pages = orderRepository.findByItem_Title(keyword, pageable);
        } else if (option == 1) {       // 아이디로 조회
            pages = orderRepository.findByAccount_AccountId(keyword, pageable);
        } else if (option == 2) {       // 결제방법
            pages = orderRepository.findByPayment(keyword, pageable);
        } else if (option == 3) {   // 주문상태

            String arr[] = {"결제완료", "배송준비", "배송중", "배송완료", "거래취소"};

            int status = Arrays.asList(arr).indexOf(keyword);

            if (status == -1)
                return new ResponseEntity(HttpStatus.NO_CONTENT);

            pages = orderRepository.findByStatus(status, pageable);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        if (pages.getTotalElements() == 0)
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity(pages, HttpStatus.OK);

    }

    // 문제점: item.id 리스트를 받아서 조회해서 넣어야함
    @PostMapping
    public ResponseEntity addOrder(@AuthenticationPrincipal AccountAdapter account, @RequestBody List<Item> itemList) {


        for (int i = 0; i < itemList.size(); i++) {

            System.out.println("테스트:"+itemList.get(i).getOptions());

            Optional<Item> optionalItem = itemRepository.findById(itemList.get(i).getId());
            Item item = optionalItem.orElseThrow(() -> new EntityNotFoundException());
            item.setSellCount(item.getSellCount()+1);
            Item save = itemRepository.save(item);

            Order order = Order.builder()
                    .account(account.getAccount())
                    .orderCreated(LocalDateTime.now())
                    .status(0)
                    .item(save)
                    .payment("카드")
                    .build();

            if(itemList.get(i).getOptions().size()>0 && !itemList.get(i).getOptions().get(0).getOption().equals("선택"))
                order.setOptions(itemList.get(i).getOptions().get(0).getOption());

            orderRepository.save(order);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    // 구매내역을 수정할수없으니 진행 상태를 수정
    @PutMapping
    public ResponseEntity updateStatus(@AuthenticationPrincipal AccountAdapter account, @RequestBody Order order) {

        if (account == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        if (!account.getAccount().getRoles().contains(AccountRoles.ADMIN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        // order ==> status,id 두개만 받아옴

        Order newOrder = orderRepository.findById(order.getId()).get();
        newOrder.setStatus(order.getStatus());
        orderRepository.save(newOrder);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/operation/status/{yy}/{mm}/{dd}")
    public ResponseEntity getOperationStatus(@PathVariable int yy, @PathVariable int mm, @PathVariable int dd) {

        List<OperationStatus> OperationStatusList = new ArrayList<>();

        logger.info("운영현황 조회:"+yy+"년"+mm+"월"+dd+"일로부터 1주일 조회");
        OperationStatus sum = new OperationStatus();

        // 지난 1주일간의 운영현황 확인
        for (int i = 0; i <= 6; i++) {
            LocalDateTime start = LocalDateTime.of(LocalDate.of(yy, mm, dd).plusDays(i), LocalTime.of(0, 0, 0));
            LocalDateTime end = LocalDateTime.of(LocalDate.of(yy, mm, dd).plusDays(i), LocalTime.of(23, 59, 59));
            OperationStatus operationStatus = operationCal(sum, start, end);
            OperationStatusList.add(operationStatus);
        }
        sum.setToday("합계");
        OperationStatusList.add(sum);
        return new ResponseEntity(OperationStatusList, HttpStatus.OK);

    }

    @GetMapping("/operation/status")
    public ResponseEntity getOperationStatus() {

        List<OperationStatus> OperationStatusList = new ArrayList<>();

        OperationStatus sum = new OperationStatus();

        // 지난 1주일간의 운영현황 확인
        for (int i = 0; i <= 6; i++) {
            LocalDateTime start = LocalDateTime.of(LocalDate.now().plusDays(i), LocalTime.of(0, 0, 0));
            LocalDateTime end = LocalDateTime.of(LocalDate.now().plusDays(i), LocalTime.of(23, 59, 59));
            OperationStatus operationStatus = operationCal(sum, start, end);
            OperationStatusList.add(operationStatus);
        }
        sum.setToday("합계");
        OperationStatusList.add(sum);
        return new ResponseEntity(OperationStatusList, HttpStatus.OK);

    }


    public OperationStatus operationCal(OperationStatus sum, LocalDateTime start, LocalDateTime end) {
        List<Order> orders = orderRepository.findByOrderCreatedBetween(start, end);

        OperationStatus operationStatus = new OperationStatus();

        operationStatus.setToday(start.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        operationStatus.setQnaCount(qnARepository.findByQnaCreatedBetween(start, end).size());
        operationStatus.setReviewCount(reviewRepository.findByReviewCreatedBetween(start, end).size());

        // 매출액,결제완료,배송준비,배송중,배송완료
        for (int j = 0; j < orders.size(); j++) {
            operationStatus.setSales(operationStatus.getSales()
                    + (orders.get(j).getItem().getPrice() - orders.get(j).getItem().getDiscount())); // 매출액
            if (orders.get(j).getStatus() == 0) // 결제완료
                operationStatus.setPaymentCompletedCount(operationStatus.getPaymentCompletedCount() + 1);
            else if (orders.get(j).getStatus() == 1)    // 배송준비
                operationStatus.setShippingPreparationCount(operationStatus.getShippingPreparationCount() + 1);
            else if (orders.get(j).getStatus() == 2)    // 배송중
                operationStatus.setShippingCount(operationStatus.getShippingCount() + 1);
            else if (orders.get(j).getStatus() == 3)    // 배송완료
                operationStatus.setShippingCompleteCount(operationStatus.getShippingCompleteCount() + 1);
        }

        sum.setSales(sum.getSales() + operationStatus.getSales());
        sum.setShippingCount(sum.getShippingCount() + operationStatus.getShippingCount());
        sum.setShippingCompleteCount(sum.getShippingCompleteCount() + operationStatus.getShippingCompleteCount());
        sum.setShippingPreparationCount(sum.getShippingPreparationCount() + operationStatus.getShippingPreparationCount());
        sum.setReviewCount(sum.getReviewCount() + operationStatus.getReviewCount());
        sum.setQnaCount(sum.getQnaCount() + operationStatus.getQnaCount());
        sum.setPaymentCompletedCount(sum.getPaymentCompletedCount() + operationStatus.getPaymentCompletedCount());

        return operationStatus;
    }


}
