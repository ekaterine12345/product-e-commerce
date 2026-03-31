package com.example.product_api.service.business;

import com.example.product_api.dto.product.ProductRequestDto;
import com.example.product_api.dto.product.ProductResponseDto;
import com.example.product_api.dto.product.UpdateProductDto;
import com.example.product_api.entity.Product;
import com.example.product_api.exception.product.ProductNotFoundException;
import com.example.product_api.mapper.ProductMapper;
import com.example.product_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    private Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    public List<ProductResponseDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toResponseDto)
                .toList();
    }

    public ProductResponseDto addProduct(ProductRequestDto productDto) {
        Objects.requireNonNull(productDto, "Product request data must not be null");

        Product product = productMapper.toEntity(productDto);
        Product insertedProduct = productRepository.save(product);

        return productMapper.toResponseDto(insertedProduct);
    }

    public ProductResponseDto updateProduct(Long id, UpdateProductDto productDto){
        Product product = getProductById(id);
        productMapper.updateProductFromDto(productDto, product);
        return productMapper.toResponseDto(productRepository.save(product));
    }

    public ProductResponseDto deleteProduct(Long id){
        Product product = getProductById(id);
        productRepository.deleteById(id);
        return productMapper.toResponseDto(product);
    }
}
