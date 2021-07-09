package demo.restClient;

import demo.exceptions.ProductNotFoundException;
import demo.model.Item;
import demo.model.Product;
import demo.model.ProductDescription;
import demo.model.RedSky;
import demo.restclient.RedSkyApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RedSkyApiTest  {

    @Mock
    RestTemplate restTemplateMock;

    @Mock
    ResponseEntity<RedSky> responseEntityMock;

    @InjectMocks
    RedSkyApi redSkyApiMock;

    @Test
    public void invokeRedSkyApi(){
        ReflectionTestUtils.setField(redSkyApiMock, "redSkyUrl", "http://testurl:8888/");
        given(restTemplateMock.getForEntity(anyString(), Matchers.eq(RedSky.class))).willReturn(responseEntityMock);
        given(responseEntityMock.getBody()).willReturn(responseBody());
        String title = redSkyApiMock.invokeRedSkyApi(13860428);
        assertEquals("TELEVISION",title);

    }

    @Test(expected= ProductNotFoundException.class)
    public void invokeRedSkyApiException(){
        ReflectionTestUtils.setField(redSkyApiMock, "redSkyUrl", "http://testurl:8888/");
        given(restTemplateMock.getForEntity(anyString(), any())).willThrow(ProductNotFoundException.class);
        String title = redSkyApiMock.invokeRedSkyApi(13860428);
        assertEquals("TELEVISION",title);

    }

    private RedSky responseBody() {
        ProductDescription productDescription = new ProductDescription();
        Item item = new Item();
        Product product = new Product();
       RedSky redSkyApi = new RedSky();
        productDescription.setTitle("TELEVISION");
        item.setProductDescription(productDescription);
        product.setItem(item);
        redSkyApi.setProduct(product);
        return redSkyApi;
    }


}
