package demo.restclient;

import demo.constants.MyRetailConstants;
import demo.exceptions.ProductNotFoundException;
import demo.model.RedSky;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.json.JSONObject;


@Service
@Slf4j
public class RedSkyApi {
	
	@Autowired
	private RestTemplate restTemplate;

	@Value("${redsky.url}")
    private String redSkyUrl;
	
	public String invokeRedSkyApi(Integer id) throws ProductNotFoundException {
		String title = null;
		ResponseEntity<RedSky> responseEntity = null;
		String url = String.join("", redSkyUrl,Integer.toString(id),MyRetailConstants.REDSKY_URL_EXCLUSIONS);
        try {
            responseEntity = restTemplate.getForEntity(url, RedSky.class);
            RedSky responseStr = responseEntity.getBody();
            title = responseStr.getProduct().getItem().getProductDescription().getTitle();
        } 
        catch(RestClientException e) {
           throw new ProductNotFoundException("Exception occured while invoking redsky : "+e.getMessage());
        }catch(Exception e) {
			throw new ProductNotFoundException("Title is null : "+e.getMessage());
		}
		return title;
	}

}
