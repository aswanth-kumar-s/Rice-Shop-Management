package com.ricemerchant.ricemerchantapp.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricemerchant.ricemerchantapp.Entity.BrandEntity;
import com.ricemerchant.ricemerchantapp.Entity.CartEntity;
import com.ricemerchant.ricemerchantapp.Entity.PurchaseHistoryEntity;
import com.ricemerchant.ricemerchantapp.Entity.SettlementEntity;
import com.ricemerchant.ricemerchantapp.Entity.StockEntity;
import com.ricemerchant.ricemerchantapp.Entity.StockHistoryEntity;

import com.ricemerchant.ricemerchantapp.Repository.BrandRepository;
import com.ricemerchant.ricemerchantapp.Repository.CartRepository;
import com.ricemerchant.ricemerchantapp.Repository.PurchaseHistoryRepository;
import com.ricemerchant.ricemerchantapp.Repository.SettlementRepository;
import com.ricemerchant.ricemerchantapp.Repository.StockHistoryRepository;
import com.ricemerchant.ricemerchantapp.Repository.StockRepository;

@Service
public class RiceMerchantServiceImplementation implements RiceMerchantService {

	@Autowired
	private BrandRepository brandrepository;
	@Autowired
	private CartRepository cartrepository;
	@Autowired
	private StockRepository stockrepository;
	@Autowired
	private StockHistoryRepository stockhistoryrepository;
	@Autowired
	private PurchaseHistoryRepository purchasehistoryrepository;
	@Autowired
	private SettlementRepository settlementRepository;

	@Override
	public boolean registerBrand(String brandName, int kg) {
		if(((List<BrandEntity>) brandrepository.findAll()).stream().filter(predicate -> predicate.getBrandName().equalsIgnoreCase(brandName+" - "+kg+"kg")).count() > 0) 
			return false;
		else if(brandName!=null && kg!=0) {
			BrandEntity brandEntity=new BrandEntity();
			brandEntity.setBrandName(brandName+" - "+kg+"kg");
			brandEntity.setGstApplicable(kg<=25?true:false);
			brandEntity.setKg(kg);
		    brandrepository.save(brandEntity);
		    return true;
		}
		else
			return false;
	}
	
	@Override
	public List<String> getAllBrands() {
		return ((List<BrandEntity>) brandrepository.findAll()).stream().map(BrandEntity::getBrandName).collect(Collectors.toList());
	}

	@Override
	public boolean registerStocks(String brandName, String dateOfDelivery, String dateOfPurchase, float miscellaneousAmount, int quantity, float riceAmount, int settlementPeriodValue) {
		StockHistoryEntity stockHistoryEntity=new StockHistoryEntity();
		int brandId=((List<BrandEntity>) brandrepository.findAll()).stream().filter(predicate -> predicate.getBrandName().equalsIgnoreCase(brandName)).findFirst().get().getBrandId();
		stockHistoryEntity.setBrandId(brandId);
		stockHistoryEntity.setBrandName(brandName);
		stockHistoryEntity.setDateOfDelivery(LocalDate.parse(dateOfDelivery));
		stockHistoryEntity.setDateOfPurchase(LocalDate.parse(dateOfPurchase));
		LocalDate today=LocalDate.parse(dateOfDelivery);
		stockHistoryEntity.setDueDate(LocalDate.parse(today.plusDays(settlementPeriodValue).toString()));
		stockHistoryEntity.setMiscellaneousAmount(miscellaneousAmount);
		stockHistoryEntity.setQuantity(quantity);
		stockHistoryEntity.setRiceAmount(riceAmount);
		stockHistoryEntity.setSettlementPeriod(settlementPeriodValue);
		stockHistoryEntity.setTotalAmount(stockHistoryEntity.getRiceAmount()+stockHistoryEntity.getMiscellaneousAmount());
		stockHistoryEntity.setSettlementStatus(false);
	    stockhistoryrepository.save(stockHistoryEntity);
		Optional<StockEntity> stockEntity=((List<StockEntity>) stockrepository.findAll()).stream().filter(predicate -> predicate.getBrandName().equalsIgnoreCase(brandName)).findFirst();
    	StockEntity newStockEntity=new StockEntity();
		if(stockEntity.isPresent()) {
			newStockEntity=stockEntity.get();
			newStockEntity.setStocks((newStockEntity.getStocks())+quantity);
	    }
	    else {
	    	newStockEntity.setBrandId(brandId);
	    	newStockEntity.setStocks(quantity);
	    	newStockEntity.setBrandName(brandName);
	    }
		stockrepository.save(newStockEntity);
	    return true;
	}

	@Override
	public List<StockHistoryEntity> getAllStockHistory() {
		return (List<StockHistoryEntity>) stockhistoryrepository.findAll();
	}

	@Override
	public List<StockEntity> getAllStocks() {
		return (List<StockEntity>) stockrepository.findAll();
	}

	@Override
	public StockHistoryEntity getStockHistory(int id) {
		return stockhistoryrepository.findById(id).get();
	}
	
	@Override
	public boolean addSettlement(int stockHistoryId, String settlementType, long chequeNumber, float settlementAmount) {
		SettlementEntity settlementEntity=new SettlementEntity();
		settlementEntity.setStockHistoryId(stockHistoryId);
		settlementEntity.setSettlementType(settlementType);
		settlementEntity.setSettlementAmount(settlementAmount);
		if(chequeNumber!=0)
			settlementEntity.setChequeNumber(chequeNumber);
		else
			settlementEntity.setChequeNumber(0);
	    settlementRepository.save(settlementEntity);
	    StockHistoryEntity stockHistory = stockhistoryrepository.findById(stockHistoryId).get();
	    stockHistory.setSettlementStatus(true);
	    stockhistoryrepository.save(stockHistory);
	    return true;
	}

	@Override
	public boolean updateStockHistory(int stockhistoryId, String dateOfDelivery, String dateOfPurchase, float miscellaneousAmount, int quantity, float riceAmount, int settlementPeriodValue) {
		StockHistoryEntity stockHistoryEntity=stockhistoryrepository.findById(stockhistoryId).get();
		stockHistoryEntity.setDateOfDelivery(LocalDate.parse(dateOfDelivery));
		stockHistoryEntity.setDateOfPurchase(LocalDate.parse(dateOfPurchase));
		LocalDate today=LocalDate.parse(dateOfDelivery);
		stockHistoryEntity.setDueDate(LocalDate.parse(today.plusDays(settlementPeriodValue).toString()));
		stockHistoryEntity.setMiscellaneousAmount(miscellaneousAmount);
		stockHistoryEntity.setRiceAmount(riceAmount);
		stockHistoryEntity.setSettlementPeriod(settlementPeriodValue);
		stockHistoryEntity.setTotalAmount(stockHistoryEntity.getRiceAmount()+stockHistoryEntity.getMiscellaneousAmount());
	    
	    Optional<StockEntity> stockEntity=((List<StockEntity>) stockrepository.findAll()).stream().filter(predicate -> predicate.getBrandName().equalsIgnoreCase(stockHistoryEntity.getBrandName())).findFirst();
	    StockEntity newStockEntity=stockEntity.get();
	    if(stockHistoryEntity.getQuantity()>quantity)
	    	newStockEntity.setStocks((newStockEntity.getStocks())-(stockHistoryEntity.getQuantity()-quantity));
	    else
	    	newStockEntity.setStocks((newStockEntity.getStocks())+(quantity-stockHistoryEntity.getQuantity()));
	    
	    stockHistoryEntity.setQuantity(quantity);
	    
	    stockhistoryrepository.save(stockHistoryEntity);
	    stockrepository.save(newStockEntity);
	    return true;
	}

	@Override
	public boolean deleteStockHistory(int id) {
		StockHistoryEntity stockHistory = stockhistoryrepository.findById(id).get();
		StockEntity stockEntity = ((List<StockEntity>) stockrepository.findAll()).stream().filter(predicate -> predicate.getBrandName().equalsIgnoreCase(stockHistory.getBrandName())).findFirst().get();
		stockEntity.setStocks(stockEntity.getStocks()-stockHistory.getQuantity());
		stockrepository.save(stockEntity);
		stockhistoryrepository.deleteById(id);
		return true;
	}
	
	@Override
	public List<String> getAllBrandsInStockEntity() {
		return ((List<StockEntity>) stockrepository.findAll()).stream().map(StockEntity::getBrandName).collect(Collectors.toList());
	}

	@Override
	public List<CartEntity> getAllCartProducts() {
		return (List<CartEntity>) cartrepository.findAll();
	}

	@Override
	public boolean addToCart(String brandName, int quantity, float unitPrice, List<StockEntity> stocks) {
		StockEntity stockEntity = stocks.stream().filter(predicate -> predicate.getBrandName().equalsIgnoreCase(brandName)).findFirst().get();
		BrandEntity brandEntity = ((List<BrandEntity>) brandrepository.findAll()).stream().filter(predicate -> predicate.getBrandName().equalsIgnoreCase(brandName)).findFirst().get();
		if(stockEntity.getStocks() >= quantity) {
			 CartEntity cart = new CartEntity();
			 DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			 cart.setDateOfPurchase(dateTimeFormatter.format(LocalDateTime.now()).toString());
			 cart.setBrandName(brandName);
			 cart.setUnitPrice(unitPrice);
			 cart.setQuantity(quantity);
		     cart.setTotalPrice(quantity * unitPrice);
		     if(brandEntity.getGstApplicable())
		    	 cart.setTaxAmount(0.05*cart.getTotalPrice());
		     else
		    	 cart.setTaxAmount(0);
		     cartrepository.save(cart);
		     
		     stockEntity.setStocks(stockEntity.getStocks()-cart.getQuantity());
		     stockrepository.save(stockEntity);
		     return true;
		}
		else return false;
	}

	@Override
	public boolean deleteFromCart(int cartId, List<StockEntity> stocks) {
		CartEntity cart = cartrepository.findById(cartId).get();
		StockEntity stock = stocks.stream().filter(predicate -> predicate.getBrandName().equalsIgnoreCase(cart.getBrandName())).findFirst().get();
		stock.setStocks(stock.getStocks()+cart.getQuantity());
		stockrepository.save(stock);
		cartrepository.deleteById(cartId);
		return true;
	}

	@Override
	public boolean emptyCart(boolean callfrom) {
		if(callfrom==true) {
			List<PurchaseHistoryEntity> purchasehistorylist = new ArrayList<PurchaseHistoryEntity>();
		    for(CartEntity cart : cartrepository.findAll()) {
		       PurchaseHistoryEntity purchasehistory = new PurchaseHistoryEntity();
		       purchasehistory.setBrandName(cart.getBrandName());
		       purchasehistory.setDateOfPurchase(cart.getDateOfPurchase());
		       purchasehistory.setQuantity(cart.getQuantity());
		       purchasehistory.setUnitPrice(cart.getUnitPrice());
		       purchasehistory.setTotalPrice(cart.getTotalPrice());
		       purchasehistory.setTaxAmount(cart.getTaxAmount());
		       purchasehistorylist.add(purchasehistory);
		    }
	        purchasehistoryrepository.saveAll(purchasehistorylist);
		    cartrepository.deleteAll();
		}
		else {
			List<StockEntity> stocklist = new ArrayList<StockEntity>();
		    for(CartEntity cart : cartrepository.findAll()) {
		       StockEntity stock = ((List<StockEntity>)stockrepository.findAll()).stream().filter(predicate -> predicate.getBrandName().equalsIgnoreCase(cart.getBrandName())).findFirst().get();
		       stock.setStocks(cart.getQuantity()+stock.getStocks());
		       stocklist.add(stock);
		    }
	        stockrepository.saveAll(stocklist);
		    cartrepository.deleteAll();
		}
	    return true;
	}

	@Override
	public List<PurchaseHistoryEntity> getAllHistory() {
		return (List<PurchaseHistoryEntity>) purchasehistoryrepository.findAll();
	}

	@Override
	public PurchaseHistoryEntity getPurchaseHistory(int purchaseHistoryId) {
		return purchasehistoryrepository.findById(purchaseHistoryId).get();
	}

}
