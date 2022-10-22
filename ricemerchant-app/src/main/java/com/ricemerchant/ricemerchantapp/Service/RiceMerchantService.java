package com.ricemerchant.ricemerchantapp.Service;

import java.util.List;

import com.ricemerchant.ricemerchantapp.Entity.CartEntity;
import com.ricemerchant.ricemerchantapp.Entity.PurchaseHistoryEntity;
import com.ricemerchant.ricemerchantapp.Entity.StockEntity;
import com.ricemerchant.ricemerchantapp.Entity.StockHistoryEntity;

public interface RiceMerchantService {
   
   boolean registerBrand(String brandName, int kg);
   
   List<String> getAllBrands();
	
   boolean registerStocks(String brandName, String dateOfDelivery, String dateOfPurchase, float miscellaneousAmount, int quantity, float riceAmount, int settlementPeriodValue);
   
   List<StockHistoryEntity> getAllStockHistory();
   
   boolean addSettlement(int stockHistoryId, String settlementType, long chequeNumber, float settlementAmount);
   
   StockHistoryEntity getStockHistory(int id);
   
   boolean updateStockHistory(int stockhistoryId, String dateOfDelivery, String dateOfPurchase, float miscellaneousAmount, int quantity, float riceAmount, int settlementPeriodValue);
   
   boolean deleteStockHistory(int id);
   
   List<String> getAllBrandsInStockEntity();
   
   List<CartEntity> getAllCartProducts();
   
   boolean addToCart(String brandName, int quantity, float unitPrice, List<StockEntity> stocks);
   
   boolean deleteFromCart(int cartId, List<StockEntity> stocks);
   
   boolean emptyCart(boolean callfrom);
   
   List<StockEntity> getAllStocks();
   
   List<PurchaseHistoryEntity> getAllHistory();
   
   PurchaseHistoryEntity getPurchaseHistory(int purchaseHistoryId);

}
