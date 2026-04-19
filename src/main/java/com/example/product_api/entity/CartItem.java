package com.example.product_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItem extends BaseEntity<Long>{

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    @SequenceGenerator(name="cartItemSeqId", sequenceName = "CARTITEM_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="cartItemSeqId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    @jakarta.validation.constraints.Min(1)
    private Integer quantity;

    public CartItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        setQuantity(quantity);
    }

    public void increase(int amount) {
        validatePositive(amount);
        this.quantity += amount;
    }

    public void decrease(int amount) {
        validatePositive(amount);

        if (amount >= this.quantity) {
            throw new IllegalArgumentException("Cannot reduce below 1. Use remove endpoint instead.");
        } else {
            this.quantity -= amount;
        }
    }

    public boolean isZero() {
        return quantity == 0;
    }

    private void validatePositive(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}
