package com.shubhada.productservice.services;

import com.shubhada.productservice.Clients.fakestoreapi.FakeStoreClient;
import com.shubhada.productservice.Clients.fakestoreapi.FakeStoreProductDTO;
import com.shubhada.productservice.dtos.ProductDTO;
import com.shubhada.productservice.models.Category;
import com.shubhada.productservice.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FakeStoreProductImpl implements ProductService{
   private RestTemplateBuilder restTemplateBuilder;
   private FakeStoreClient fakeStoreClient;
   public FakeStoreProductImpl(RestTemplateBuilder restTemplateBuilder,FakeStoreClient fakeStoreClient){
       this.restTemplateBuilder=restTemplateBuilder;
       this.fakeStoreClient=fakeStoreClient;
   }
 private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDTO productDTO){
     Product product=new Product();
     product.setId(productDTO.getId());
     product.setTitle(productDTO.getTitle());
     product.setPrice(productDTO.getPrice());
     product.setDescription(productDTO.getDescription());

     Category category=new Category();
     category.setName(productDTO.getCategory());
     product.setCategory(category);
     product.setImageUrl(productDTO.getImage());
     return product;

 }
   private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod,String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate=restTemplateBuilder.requestFactory(HttpComponentsClientHttpRequestFactory.class).build();
       RequestCallback requestCallback =restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return((ResponseEntity)restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables));
    }


    @Override
    public List<Product> getAllProducts() {
      /* RestTemplate restTemplate=restTemplateBuilder.build();
      ResponseEntity<FakeStoreProductDTO[]> l= restTemplate.getForEntity(
               "https://fakestoreapi.com/products",
              FakeStoreProductDTO[].class

       );*/
        List<FakeStoreProductDTO> fakeStoreProductDTOS=fakeStoreClient.getAllProducts();
      List<Product> answer=new ArrayList<>();
      /*  for(FakeStoreProductDTO productDTO:l.getBody())*/
      for(FakeStoreProductDTO productDTO:fakeStoreProductDTOS)
      {

        //convert to product object
          answer.add(convertFakeStoreProductDtoToProduct(productDTO));

      }
        return answer;
    }
    /*
    Return a product object with all the details of the fetched product. The id of category will be null
    but the name of category is correct.
     */
    @Override
    public Optional<Product> getSingleProduct(Long productId) {
       //{url,returnType,parameter_in_url}
        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO> response= restTemplate.getForEntity(
                "https://fakestoreapi.com/products/{id}",
                FakeStoreProductDTO.class,
                productId);
        /*if(response.getStatusCode().is2xxSuccessful()){

        }else{}*/
        FakeStoreProductDTO productDTO=response.getBody();
        if(productDTO==null){
            return Optional.empty();
        }

        return Optional.of(convertFakeStoreProductDtoToProduct(productDTO));
    }

    @Override
    public Product addNewProduct(ProductDTO product) {
       /* RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO> response = restTemplate.postForEntity(
                "https://fakestoreapi.com/products",
                product,//accepts parameters
                FakeStoreProductDTO.class//response
        );*/

        FakeStoreProductDTO productDTO=fakeStoreClient.addNewProduct(product);
        //convert into Product object

        return convertFakeStoreProductDtoToProduct(productDTO);
    }

    //Patch for Object API
    @Override
    public Product updateProduct(Long productId, Product product) {
      /*RestTemplate restTemplate=restTemplateBuilder.build();*/
       /* RestTemplate restTemplate=restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();*/
      /*FakeStoreProductDTO fakeStoreProductDTO=new FakeStoreProductDTO();
      fakeStoreProductDTO.setDescription(product.getDescription());
      fakeStoreProductDTO.setImage(product.getImageUrl());
      fakeStoreProductDTO.setPrice(product.getPrice());
      fakeStoreProductDTO.setTitle(product.getTitle());
      fakeStoreProductDTO.setCategory(product.getCategory().getName());*/
    /*ResponseEntity<FakeStoreProductDTO> response=  requestForEntity(
        HttpMethod.PATCH,
              "https://fakestoreapi.com/products/{id}",
              fakeStoreProductDTO,//requsetBody
            FakeStoreProductDTO.class,
              productId

      );*/
     /* FakeStoreProductDTO fakeStoreProductDTOResponse=restTemplate.patchForObject(
              "https://fakestoreapi.com/products/{id}",
              fakeStoreProductDTO,
              FakeStoreProductDTO.class,
              productId
      );*/
       /* return convertFakeStoreProductDtoToProduct(response.getBody());*/
        FakeStoreProductDTO fakeStoreProductDTOResponse=fakeStoreClient.updateProduct(productId,product);
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDTOResponse);
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
       /* RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDTO fakeStoreProductDTO=new FakeStoreProductDTO();
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setImage(product.getImageUrl());
        fakeStoreProductDTO.setCategory(product.getCategory().getName());
        ResponseEntity<FakeStoreProductDTO> res=requestForEntity(
                HttpMethod.PUT,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDTO,
                FakeStoreProductDTO.class,
                productId

        );*/
        FakeStoreProductDTO fakeStoreProductDTOResponse=fakeStoreClient.replaceProduct(productId,product);
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDTOResponse);


    }

    @Override
    public boolean deleteProduct(Long productId) {
        return false;
    }
}
