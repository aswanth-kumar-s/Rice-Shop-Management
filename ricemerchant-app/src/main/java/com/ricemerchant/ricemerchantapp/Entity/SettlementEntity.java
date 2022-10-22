package com.ricemerchant.ricemerchantapp.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class SettlementEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int settlementId;
	
	private int stockHistoryId;
	private String settlementType;
	private long chequeNumber;
	private float settlementAmount;
	
	public int getSettlementId() {
		return settlementId;
	}
	
	public void setSettlementId(int settlementId) {
		this.settlementId = settlementId;
	}
	
	public int getStockHistoryId() {
		return stockHistoryId;
	}
	
	public void setStockHistoryId(int stockHistoryId) {
		this.stockHistoryId = stockHistoryId;
	}
	
	public String getSettlementType() {
		return settlementType;
	}
	
	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}
	
	public long getChequeNumber() {
		return chequeNumber;
	}
	
	public void setChequeNumber(long chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	
	public float getSettlementAmount() {
		return settlementAmount;
	}
	
	public void setSettlementAmount(float settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	
}
