package com.mono.api.cart.internal;

import com.mono.api.abo.IAboService;
import com.mono.api.cart.CartItem;
import com.mono.api.product.IProductService;
import com.mono.api.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
//@SessionScope TODO should be stateful
public class CartManagement {

    private final List<CartItem> CART = new ArrayList<>();
    private final IAboService aboService;
    private final IProductService productService;

    @Autowired
    public CartManagement(IAboService aboService, IProductService productService) {
        this.aboService = aboService;
        this.productService = productService;
    }

    public List<CartItem> getCart() {
        return this.CART;
    }

    public void addToCart(Integer productNr) {
        Product input = productService.getProduct(productNr);
        CART.add(new CartItem(input, 1)); // count as placeholder for more flexibility
    }

    public void checkoutCart() {
        CART.forEach(item -> aboService.addAbo(item.getProduct().getNr()));
        this.resetCart();
    }

    public void resetCart() {
        CART.clear();
    }
}
