package com.ricemerchant.ricemerchantapp.Entity;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class StockHistoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int stockHistoryId;
	private int brandId;
	private LocalDate dateOfPurchase;
	private String brandName;
	private int quantity;
	private LocalDate dateOfDelivery;
	private LocalDate dueDate;
	private int settlementPeriod;
	private boolean settlementStatus;
	private double riceAmount;
	private double miscellaneousAmount;
	private double totalAmount;
	
	public int getStockHistoryId() {
		return stockHistoryId;
	}
	public void setStockHistoryId(int stockHistoryId) {
		this.stockHistoryId = stockHistoryId;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public LocalDate getDateOfPurchase() {
		return dateOfPurchase;
	}
	public void setDateOfPurchase(LocalDate dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public LocalDate getDateOfDelivery() {
		return dateOfDelivery;
	}
	public void setDateOfDelivery(LocalDate dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public int getSettlementPeriod() {
		return settlementPeriod;
	}
	public void setSettlementPeriod(int settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}
	public boolean isSettlementStatus() {
		return settlementStatus;
	}
	public void setSettlementStatus(boolean settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	public double getRiceAmount() {
		return riceAmount;
	}
	public void setRiceAmount(double riceAmount) {
		this.riceAmount = riceAmount;
	}
	public double getMiscellaneousAmount() {
		return miscellaneousAmount;
	}
	public void setMiscellaneousAmount(double miscellaneousAmount) {
		this.miscellaneousAmount = miscellaneousAmount;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
