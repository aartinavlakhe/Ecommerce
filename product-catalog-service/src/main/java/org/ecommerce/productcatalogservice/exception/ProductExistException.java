package org.ecommerce.productcatalogservice.exception;

public class ProductExistException extends RuntimeException {
    public ProductExistException(String message) {
        super(message);
    }
}
