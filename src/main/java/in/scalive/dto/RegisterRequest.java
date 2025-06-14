package in.scalive.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	@Email(message = "Please provide a valid email")
	@NotNull(message = "Email should not be null")
	private String email;
	@NotNull(message = "Password should not be null")
	@Size(min = 8, max = 2000, message = "Password should be atleast 8 characters long")
	private String pwd;
	@NotNull(message = "role should not be null")
	private String role;
	@PositiveOrZero(message = "balance should be zero or greater")
	private double balance;
}
