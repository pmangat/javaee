package com.javaeesamples.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OrderId", updatable = false)
	private Long Id;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.MERGE })
	@JoinColumn(name = "CustomerFK")
	private Customer customer;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.MERGE })
	@JoinColumn(name = "EmployeeFK")
	private Employee employee;

	@Temporal(TemporalType.DATE)
	@Column(name = "OrderDate")
	private Date orderDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "ShipDate")
	private Date shipDate;

	@Column(name = "Freight")
	private double freight;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.MERGE }, fetch=FetchType.EAGER)
	@JoinColumn(name = "OrderFK")
	private Set<OrderDetail> orderDetails;

	public Customer getCustomer() {
		return customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public double getFreight() {
		return freight;
	}

	public Long getId() {
		return Id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	public void setId(Long id) {
		Id = id;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
}
