package in.scalive.service;

import in.scalive.dto.TransferRequest;
import in.scalive.exception.EmailNotFoundException;
import in.scalive.exception.InsufficientBalanceException;
import in.scalive.model.Customer;
import in.scalive.model.Transaction;
import in.scalive.repository.CustomerRepository;
import in.scalive.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankService {
	
	@Autowired
	private TransactionRepository transRepo;
	@Autowired
	private CustomerRepository custRepo;
	
	@PreAuthorize("hasRole('USER')")
	public String getAccount() {
		String email = getLoggedInUserEmail();
		Customer cust = custRepo.findByEmail(email).get();
		return "Account Details:[Email:" + email + ",Balance:" + cust.getBalance() + ",AccNo:" + cust.getId() + "]";
	}
	
	@PreAuthorize("hasRole('USER')")
	public String getBalance() {
		String email = getLoggedInUserEmail();
		Customer cust = custRepo.findByEmail(email).get();
		return "Available balance:" + cust.getBalance();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public String getReports() {
		return "Financial Reports for the year 2024-2025";
	}
	
	private String getLoggedInUserEmail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
	
	@PreAuthorize("hasRole('USER')")
	@Transactional
	public Transaction withdraw(double amt) {
		String email = getLoggedInUserEmail();
		Customer cust = custRepo.findByEmail(email).get();
		if(cust.getBalance() < amt) {
			throw new InsufficientBalanceException("Insifficient balance");
		}
		cust.setBalance(cust.getBalance() - amt);
		custRepo.save(cust);
		Transaction trans = new Transaction(email, amt, "WITHDRAW", LocalDateTime.now());
		return transRepo.save(trans);
	}
	
	@PreAuthorize("hasRole('USER')")
	@Transactional
	public Transaction deposit(double amt) {
		String email = getLoggedInUserEmail();
		Customer cust = custRepo.findByEmail(email).get();
		cust.setBalance(cust.getBalance() + amt);
		custRepo.save(cust);
		Transaction trans = new Transaction(email, amt, "DEPOSIT", LocalDateTime.now());
		return transRepo.save(trans);
	}
	
	@PostAuthorize("returnObject.isEmpty() or returnObject[0].email==authentication.name or hasRole('ADMIN')")
	public List<Transaction> getTransactions(String email) {
		return transRepo.findByEmail(email);
	}
	
	@Transactional
	@PreAuthorize("hasRole('USER')")
	public String transfer(TransferRequest transferRequest) {
		Customer recipient = custRepo.findByEmail(transferRequest.getToEmail())
				.orElseThrow(() -> new EmailNotFoundException("Email: "+transferRequest.getToEmail()+" not found. Please provide a valid email id for money transfer."));
		String email = getLoggedInUserEmail();
		Customer sender = custRepo.findByEmail(email).get();
		if(sender.getBalance() < transferRequest.getAmount()) {
			throw new InsufficientBalanceException("Insufficient balance");
		}
		sender.setBalance(sender.getBalance()-transferRequest.getAmount());
		custRepo.save(sender);
		recipient.setBalance(recipient.getBalance()+transferRequest.getAmount());
		custRepo.save(recipient);
		Transaction trans = new Transaction(sender.getEmail(), transferRequest.getAmount(),
				"TRANSFER_OUT", LocalDateTime.now());
		transRepo.save(trans);
		Transaction trans2 = new Transaction(recipient.getEmail(), transferRequest.getAmount(),
				"TRANSFER_IN", LocalDateTime.now());
		transRepo.save(trans2);
		String response = "Amount: %s transferred from %s to %s"
				.formatted(transferRequest.getAmount(), email, recipient.getEmail());
		return response;
	}
}
