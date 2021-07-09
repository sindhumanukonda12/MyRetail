package demo.service;

import demo.dao.MyRetailRepository;
import demo.exceptions.ProductNotFoundException;
import demo.model.CurrentPrice;
import demo.model.ProductDetailsResponse;
import demo.model.ProductPrizeDetails;
import demo.restclient.RedSkyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailService implements IProductDetailService {
	
	@Autowired
	private RedSkyApi redSkyApi;

	@Autowired
	private MyRetailRepository myRetailRepository;

	@Override 
	public ProductDetailsResponse getProductDetails(Integer id) throws ProductNotFoundException{
		ProductDetailsResponse productDetailsResponse;


		String title = redSkyApi.invokeRedSkyApi(id);

		try {
			ProductPrizeDetails productPrizeDetails = myRetailRepository.getProductDetails(id);
			CurrentPrice currentPrice = new CurrentPrice(productPrizeDetails.getValue(),productPrizeDetails.getCurrencyCode());
			productDetailsResponse= new ProductDetailsResponse(id,title,currentPrice,null);
		}
		catch(Exception e){
			throw new ProductNotFoundException("Product not found in Database");
		}
		return productDetailsResponse;
		
	}

	@Override
	public ProductPrizeDetails updateProductDetails(ProductDetailsResponse productDetailResponse){
		ProductPrizeDetails productPrizeDetails = myRetailRepository.getProductDetails(productDetailResponse.getId());
		if(productPrizeDetails.getId()==0){
			throw new ProductNotFoundException("Product not found in Database");
		}
		ProductPrizeDetails productUpdate = null;
		try {
			ProductPrizeDetails productPrizeDetailsModified = new ProductPrizeDetails(productDetailResponse.getId(), productDetailResponse.getCurrentPrice().getValue(), productDetailResponse.getCurrentPrice().getCurrencyCode());
			productUpdate = myRetailRepository.updateProductDetails(productPrizeDetailsModified);
		}
		catch(Exception exe){
			throw new ProductNotFoundException("Could not update the details");
		}
		return productUpdate;
	}

}
