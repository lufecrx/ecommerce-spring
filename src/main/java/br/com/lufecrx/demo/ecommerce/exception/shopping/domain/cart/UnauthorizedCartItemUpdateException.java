package br.com.lufecrx.demo.ecommerce.exception.shopping.domain.cart;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnauthorizedCartItemUpdateException extends RuntimeException {
    
    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    public UnauthorizedCartItemUpdateException() {
        super(bundle.getString("cart.not_allowed"));
        log.error(bundle.getString("cart.not_allowed"));
    }
    
    public UnauthorizedCartItemUpdateException(Throwable cause) {
        super(bundle.getString("cart.not_allowed"), cause);
        log.error(bundle.getString("cart.not_allowed"), cause);
    }
}
