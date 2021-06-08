package com.hazir.Hazirlaniyor.entity.concretes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table



public class Product  {
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long Id;
    @NotNull(message = "ProductName can not be Empty")
    private String productName;
	  @NotNull(message = "Product Description can not be Empty")
	  private String productDescription;
	  @NotNull(message = "Product Price can not be Empty")
	  private Integer productPrice;
	  @NotNull(message = "Product Category can not be Empty ")
	  @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
	  @NotNull(message = "Stock can not be empty ")
	  private Integer unitsInStock;

    public Product() {
    }

    public Product(Long id, String productName, String productDescription, Integer productPrice, ProductCategory productCategory, Integer unitsInStock) {
        Id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.unitsInStock = unitsInStock;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }
}
