package demo.service;

import demo.model.ProductDetailsResponse;
import demo.model.ProductPrizeDetails;

public interface IProductDetailService {
	
	ProductDetailsResponse getProductDetails(Integer id);

    ProductPrizeDetails updateProductDetails(ProductDetailsResponse productDetailResponse);
}
