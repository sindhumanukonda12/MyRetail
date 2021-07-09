package demo.controller;

import demo.constants.MyRetailConstants;
import demo.exceptions.ProductNotFoundException;
import demo.model.ProductDetailsResponse;
import demo.model.ProductPrizeDetails;
import demo.service.IProductDetailService;
import demo.service.ProductDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;


import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("myRetail/v1")
@Slf4j
public class MyRetailController {
	
	@Autowired
	private IProductDetailService productDetailService;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<Object> getProductDetails(@PathVariable("id") int id){
        DeferredResult<Object> deferredResult = new DeferredResult<>();

        Observable<ProductDetailsResponse> productDetailsObservable = Observable
                .fromCallable(new Callable<ProductDetailsResponse>() {
                    @Override
                    public ProductDetailsResponse call() throws ProductNotFoundException {
						return productDetailService.getProductDetails(id);

                    }
                });
                Subscriber<ProductDetailsResponse> productDetailsSubscriber = new Subscriber<ProductDetailsResponse>() {
        			@Override
        			public void onNext(ProductDetailsResponse response) {
						log.debug("Deferred result has received a response");
        				deferredResult.setResult(response); // Result is set to the
        													// DeferredResult
        			}
        			@Override
        			public void onError(Throwable ex) {
						ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse(id,null,null,ex.getMessage());
        				log.error("Error while retrieving product details for {} is {}",id,ex.getMessage());
        				deferredResult.setErrorResult(
        						new ResponseEntity<Object>(productDetailsResponse, HttpStatus.NOT_FOUND));
        			}

        			@Override
        			public void onCompleted() {
        			log.info("Successfull completed");
        			}
        		};

        		productDetailsObservable.subscribe(productDetailsSubscriber);
        		return deferredResult;
    }

	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<Object> updateProductDetails(@PathVariable("id") int id, @RequestBody ProductDetailsResponse productDetailsResponse){
		DeferredResult<Object> deferredResult = new DeferredResult<>();

		Observable<ProductPrizeDetails> productDetailsObservable = Observable
				.fromCallable(new Callable<ProductPrizeDetails>() {
					@Override
					public ProductPrizeDetails call() throws ProductNotFoundException {
						if(id == productDetailsResponse.getId()) {
							ProductPrizeDetails productPrizeDetails= productDetailService.updateProductDetails(productDetailsResponse);
							return productPrizeDetails;
						}
						else{
							throw new ProductNotFoundException("ID is not matching id in request Body");
						}

					}
				});
		Subscriber<ProductPrizeDetails> productDetailsSubscriber = new Subscriber<ProductPrizeDetails>() {
			@Override
			public void onNext(ProductPrizeDetails response) {
				log.debug("Deferred result has received a response");
				deferredResult.setResult(response); // Result is set to the
				// DeferredResult
			}
			@Override
			public void onError(Throwable ex) {
				ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse(id,null,null,ex.getMessage());
				log.error("Error while retrieving product details for {} is {}",id,ex.getMessage());
				deferredResult.setErrorResult(
						new ResponseEntity<Object>(productDetailsResponse, HttpStatus.NOT_FOUND));
			}

			@Override
			public void onCompleted() {
				log.info("Successfull completed");
			}
		};

		productDetailsObservable.subscribe(productDetailsSubscriber);
		return deferredResult;
	}


}
