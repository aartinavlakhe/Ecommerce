package org.ecommerce.productcatalogservice.service;

import org.ecommerce.productcatalogservice.dto.FakeStoreProductDto;
import org.ecommerce.productcatalogservice.model.Category;
import org.ecommerce.productcatalogservice.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductServiceImp implements ProductServiceInterface {

    private RestTemplate restTemplate;

    public FakeStoreProductServiceImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                        FakeStoreProductDto.class);

        Product product = convertDtoToProduct(fakeStoreProductDto);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtoList =
                restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDto[].class);

        //convert result
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtoList) {
            products.add(convertDtoToProduct(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = convertProductToDto(product);
        fakeStoreProductDto = restTemplate.patchForObject("https://fakestoreapi.com/products/" + id,
                fakeStoreProductDto, FakeStoreProductDto.class);
        return convertDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = convertProductToDto(product);
        fakeStoreProductDto =
                restTemplate
                        .postForObject("https://fakestoreapi.com/products", fakeStoreProductDto, FakeStoreProductDto.class);
        return convertDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }

    private Product convertDtoToProduct(FakeStoreProductDto dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();

        product.setId(dto.getId());
        product.setProductName(dto.getTitle());
        product.setProductPrice(dto.getPrice());
        product.setProductDescription(dto.getDescription());

        // Assuming Category needs only id and title, and title is derived from category field of DTO
        Category category = new Category();
        // Here we would assign the id and title of the category appropriately.
        // For demonstration, let's assume the category title is what's passed in the DTO
        // and we set a dummy ID or fetch the correct ID based on the title from a database or other source.
        category.setId(0L); // Dummy ID or retrieve appropriately
        category.setCategoryName(dto.getCategory());

        product.setProductCategory(category);

        return product;
    }

    // Converts a Product to a FakeStoreProductDto object
    public static FakeStoreProductDto convertProductToDto(Product product) {
        if (product == null) {
            return null;
        }

        FakeStoreProductDto dto = new FakeStoreProductDto();

        dto.setId(0L);
        dto.setTitle(product.getProductName());
        dto.setPrice(product.getProductPrice());
        dto.setDescription(product.getProductDescription());

        // Assuming the category title is what needs to be set in the DTO's category field
        if (product.getProductCategory() != null) {
            dto.setCategory(product.getProductCategory().getCategoryName());
        } else {
            dto.setCategory(null); // or set to a default value as appropriate
        }

        return dto;
    }

}
