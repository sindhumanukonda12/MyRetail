package demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Document(collection = "ProductPrizeDetails")
public class ProductPrizeDetails {

	@Id
	private int id;
	
	private float value;
	
	private String currencyCode;	

}
