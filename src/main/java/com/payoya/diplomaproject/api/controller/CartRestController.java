package com.payoya.diplomaproject.api.controller;

import com.payoya.diplomaproject.api.entity.Cart;
import com.payoya.diplomaproject.api.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/savenew")
    public Cart saveProductToCart(@RequestParam Long userId, @RequestParam Long productId){
        return cartService.saveItemToCart(userId, productId);
    }

    @DeleteMapping("/deleteItem")
    public Cart deleteItemFromCart(@RequestParam Long userId, @RequestParam Long productId){
        return cartService.deleteItemFromCart(userId, productId);
    }

}
