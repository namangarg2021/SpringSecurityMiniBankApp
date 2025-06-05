package in.scalive;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHashingExample {
	public static void main(String[] args) {
		//Simulating Registration
		String originalPwd = "naman";
		PasswordEncoder pEncoder = new BCryptPasswordEncoder(24);
		String encodedPwd = pEncoder.encode(originalPwd);
		System.out.println("original:" + originalPwd);
		System.out.println("Hashed pwd:" + encodedPwd);
		
		//Simulate login
		originalPwd = "naman";
		boolean result = pEncoder.matches(originalPwd, encodedPwd);
		System.out.println("Res:" + result);
	}
}
