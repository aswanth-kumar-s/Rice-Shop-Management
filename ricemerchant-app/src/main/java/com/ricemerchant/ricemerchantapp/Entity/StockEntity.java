package com.ricemerchant.ricemerchantapp.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class StockEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int StockId;
	@Column(unique=true)
	private int brandId;
	private String brandName;
	private int stocks;
	
	public int getStockId() {
		return StockId;
	}
	
	public void setStockId(int StockId) {
		this.StockId = StockId;
	}
	
	public int getBrandId() {
		return brandId;
	}
	
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public int getStocks() {
		return stocks;
	}
	
	public void setStocks(int stocks) {
		this.stocks = stocks;
	}
		
}
