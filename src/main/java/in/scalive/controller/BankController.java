package in.scalive.controller;

import in.scalive.dto.TransferRequest;
import in.scalive.model.Transaction;
import in.scalive.service.BankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BankController {
	@Autowired
	private BankService bserv;
	
	@GetMapping("/account")
	public String getAccount() {
		return bserv.getAccount();
	}
	
	@GetMapping("/balance")
	public String getBalance() {
		return bserv.getBalance();
	}
	
	@GetMapping("/about")
	public String getAbout() {
		return "Welcome To Secure Bank";
	}
	
	@GetMapping("/contact")
	public String getContact() {
		return "Contact us at:support@securebank.com";
	}
	
	@GetMapping("/reports")
	public String getReports() {
		return bserv.getReports();
	}
	
	@GetMapping("/transactions/{email}")
	public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String email) {
		List<Transaction> transList = bserv.getTransactions(email);
		return ResponseEntity.ok(transList);
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<Transaction> withdraw(@RequestBody Map<String, Double> request) {
		double amount = request.get("amount");
		Transaction transaction = bserv.withdraw(amount);
		return ResponseEntity.ok(transaction);
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<Transaction> deposit(@RequestBody Map<String, Double> request) {
		double amount = request.get("amount");
		Transaction transaction = bserv.deposit(amount);
		return ResponseEntity.ok(transaction);
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<String> transfer(@RequestBody @Valid TransferRequest transferRequest) {
		return ResponseEntity.ok(bserv.transfer(transferRequest));
	}
}
