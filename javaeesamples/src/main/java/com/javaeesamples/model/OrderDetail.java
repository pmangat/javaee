package com.javaeesamples.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OrderDetailId", updatable = false)
	private Long Id;

	@Column(name = "UnitPrice")
	private double unitPrice;

	@Column(name = "Quantity")
	private int quantity;

	@Column(name = "Discount")
	private double discount;
	
	@Column(name = "ProductName")
	private String productName;

	public double getDiscount() {
		return discount;
	}

	public Long getId() {
		return Id;
	}

	public String getProductName() {
		return productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setId(Long id) {
		Id = id;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
