package com.cust.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	RestTemplate restTemplate;
	
	
	@GetMapping
	public String welcome() {
		return "Welcome Eureka client-RestTemplate calling ->CustomerController";
	}
	
	@GetMapping("/productlist")
	public ResponseEntity<String> getproductList() {
		//String uri="http://localhost:8081/product/";
		String uri="http://orderservice/product/";
		ResponseEntity<String> response=restTemplate.getForEntity(uri, String.class);
		return response;
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<String> getproductByCustId(@PathVariable("id")long id) {
		String uri="http://orderservice/product/"+id;
		ResponseEntity<String> response=restTemplate.getForEntity(uri, String.class);
		return response;
	}
	
	@GetMapping("/productById")
	public ResponseEntity<String> getproductByOrderId() {//@RequestParam working fine
		String uri="http://orderservice/product/prodreqById";
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity=new HttpEntity<String>(headers);
		UriComponentsBuilder uriBuilder =UriComponentsBuilder.fromHttpUrl(uri).queryParam("id", 123);
		ResponseEntity<String> response=restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET,entity,String.class);
		return response;
	}
	
	@PostMapping("/createproduct")
	public ResponseEntity<String> createproduct(){
		String uri="http://orderservice/product/createproduct";
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity=new HttpEntity<String>(headers);
		UriComponentsBuilder uriBuilder =UriComponentsBuilder.fromHttpUrl(uri)
				.queryParam("id", 963)
				.queryParam("pname", "switch");
		ResponseEntity<String> response=restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST,entity,String.class);
		return response;
	}
	
	
	
	
	
	
	

   @Bean
   @LoadBalanced
   public RestTemplate getRestTemplate() {
      return new RestTemplate();
   }
	
	
	
	
}
