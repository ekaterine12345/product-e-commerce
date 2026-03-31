package com.example.product_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "products")
public class Product extends BaseEntity<Long>{
    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    @SequenceGenerator(name="productSeqId", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="productSeqId")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    private String description;
}
