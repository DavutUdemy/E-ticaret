package com.hazir.Hazirlaniyor.entity.concretes;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {
	@SequenceGenerator(
			name = "cart_sequence",
			sequenceName = "cart_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "cart_sequence"
	)
	private Long      id;
	@NotNull(message = "ProductName can not be empty!")
	private String          productName;
	@NotNull(message = "Product Description can not be empty!")
	private String          productDescription;
	@NotNull(message = "Product Price can not be empty!")
	private Integer         productPrice;
	//@NotNull(message = "Product Category can not be empty!")
	@Enumerated(EnumType.STRING)
	private ProductCategory productCategory;
	@NotNull(message = "Product Price can not be empty!")
	private Integer         quantity;
	@Formula(value = "product_price * quantity")
	private Integer         totalPrice;
	@NotNull(message = "Email can not be empty!")
	private String          email;




}