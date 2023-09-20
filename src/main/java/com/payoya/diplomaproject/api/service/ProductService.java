package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.Product;
import com.payoya.diplomaproject.api.repository.IProductRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private IProductRepository productRepository;

    private UserService userService;

    public ProductService(IProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();

    }

    public Product saveNewProduct(Product product){
        product.setDateofCreate(LocalDateTime.now().withNano(0));
        return productRepository.save(product);
    }

    @PostAuthorize("returnObject.user.username == principal.username")
    public Product saveNewProductWithUser(Long id, Product product){
        product.setUser(userService.findUserById(id));
        return saveNewProduct(product);
    }



    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }



}