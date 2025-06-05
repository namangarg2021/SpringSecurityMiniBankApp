package in.scalive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
	@NotNull
	@Email
	private String toEmail;
	
	@NotNull
	@Min(value = 0)
	private Double amount;
}
