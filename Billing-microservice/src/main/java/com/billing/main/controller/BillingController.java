package com.billing.main.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.main.exception.ResourceNotFoundException;
import com.billing.main.model.Billing;
import com.billing.main.service.BillingService;
import com.inventory.main.model.Customer;
import com.inventory.main.model.Product;
import com.inventory.main.service.CustomerService;
import com.inventory.main.service.ProductService;
import com.inventory.main.service.StockService;

@RestController
@RequestMapping("/billing")
public class BillingController {
	
	@Autowired
	private BillingService billingService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private StockService stockService;
	
	@PostMapping("/add/{customerId}")
	public ResponseEntity<?> postBilling(@RequestBody Billing billing,@PathVariable("customerId") int cusId) {
		
		Customer customer = customerService.getById(cusId);
		
		System.out.println(customer);
		if(customer == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid customer ID given");
		
		billing.setCustomer(customer);
		billing.setDate(LocalDate.now());
		
		List<Integer> productIds = billing.getProductIds();
		
		

        // Fetch the products based on the list of product IDs
        List<Product> products = productService.getProductsByIds(productIds);
        
        System.out.println(products);
        // Set the fetched products to the billing object
        billing.setProduct(products);
        
        List<Double> productPrices = billingService.calculateProductPrices(products, billing.getProductQuantities());
        billing.setProductPrices(productPrices);

        // Calculate net price
        double netPrice = billingService.calculateNetPrice(products, billing.getProductQuantities());
        billing.setNetPrice(netPrice);
        
        billingService.updateCustomerLoyaltyPoints(customer, netPrice);
		
        billingService.updateLoyaltyPlusNetPrice(customer,netPrice,billing);
        
//        if (billing.getId() < 0) {
            billing = billingService.insert(billing);
//        }
		
		 for (int i = 0; i < products.size(); i++) {
	            Product product = products.get(i);
	            System.out.println(product);
	            int quantityChange = -billing.getProductQuantities().get(i);
	            try {
					stockService.updateStockQuantity(product, quantityChange);
				} catch (ResourceNotFoundException e) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
				}
	        }
		
		return ResponseEntity.status(HttpStatus.OK).body(billing);
	}
	
	@GetMapping("/all")
	public List<Billing> getAll() {
		return billingService.getAll();
	}
	@GetMapping("/filter")
    public ResponseEntity<List<Billing>> filterBillings(
        @RequestParam(required = false) String date,
        @RequestParam(required = false) String customer,
        @RequestParam(required = false) Double netPrice
    ) {
        List<Billing> filteredBillings = billingService.filterBillings(date, customer, netPrice);
        return ResponseEntity.ok(filteredBillings);
    }

}
