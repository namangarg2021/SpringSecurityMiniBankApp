package in.scalive.service;

import in.scalive.model.Customer;
import in.scalive.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecureBankService implements UserDetailsService {
	@Autowired
	private CustomerRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer cust = repo.findByEmail(username).orElse(null);
		if(cust == null) {
			throw new UsernameNotFoundException("User details not found for:" + username);
		}
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(cust.getRole()));
		User user = new User(cust.getEmail(), cust.getPwd(), authorities);
		return user;
	}
	
}
