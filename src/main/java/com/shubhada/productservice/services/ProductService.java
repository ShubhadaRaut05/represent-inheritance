package com.shubhada.productservice.services;

import com.shubhada.productservice.dtos.ProductDTO;
import com.shubhada.productservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public interface ProductService {

  List<Product> getAllProducts();


  Optional<Product> getSingleProduct(Long productId);

  //service should not take DTO object
    Product addNewProduct(ProductDTO product);

    /*
    product object has only those fields filled which need to be update
    everything else is null
     */
    Product updateProduct( Long productId, Product product);

    Product replaceProduct(Long productId,Product product);

    boolean deleteProduct( Long productId);
}
