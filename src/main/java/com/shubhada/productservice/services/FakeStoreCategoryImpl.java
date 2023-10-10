package com.shubhada.productservice.services;

import com.shubhada.productservice.dtos.CategoryDTO;
import com.shubhada.productservice.dtos.ProductDTO;
import com.shubhada.productservice.models.Category;
import com.shubhada.productservice.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreCategoryImpl implements CategoryService{
    private RestTemplateBuilder restTemplateBuilder;
    FakeStoreCategoryImpl(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder=restTemplateBuilder;
    }
    @Override
    public List<CategoryDTO> getAllCategories() {
        RestTemplate restTemplate=restTemplateBuilder.build();
       ResponseEntity<CategoryDTO[]> l= restTemplate.getForEntity(
                "https://fakestoreapi.com/products/categories",
                CategoryDTO[].class
        );
        List<CategoryDTO> answer=new ArrayList<>();
        for(CategoryDTO categoryDTO:l.getBody())
        {
            /*Category category=new Category();
          category.setName(categoryDTO.getCategory());*/
             answer.add(categoryDTO);
        }

        return answer;
    }

    @Override
    public List<Product> getProductsInCategory(String category) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<ProductDTO[]> response=restTemplate.getForEntity(
                "https://fakestoreapi.com/products/category/{category}",
                ProductDTO[].class,
                category
        );
        List<Product> answer=new ArrayList<>();
      for(ProductDTO productDTO:response.getBody()) {
          Product product = new Product();
          product.setId(productDTO.getId());
          product.setTitle(productDTO.getTitle());
          product.setPrice(productDTO.getPrice());
          product.setDescription(productDTO.getDescription());
          Category category1=new Category();
          category1.setName(productDTO.getCategory());
          product.setCategory(category1);
          product.setImageUrl(productDTO.getImage());
          answer.add(product);


      }

        return answer;
    }
}
