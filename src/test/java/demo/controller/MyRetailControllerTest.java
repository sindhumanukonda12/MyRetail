package demo.controller;


import demo.exceptions.ProductNotFoundException;
import demo.model.CurrentPrice;
import demo.model.ProductDetailsResponse;
import demo.model.ProductPrizeDetails;
import demo.service.ProductDetailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MyRetailControllerTest {

    @InjectMocks
    MyRetailController myRetailController;

    @Mock
    ProductDetailService productDetailService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(myRetailController).build();
    }

    @Test
    public void getProductDetailsTest() throws Exception {
        ProductDetailsResponse productDetailsResponse =  getProductDetailsResponse();
        when(productDetailService.getProductDetails(765489)).thenReturn(productDetailsResponse);

        MvcResult mockresults = mockMvc.perform(get("/myRetail/v1/products/765489").accept(MediaType.APPLICATION_JSON))
                .andReturn();
        mockMvc.perform(asyncDispatch(mockresults))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(765489))
                .andExpect(jsonPath("$.name").value("Television"))
                .andExpect(jsonPath("$.current_price.value").value(55.44))
                .andExpect(jsonPath("$.current_price.currency_code").value("USD"))
                .andReturn();
    }

    @Test
    public void getProductDetailsExceptionTest() throws Exception {
        ProductDetailsResponse productDetailsResponse =  getProductDetailsResponse();
        when(productDetailService.getProductDetails(765489)).thenThrow(ProductNotFoundException.class);

        MvcResult mockresults = mockMvc.perform(get("/myRetail/v1/products/765489").accept(MediaType.APPLICATION_JSON))
                .andReturn();
        mockMvc.perform(asyncDispatch(mockresults))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void updateProductDetailsTest() throws Exception {
        ProductPrizeDetails productDetails =  getProductPriceDetails();
        when(productDetailService.updateProductDetails(any())).thenReturn(productDetails);

            mockMvc.perform(put("/myRetail/v1/products/765489").contentType(MediaType.APPLICATION_JSON_VALUE).content(postResponseBody()))
                .andExpect(status().isOk()).andReturn();
    }


    public ProductPrizeDetails getProductPriceDetails(){
        ProductPrizeDetails productPrizeDetails = new ProductPrizeDetails(765489,34.55F,"USD");
        return productPrizeDetails;

    }

    private String postResponseBody() {
        return "{\n" +
                "    \"id\": 765489,\n" +
                "    \"name\": \"The Big Lebowski (Blu-ray)\",\n" +
                "    \"current_price\": {\n" +
                "        \"value\": 15.46,\n" +
                "        \"currency_code\": \"USD\"\n" +
                "    }\n" +
                "}";
    }

    public ProductDetailsResponse getProductDetailsResponse(){
        CurrentPrice currentPrice = new CurrentPrice(55.44F,"USD");
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse(765489,"Television",currentPrice,null);
        return productDetailsResponse;

    }


}
