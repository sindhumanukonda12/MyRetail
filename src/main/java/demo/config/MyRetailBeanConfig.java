package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyRetailBeanConfig {
	
	@Bean
    public RestTemplate restTemplate(){
    	return new RestTemplate(getClientHttpRequestFactory());
    }
    
    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
          = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(60000);
        return clientHttpRequestFactory;
    }

}
