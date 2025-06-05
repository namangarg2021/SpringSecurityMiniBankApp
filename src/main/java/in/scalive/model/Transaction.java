package in.scalive.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private double amount;
	private String type;
	private LocalDateTime timestamp;
	
	public Transaction(String email, double amount, String type, LocalDateTime timestamp) {
		
		this.email = email;
		this.amount = amount;
		this.type = type;
		this.timestamp = timestamp;
	}
	
}
