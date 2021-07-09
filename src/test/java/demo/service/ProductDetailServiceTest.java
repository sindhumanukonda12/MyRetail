package demo.service;

import com.mongodb.MongoException;
import demo.dao.MyRetailRepository;
import demo.exceptions.ProductNotFoundException;
import demo.model.ProductDetailsResponse;
import demo.model.ProductPrizeDetails;
import demo.restclient.RedSkyApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailServiceTest {

    @Mock
    RedSkyApi redSkyApi;

    @Mock
    MyRetailRepository myRetailRepository;

    @InjectMocks
    ProductDetailService productDetailService;

    @Test
    public void getProductDetailsTest(){

        given(redSkyApi.invokeRedSkyApi(anyInt())).willReturn("Television");
        ProductPrizeDetails productPrizeDetails = new ProductPrizeDetails(1234, 13.4F,"USD");
        given(myRetailRepository.getProductDetails(anyInt())).willReturn(productPrizeDetails);
        ProductDetailsResponse productDetailsResponse=productDetailService.getProductDetails(1234);
        assertEquals(1234,productDetailsResponse.getId());
        assertEquals("Television",productDetailsResponse.getName());
        assertEquals("USD",productDetailsResponse.getCurrentPrice().getCurrencyCode());

    }

    @Test(expected= ProductNotFoundException.class)
    public void getProductDetailsTestException(){

        given(redSkyApi.invokeRedSkyApi(anyInt())).willThrow(ProductNotFoundException.class);
        ProductDetailsResponse productDetailsResponse=productDetailService.getProductDetails(1234);

    }

    @Test(expected= ProductNotFoundException.class)
    public void getProductDetailsTestSQlException(){

        given(redSkyApi.invokeRedSkyApi(anyInt())).willReturn("Television");
        ProductPrizeDetails productPrizeDetails = new ProductPrizeDetails(1234, 13.4F,"USD");
        given(myRetailRepository.getProductDetails(anyInt())).willReturn(null);
        ProductDetailsResponse productDetailsResponse=productDetailService.getProductDetails(1234);
        assertEquals(1234,productDetailsResponse.getId());
        assertEquals("Television",productDetailsResponse.getName());
        assertEquals("USD",productDetailsResponse.getCurrentPrice().getCurrencyCode());

    }


}
