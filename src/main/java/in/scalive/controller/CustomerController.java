package in.scalive.controller;

import in.scalive.dto.RegisterRequest;
import in.scalive.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class CustomerController {
	@Autowired
	private CustomerService custServ;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody @Valid RegisterRequest request) {
		String message = custServ.registerCustomer(request);
		return ResponseEntity.ok(message);
	}
}
