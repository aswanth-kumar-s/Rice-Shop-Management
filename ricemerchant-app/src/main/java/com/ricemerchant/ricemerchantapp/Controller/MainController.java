package com.ricemerchant.ricemerchantapp.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ricemerchant.ricemerchantapp.Service.RiceMerchantService;

@RequestMapping("/ricemerchant")
@Controller
public class MainController {
	
	@Autowired
	private RiceMerchantService ricemerchantservice;
	
	@RequestMapping(value = "/registerbrandpage", method = {RequestMethod.POST, RequestMethod.GET})
	public String registerBrandPage(Model model) {
		  model.addAttribute("activeLink", "Register Brand");
		  return "Register Brand";
	}
	
	@RequestMapping(value = "/registerbrand/save", method = RequestMethod.POST)
	public String registerBrand(@RequestParam("brandname") String brandName, @RequestParam("kgvalue") int kg, RedirectAttributes redirectattributes) {
		  if(ricemerchantservice.registerBrand(brandName, kg))
			  redirectattributes.addFlashAttribute("success", "Registrated successfully");
		  else
			  redirectattributes.addFlashAttribute("errorinbrandname", "Brand already registered");
		  return "redirect:/ricemerchant/registerbrandpage";
	}
	
	@RequestMapping(value = "/stocks/registerstockspage", method = {RequestMethod.POST, RequestMethod.GET})
	public String registerStocks(Model model) {
		model.addAttribute("brandslist", ricemerchantservice.getAllBrands());
		model.addAttribute("activeLink", "Register Stocks");
		return "Register Stocks";
	}
	
	@RequestMapping(value = "/stocks/registerstocks/save", method = RequestMethod.POST)
	public String registerStocksPage(@RequestParam("brandname") String brandName, @RequestParam("dateofdelivery") String dateOfDelivery, @RequestParam("dateofpurchase") String dateOfPurchase, @RequestParam("miscellaneousamount") float miscellaneousAmount, @RequestParam("quantity") int quantity, @RequestParam("riceamount") float riceAmount, @RequestParam("settlementperiodvalue") int settlementPeriodValue, RedirectAttributes redirectattributes) {
		if(ricemerchantservice.registerStocks(brandName, dateOfDelivery, dateOfPurchase, miscellaneousAmount, quantity, riceAmount, settlementPeriodValue))
			redirectattributes.addFlashAttribute("success", "Registered successfully");
		else 
			redirectattributes.addFlashAttribute("error", "Registration unsuccessful");
	    return "redirect:/ricemerchant/stocks/registerstockspage";
	}
	
	@RequestMapping(value = "/stocks/stockhistory", method = RequestMethod.GET)
	public String stockHistoryPage(Model model) {
		model.addAttribute("activeLink", "Stock History");
		model.addAttribute("stockHistoryList", ricemerchantservice.getAllStockHistory());
		model.addAttribute("today", LocalDate.now().toString());
		return "Stocks History";
	}
    
	@GetMapping("/")
	public String stocksPageRedirect(Model model) {
		model.addAttribute("activeLink", "Stocks");
		return "Stocks";
	}
	
	@GetMapping("/stocks")
	public String stockPage(Model model) {
		model.addAttribute("activeLink", "Stocks");
		model.addAttribute("stockslist", ricemerchantservice.getAllStocks());
		return "Stocks";
	}
	
	@GetMapping("/stocks/addsettlement/{stockHistoryId}")
	public String addSettlementPage(@PathVariable(value = "stockHistoryId" ) int stockHistoryId, Model model) {
		model.addAttribute("activeLink", "Stock History");
		model.addAttribute("stockHistoryId", stockHistoryId);
		return "Settlement"; 
	}
	
	@PostMapping("/stocks/addsettlement")
	public String addSettlement(@RequestParam(value="stockHistoryId") int stockHistoryId, @RequestParam(value="settlementType") String settlementType, @RequestParam(value="chequeNumber", defaultValue = "0") long chequeNumber, @RequestParam(value="settlementAmount") float settlementAmount, RedirectAttributes redirectattributes) {
		if(ricemerchantservice.addSettlement(stockHistoryId, settlementType, chequeNumber, settlementAmount)) redirectattributes.addFlashAttribute("success", "Stock Added successfully");
		else redirectattributes.addFlashAttribute("error", "Stock add unsuccessful");
		return "redirect:/ricemerchant/stocks/stockhistory";
	}
    
	
	@GetMapping("/stocks/stockhistoryupdate/{id}")
	public String getStock(@PathVariable (value = "id" ) int id, Model model) {
		  model.addAttribute("stockHistory", ricemerchantservice.getStockHistory(id));
		  return "Stock History Update"; 
	}
	
	@PostMapping("/stocks/stockhistoryupdate")
	public String updateStockHistory(@RequestParam("stockhistoryId") int stockhistoryId, @RequestParam("dateofdelivery") String dateOfDelivery, @RequestParam("dateofpurchase") String dateOfPurchase, @RequestParam("miscellaneousamount") float miscellaneousAmount, @RequestParam("quantity") int quantity, @RequestParam("riceamount") float riceAmount, @RequestParam("settlementperiodvalue") int settlementPeriodValue, RedirectAttributes redirectattributes) {
		  if(ricemerchantservice.updateStockHistory(stockhistoryId, dateOfDelivery, dateOfPurchase, miscellaneousAmount, quantity, riceAmount, settlementPeriodValue))
			  redirectattributes.addFlashAttribute("success", "Edited successfully");
		  else
			  redirectattributes.addFlashAttribute("error", "Edit unsuccessful");
		  return "redirect:/ricemerchant/stocks/stockhistory";
	}
	
	@GetMapping("/stocks/stockhistorydelete/{id}")
	public String deleteStockHistory(@PathVariable(value="id") int id, RedirectAttributes redirectattributes) {
		  if(ricemerchantservice.deleteStockHistory(id)) redirectattributes.addFlashAttribute("success", "Deleted successfully");
		  else redirectattributes.addFlashAttribute("error", "Delete unsuccessful");
		  return "redirect:/ricemerchant/stocks/stockhistory";
	}
	
	@GetMapping("/stocks/stockshistory")
	public String stocksHistoryPage(Model model) {
		model.addAttribute("activeLink", "Stocks History");
		return "Stocks History";
	}
	
	@GetMapping("/stocks/customerpurchase")
	public String customerPurchase(Model model) {
		model.addAttribute("cartlist", ricemerchantservice.getAllCartProducts());
		model.addAttribute("brandlist", ricemerchantservice.getAllBrandsInStockEntity());
		model.addAttribute("activeLink", "Customer Purchase");
		return "Customer Purchase";
	}
	
	@PostMapping("/stocks/customerpurchase/addtocart")
	public String addToCart(@RequestParam(value="brandName", defaultValue="") String brandName, @RequestParam(value="quantity") int quantity, @RequestParam(value="unitPrice") float unitPrice, RedirectAttributes redirectattributes) {
	    if(brandName.equalsIgnoreCase("Select Brand Name")) {
	    	redirectattributes.addFlashAttribute("error", "Brand name cannot be empty");
	    	redirectattributes.addFlashAttribute("quantity", quantity);
	    	redirectattributes.addFlashAttribute("unitPrice", unitPrice);
	    }
		else if(ricemerchantservice.addToCart(brandName, quantity, unitPrice, ricemerchantservice.getAllStocks()))
			redirectattributes.addFlashAttribute("success", "Added successfully");
		else {
	    	redirectattributes.addFlashAttribute("quantity", quantity);
			redirectattributes.addFlashAttribute("error", "Out of requested Stock");
		}
		return "redirect:/ricemerchant/stocks/customerpurchase";
	}
	
	@GetMapping("/stocks/customerpurchase/deletefromcart/{cartId}")
	public String deleteStock(@PathVariable(value="cartId") int cartId, Model model, RedirectAttributes redirectattributes) {
		  if(ricemerchantservice.deleteFromCart(cartId, ricemerchantservice.getAllStocks()))
	    	  redirectattributes.addFlashAttribute("success", "Delete successful");
		  else
			  redirectattributes.addFlashAttribute("error", "Delete unsuccessful");
		  return "redirect:/ricemerchant/stocks/customerpurchase";
	}
	
	@GetMapping("/stocks/customerpurchase/print") 
	public String printPage(Model model) {
		model.addAttribute("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		model.addAttribute("time", new SimpleDateFormat("hh:mm:ss a").format(new Date()));
		model.addAttribute("cartlist", ricemerchantservice.getAllCartProducts());
		return "Print";
	}
	
	@GetMapping("/stocks/customerpurchase/emptycart/{callfrom}")
	public String emptyCart(@PathVariable(value="callfrom") boolean callfrom) {
		ricemerchantservice.emptyCart(callfrom);
		return "redirect:/ricemerchant/stocks/customerpurchase";
	}
	
	@GetMapping("/stocks/purchasehistory")
	public String purchaseHistoryPage(Model model) {
		model.addAttribute("activeLink", "Purchase History");
		model.addAttribute("historylist", ricemerchantservice.getAllHistory());
		return "Purchase History";
	}
	
	@GetMapping("/stocks/purchasehistory/print/{purchaseHistoryId}")
	public String purchaseHistoryPrint(@PathVariable(value="purchaseHistoryId") int purchaseHistoryId, Model model) {
		model.addAttribute("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		model.addAttribute("time", new SimpleDateFormat("hh:mm:ss a").format(new Date()));
		model.addAttribute("purchaseHistory", ricemerchantservice.getPurchaseHistory(purchaseHistoryId));
		return "History Print";
	}
	
}
