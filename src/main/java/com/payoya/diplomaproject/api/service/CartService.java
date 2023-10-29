package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.Cart;


import com.payoya.diplomaproject.api.entity.OrderItem;
import com.payoya.diplomaproject.api.entity.Product;
import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.exceptions.BadQuantityOfProductException;
import com.payoya.diplomaproject.api.repository.ICartRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ICartRepository cartRepository;

    private final UserService userService;

    private final ProductService productService;

    public CartService(ICartRepository cartRepository, @Lazy UserService userService, @Lazy ProductService productService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public Cart createNew(User user){
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        return cart;
    }

    public Cart saveItemToCart(Long userId, Long productId){
        User user = userService.findUserById(userId);
        Product product = productService.findProductById(productId);

        OrderItem orderItem = new OrderItem();

        if(product.getStockQuantity()-1 < 0 || product.getStockQuantity() < 0){
            throw new BadQuantityOfProductException("This quantity of products bigger than we have. We have: " + product.getStockQuantity());
        }

        orderItem.setProduct(product);
        orderItem.setItemPrice(product.getPrice());
        orderItem.setQuantity(1);
        product.setStockQuantity(product.getStockQuantity()-1);
        productService.saveNewProduct(product);
        orderItem.setTitle(product.getTitle());
        orderItem.setBodyTitle(product.getBodyTitle());
        orderItem.setTags(product.getTags());
        Cart cart = user.getShoppingCart();
        cart.getOrderItems().add(orderItem);

        cartRepository.save(cart);

        return cart;


    }

}