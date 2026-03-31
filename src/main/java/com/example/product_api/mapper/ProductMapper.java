package com.example.product_api.mapper;


import com.example.product_api.dto.product.ProductRequestDto;
import com.example.product_api.dto.product.ProductResponseDto;
import com.example.product_api.dto.product.UpdateProductDto;
import com.example.product_api.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toResponseDto(Product product);

    Product toEntity(ProductRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(UpdateProductDto dto, @MappingTarget Product entity);
}
