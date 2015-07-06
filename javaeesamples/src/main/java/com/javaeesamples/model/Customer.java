package com.javaeesamples.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CustomerId", updatable = false)
	private Long Id;

	@Column(name = "ContactName")
	private String contactName;

	@Column(name = "ContactPhone")
	private String phoneNumber;

	@Column(name = "Address")
	private String address;

	@Column(name = "City")
	private String city;

	@Column(name = "State")
	private String state;

	@Column(name = "Country")
	private String country;

	@Column(name = "PostalCode")
	private String postalCode;

	@OneToMany(mappedBy = "customer", cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
	private Set<Orders> orders;
	
	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getContactName() {
		return contactName;
	}

	public String getCountry() {
		return country;
	}

	public Long getId() {
		return Id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getState() {
		return state;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setId(Long id) {
		Id = id;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}
}
