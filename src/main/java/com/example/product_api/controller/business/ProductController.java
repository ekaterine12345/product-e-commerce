package com.example.product_api.controller.business;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_api.dto.product.ProductRequestDto;
import com.example.product_api.dto.product.ProductResponseDto;
import com.example.product_api.dto.product.UpdateProductDto;
import com.example.product_api.service.business.ProductService;


@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping()
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('EDITOR')")
  public List<ProductResponseDto> getProducts() {
    return productService.getProducts();
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
  public ProductResponseDto addProduct(@Valid @RequestBody ProductRequestDto product) {
    return productService.addProduct(product);
  }

  @PutMapping("{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
  public ProductResponseDto updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDto productDto){
      return productService.updateProduct(id, productDto);
  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ProductResponseDto deleteProduct(@PathVariable Long id){
    return productService.deleteProduct(id);
  }
}
