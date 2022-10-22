package com.ricemerchant.ricemerchantapp.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class BrandEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brandId;
    
	@Column(unique=true)
	private String brandName;
	private int kg;
	private boolean gstApplicable;
	
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
	
	public int getKg() {
		return kg;
	}
	
	public void setKg(int kg) {
		this.kg = kg;
	}
	
	public boolean getGstApplicable() {
		return gstApplicable;
	}
	
	public void setGstApplicable(boolean gstApplicable) {
		this.gstApplicable = gstApplicable;
	}
	
}
