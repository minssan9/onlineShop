package com.minssan9.shop.web.support;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.minssan9.shop.accounts.Account;
 

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginedUser";
    public static final String CART_KEY = "cart";

    public static boolean isLoginUser(NativeWebRequest webRequest) {
        Object loginedUser = webRequest.getAttribute(USER_SESSION_KEY, WebRequest.SCOPE_SESSION);
        return loginedUser != null;
    }

    public static Account getAccountFromSession(NativeWebRequest webRequest) {
        if (!isLoginUser(webRequest)) {
            return Account.GUEST_USER;
        }
        return (Account) webRequest.getAttribute(USER_SESSION_KEY, WebRequest.SCOPE_SESSION);
    }

    public static boolean isLoginAccount(HttpSession httpSession) {
        Object sessionedAccount = httpSession.getAttribute(USER_SESSION_KEY);
        if (sessionedAccount == null) {
            return false;
        }
        return true;
    }

    public static Account getAccountFromSession(HttpSession session) {
        if (!isLoginAccount(session)) {
            return Account.GUEST_USER;
        }
        return (Account) session.getAttribute(USER_SESSION_KEY);
    }

    public static boolean hasCart(HttpSession session) {
        Object sessionedCart = session.getAttribute(CART_KEY);
        if (sessionedCart == null) {
            return false;
        }
        return true;
    }

//    public static Cart getCartFromSession(HttpSession session) {
//        if (!hasCart(session)) {
//            Cart cart = new Cart();
//            session.setAttribute(CART_KEY, cart);
//            return cart;
//        }
//        return (Cart) session.getAttribute(CART_KEY);
//    }
}
