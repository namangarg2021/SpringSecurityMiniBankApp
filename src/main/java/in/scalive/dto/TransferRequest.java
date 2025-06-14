package in.scalive.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
	@Email(message = "Please provide a valid email")
	@NotNull(message = "Email should not be null")
	private String toEmail;
	
	@NotNull(message = "amount should not be null")
	@Positive(message = "amount should be greater than zero")
	private Double amount;
}
