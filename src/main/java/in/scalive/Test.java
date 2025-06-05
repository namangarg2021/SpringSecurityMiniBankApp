package in.scalive;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Test {
	public static void main(String[] args) {
		//Simulating Registration
		String originalPwd = "sachin";
		PasswordEncoder pEncoder = new BCryptPasswordEncoder(24);
		String encodedPwd = pEncoder.encode(originalPwd);
		System.out.println("original:" + originalPwd);
		System.out.println("Hashed pwd:" + encodedPwd);
		
		//Simulate login
		originalPwd = "sachin";
		boolean result = pEncoder.matches(originalPwd, encodedPwd);
		System.out.println("Res:" + result);
		
		
	}
}
