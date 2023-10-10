package com.shubhada.productservice.Clients.fakestoreapi;

import com.shubhada.productservice.dtos.ProductDTO;
import com.shubhada.productservice.models.Product;
import com.shubhada.productservice.services.FakeStoreProductImpl;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class FakeStoreClient {
    private RestTemplateBuilder restTemplateBuilder;
    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder=restTemplateBuilder;
    }
    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate=restTemplateBuilder.requestFactory(HttpComponentsClientHttpRequestFactory.class).build();
        RequestCallback requestCallback =restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return((ResponseEntity)restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables));
    }
  public   List<FakeStoreProductDTO> getAllProducts()
    {
        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO[]> l= restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDTO[].class

        );
        return Arrays.asList(l.getBody());
    }


   public Optional<FakeStoreProductDTO> getSingleProduct(Long productId){
        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO> response= restTemplate.getForEntity(
                "https://fakestoreapi.com/products/{id}",
                FakeStoreProductDTO.class,
                productId);
       FakeStoreProductDTO productDTO=response.getBody();
       if(productDTO==null){
           return Optional.empty();
       }
       return   Optional.of( productDTO);


    }

    //service should not take DTO object
   public  FakeStoreProductDTO addNewProduct(ProductDTO product){
       RestTemplate restTemplate=restTemplateBuilder.build();
       ResponseEntity<FakeStoreProductDTO> response = restTemplate.postForEntity(
               "https://fakestoreapi.com/products",
               product,//accepts parameters
               FakeStoreProductDTO.class//response
       );

       return response.getBody();

    }

    /*
    product object has only those fields filled which need to be update
    everything else is null
     */
    public FakeStoreProductDTO updateProduct( Long productId, Product product){
        RestTemplate restTemplate=restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();
        FakeStoreProductDTO fakeStoreProductDTO=new FakeStoreProductDTO();
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setImage(product.getImageUrl());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setCategory(product.getCategory().getName());
        FakeStoreProductDTO fakeStoreProductDTOResponse=restTemplate.patchForObject(
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDTO,
                FakeStoreProductDTO.class,
                productId
        );
        return fakeStoreProductDTOResponse;
    }

    public FakeStoreProductDTO replaceProduct(Long productId,Product product){
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDTO fakeStoreProductDTO=new FakeStoreProductDTO();
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setImage(product.getImageUrl());
        fakeStoreProductDTO.setCategory(product.getCategory().getName());
        ResponseEntity<FakeStoreProductDTO> res= requestForEntity(
                HttpMethod.PUT,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDTO,
                FakeStoreProductDTO.class,
                productId

        );

        return res.getBody();
    }

    public FakeStoreProductDTO deleteProduct( Long productId){

        return null;
    }
}
