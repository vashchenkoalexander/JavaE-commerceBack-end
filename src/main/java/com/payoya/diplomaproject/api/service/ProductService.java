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

    public Product findProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public Product saveNewProduct(Product product){
        product.setDateofCreate(LocalDateTime.now().withNano(0));
        return productRepository.save(product);
    }

    public List<Product> saveNewListProducts(List<Product> products){
        for(Product product : products){
            product.setDateofCreate(LocalDateTime.now().withNano(0));
        }
        return productRepository.saveAll(products);
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

    /*
    Find all Products by Tags with contains this tag
     */
    public List<Product> findAllByTags(String tags){
        return productRepository.findAllByTagsContainsIgnoreCase(tags);
    }

    @PostAuthorize("returnObject.user.username == principal.username")
    public Product saveNewProductWithUser(Long id, Product product){
        product.setUser(userService.findUserById(id));
        return saveNewProduct(product);
    }

    public List<Product> findAllByContainingPropertyEverywhere(String propertyName){
        return productRepository.
                findAllByTagsContainsIgnoreCaseOrBodyTitleContainingIgnoreCaseOrTitleContainingIgnoreCaseOrderByPrice
                        (propertyName, propertyName, propertyName);
    }

}
