package com.shubhada.productservice.controllers;

import com.shubhada.productservice.dtos.AddNewProductRequestDTO;
import com.shubhada.productservice.dtos.ErrorResponseDTO;
import com.shubhada.productservice.dtos.GetSingleProductResponseDTO;
import com.shubhada.productservice.dtos.ProductDTO;
import com.shubhada.productservice.exceptions.NotFoundException;
import com.shubhada.productservice.models.Category;
import com.shubhada.productservice.models.Product;
import com.shubhada.productservice.repositories.ProductRepository;
import com.shubhada.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    //inversion of control
    private final ProductService productService;
    private ProductRepository productRepository;
    public ProductController(ProductService productService,ProductRepository productRepository){

        this.productService=productService;
        this.productRepository=productRepository;
    }
    @GetMapping("")
    public List<Product> getAllProducts(){
        return  productService.getAllProducts();
        //return "Getting All Products";
    }
    @GetMapping("/{productId}") //GetSingleProductResponseDTO
    public ResponseEntity<Product> getSingleProduct(@PathVariable("productId") Long productId) throws NotFoundException {
        /*GetSingleProductResponseDTO responseDTO=new GetSingleProductResponseDTO();
        responseDTO.setProduct(
                productService.getSingleProduct(productId)
        );
         return responseDTO;*/
        MultiValueMap<String,String> headers=new LinkedMultiValueMap<>();
        headers.add(

                "auth-token","noaccess4youheyhey"

        );

      /*  try {*/
            Optional<Product> productOptional = productService.getSingleProduct(productId);
      /*  }catch(Exception e){
            throw e;
        }*/
        if(productOptional.isEmpty()){
            throw new NotFoundException("No product with product Id: "+productId);
        }
      ResponseEntity<Product> response=new ResponseEntity(
              productService.getSingleProduct(productId),
              headers,
              HttpStatus.NOT_FOUND
      );

        return response;
    }
    @PostMapping("")
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDTO product){
           /* Product newProduct= productService.addNewProduct(product);*/
        Product newProduct=new Product();
        newProduct.setDescription(product.getDescription());
        newProduct.setImageUrl(product.getImage());
        newProduct.setTitle(product.getTitle());
        newProduct.setPrice(product.getPrice());
       newProduct=productRepository.save(newProduct);

            ResponseEntity<Product> response=new ResponseEntity<>(newProduct,HttpStatus.OK);
        return response;
      //  return "Adding New Product with "+productDTO;
    }
    //assignment take requestBody

    @PatchMapping("/{productId}")
    public Product updateProduct(@PathVariable("productId") Long productId,
                                 @RequestBody ProductDTO productDTO){
        //return "Updating a Product with id: "+productId +" and with data: "+productDTO;
        //convert productDTO object into Product object
        Product product=new Product();
        product.setId(productDTO.getId());
        product.setCategory(new Category());
        product.getCategory().setName(productDTO.getCategory());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        return productService.updateProduct(productId,product);
    }
    @PutMapping("/{productId}")
    public Product replaceProduct(@PathVariable("productId") Long productId,
                                  @RequestBody ProductDTO productDTO){
        Product product=new Product();
        product.setId(productDTO.getId());
        product.setCategory(new Category());
        product.getCategory().setName(productDTO.getCategory());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
      return productService.replaceProduct(productId,product);
    }
    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId){

        //return productService.deleteProduct(productId);
        return "hello";
       /* return "Deleting a Product with id: "+productId;*/
    }
    /*@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(Exception exception){
     ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO();
     errorResponseDTO.setErrorMessage(exception.getMessage());
     return new ResponseEntity<>(errorResponseDTO,HttpStatus.NOT_FOUND);
    }*/
}
