package com.payoya.diplomaproject.api.controller;

import com.payoya.diplomaproject.api.entity.Product;
import com.payoya.diplomaproject.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/product")
public class ProductRestController {

    private ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> findAllProducts(){
        return productService.findAllProducts();
    }

    @PostMapping("/new")
    public Product saveNewProduct(@RequestBody @Valid Product product){
        return productService.saveNewProduct(product);
    }

//    @PostMapping("/new/{id}")
//    public Product saveNewProductWithUser(@RequestBody @Valid Product product, @PathVariable Long id){
//        return productService.saveNewProductWithUser(id, product);
//    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
    }



}
