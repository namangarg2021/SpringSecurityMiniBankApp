package in.scalive.service;

import in.scalive.dto.RegisterRequest;
import in.scalive.exception.EmailAlreadyExistsException;
import in.scalive.model.Customer;
import in.scalive.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public String registerCustomer(RegisterRequest request) {
		if(repo.findByEmail(request.getEmail()).isPresent()) {
			throw new EmailAlreadyExistsException("User already exists with email:" + request.getEmail());
		}
		Customer customer = new Customer();
		customer.setEmail(request.getEmail());
		customer.setPwd(encoder.encode(request.getPwd()));
		if(request.getRole().equalsIgnoreCase("admin"))
			request.setRole("ROLE_ADMIN");
		else
			request.setRole("ROLE_USER");
		customer.setBalance(request.getBalance());
		repo.save(customer);
		return "Registration successful for :" + request.getEmail();
	}
	
}
