package com.example.product_api.dto.product;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Positive(message = "Price must be greater than 0")
    private Double price;
    private String description;
}
