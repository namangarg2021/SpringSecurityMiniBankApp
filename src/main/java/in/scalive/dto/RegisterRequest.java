package in.scalive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	@Email
	@NotNull
	private String email;
	@NotNull
	@Size(min = 8, max = 2000)
	private String pwd;
	@NotNull
	private String role;
	@Min(value = 0)
	private double balance;
}
