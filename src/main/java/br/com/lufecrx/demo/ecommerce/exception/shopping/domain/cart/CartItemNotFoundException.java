package br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartItemNotFoundException extends RuntimeException {
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    public CartItemNotFoundException() {
        super(bundle.getString("cart.not_found"));
        log.error(bundle.getString("cart.not_found"));
    }
    
    public CartItemNotFoundException(Throwable cause) {
        super(bundle.getString("cart.not_found"), cause);
        log.error(bundle.getString("cart.not_found"), cause);
    }
}
