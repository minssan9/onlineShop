package com.minssan9.shop.carts;

import com.minssan9.shop.accounts.Account;
import com.minssan9.shop.accounts.AccountAdapter;
import com.minssan9.shop.accounts.AccountRepository;
import com.minssan9.shop.items.Item;
import com.minssan9.shop.items.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CartRepository cartRepository;

    @GetMapping
    public ResponseEntity getCarts(@AuthenticationPrincipal AccountAdapter adapter) {

        if (adapter == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Optional<Account> optionalAccount = accountRepository.findByAccountId(adapter.getAccount().getAccountId());
        Account account = optionalAccount.get();

        return new ResponseEntity(account.getCarts(), HttpStatus.OK);

    }

    @PostMapping("/{itemId}")
    public ResponseEntity addCart(@AuthenticationPrincipal AccountAdapter adapter, @PathVariable Long itemId) {

        if (adapter == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (!optionalItem.isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Optional<Account> optionalAccount = accountRepository.findByAccountId(adapter.getAccount().getAccountId());
        Account account = optionalAccount.get();

        Optional<Cart> optionalCart = cartRepository.findByAccount_AccountIdAndItem_Id(account.getAccountId(), itemId);

        Cart save;

        if (!optionalCart.isPresent()) {
            Cart cart = Cart.builder()
                    .account(account)
                    .item(optionalItem.get())
                    .build();

            save = cartRepository.save(cart);
        } else {
            save = optionalCart.get();
        }

        return new ResponseEntity(save, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCart(@AuthenticationPrincipal AccountAdapter adapter, @PathVariable Long id) {

        if (adapter == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Optional<Cart> optionalCart = cartRepository.findById(id);

        if (optionalCart.isPresent()) {
            cartRepository.delete(optionalCart.get());
        }

        return new ResponseEntity(HttpStatus.OK);

    }

    @DeleteMapping("/all")
    public ResponseEntity deleteAllCart(@AuthenticationPrincipal AccountAdapter adapter) {

        if (adapter == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List<Cart> carts = cartRepository.findByAccount_AccountId(adapter.getAccount().getAccountId());


        for(Cart cart:carts){
            cartRepository.delete(cart);
        }

        return new ResponseEntity(HttpStatus.OK);

    }
}
