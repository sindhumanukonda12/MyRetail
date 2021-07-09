package demo.dao;

import javax.annotation.PostConstruct;

import demo.model.ProductDetailsResponse;
import demo.model.ProductPrizeDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@Slf4j
public class MyRetailRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@PostConstruct
	public void saveProductDetails() {
		List<ProductPrizeDetails> productPrizeDetailsList = new ArrayList<ProductPrizeDetails>();
		ProductPrizeDetails productPrizeDetails = new ProductPrizeDetails(13860428, 15.45F, "USD");
		ProductPrizeDetails productPrizeDetails1 = new ProductPrizeDetails(15117729, 13.44F, "USD");
		productPrizeDetailsList.add(productPrizeDetails);
		productPrizeDetailsList.add(productPrizeDetails1);
		mongoTemplate.insertAll(productPrizeDetailsList);
	}
	
	
	public ProductPrizeDetails getProductDetails(int id) {
		return mongoTemplate.findById(id,ProductPrizeDetails.class);
	}

	public ProductPrizeDetails updateProductDetails(ProductPrizeDetails productDetails) {
		 return mongoTemplate.save(productDetails);
	}

}
