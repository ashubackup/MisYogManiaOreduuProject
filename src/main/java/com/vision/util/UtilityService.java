package com.vision.util;

import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;


@Service
public class UtilityService 
{
	
	public PackRequest setDailyPackRequest
	(String type, String name, String price, String serviceName, String misDate,
	String subscriptions,String renewals,String unsubscriptions,
	String subscriptionRevenue,String renewalsRevenue, String totalRevenue )
	{
		
		// 1
		PackRequest packRequest = new PackRequest();
		
		packRequest.setType(type);
		packRequest.setName(name);
		packRequest.setPrice(price);
		packRequest.setServiceName(serviceName);
		packRequest.setMisDate(misDate);
		packRequest.setSubscriptions(subscriptions);
		packRequest.setRenewals(renewals);
		packRequest.setUnsubscriptions(unsubscriptions);
		packRequest.setSubscriptionRevenue(subscriptionRevenue);
		packRequest.setRenewalsRevenue(renewalsRevenue);
		packRequest.setTotalRevenue(totalRevenue);	
		return packRequest;
	
	}


// SubServiceRequest
	public SubServiceRequest setDailySubServiceRequest
	(String serviceName,String subServiceName,String status, String misDate,
	String subscriptions,String renewals,
	String subscriptionRevenue,String renewalsRevenue,String totalRevenue, 
	Integer totalBase, Integer totalActiveBase, Integer unsubscription,
	List<PackRequest> packList )
	{
		SubServiceRequest subServiceRequest = new SubServiceRequest();
		
		subServiceRequest.setServiceName(serviceName);
		subServiceRequest.setSubServiceName(subServiceName);
		subServiceRequest.setStatus(status);	
		subServiceRequest.setMisDate(misDate);
		subServiceRequest.setSubscriptions(subscriptions);
		subServiceRequest.setRenewals(renewals);
		subServiceRequest.setSubscriptionRevenue(subscriptionRevenue);
		subServiceRequest.setRenewalRevenue(renewalsRevenue);
		subServiceRequest.setTotalRevenue(totalRevenue);
		subServiceRequest.setTotalBase(totalBase);	
		subServiceRequest.setTotalActiveBase(totalActiveBase);	
		subServiceRequest.setUnsubscription(unsubscription);
		subServiceRequest.setPack(packList);;
		
		return subServiceRequest;
	}


	// MainServiceRequest 
	public MainServiceRequest setMainServiceRequest
	(String service,String misDate,String totalBase, String totalActiveBase,
	String subscriptions,String renewals,
	String unsubscriptions,String subscriptionRevenue,String renewalsRevenue,
	String totalRevenue, String usdRevenue,String callbackcount,
	String SubFailed, String revenueShare, String fame,String operator, String country,
	List<SubServiceRequest> subService )
	{
		MainServiceRequest mainServiceRequest = new MainServiceRequest();
		
		mainServiceRequest.setService(service);
		mainServiceRequest.setMisDate(misDate);
		mainServiceRequest.setTotalBase(totalBase);
		mainServiceRequest.setTotalActiveBase(totalActiveBase);
		mainServiceRequest.setSubscriptions(subscriptions);
		mainServiceRequest.setRenewals(renewals);
		mainServiceRequest.setUnsubscriptions(unsubscriptions);
		mainServiceRequest.setSubscriptionRevenue(subscriptionRevenue);
		mainServiceRequest.setRenewalsRevenue(renewalsRevenue);
		mainServiceRequest.setTotalRevenue(totalRevenue);	
		mainServiceRequest.setUsdRevenue(usdRevenue);
		mainServiceRequest.setCallbackcount(callbackcount);
		mainServiceRequest.setSubFailed(SubFailed);
		mainServiceRequest.setRevenueShare(revenueShare);
		mainServiceRequest.setFame(fame);
		mainServiceRequest.setOperator(operator);	
		mainServiceRequest.setCountry(country);	
		mainServiceRequest.setSubService(subService);
		return mainServiceRequest;
	} 

// Currency Api 
public Double getUsdValue(String code) 
{
String accessKey = "b97167310564f21c0614cca7";
// Setting URL
String urlString = "https://v6.exchangerate-api.com/v6/b97167310564f21c0614cca7/latest/"+code;


// RestTemplate here also to api call 
RestTemplate rt = new RestTemplate();
//rt.getForEntity(urlString, String.class);
ResponseEntity<String> response =  rt.getForEntity(urlString, String.class);
String res = response.getBody();
   

ObjectMapper objectMapper = new ObjectMapper();
   
Double usdValue=0.0;

try {
	
     // Parse the response string into a JSON object
     JsonNode jsonNode = objectMapper.readTree(res);

     // Access the "conversion_rates" object from the response
     JsonNode conversionRates = jsonNode.get("conversion_rates");

     // Access the value corresponding to the "USD" key
     usdValue = conversionRates.get("USD").asDouble();
       

     //Current USD value
     //System.out.println("USD Value: " + usdValue);
   }catch (Exception e) {
       e.printStackTrace();
   }
   return usdValue;
   	
}


	//Api for save the data 
	public static String saveServiceApi(MainServiceRequest mainService)
	{
		String res ="";
		if(mainService != null)
	 	{
			System.out.println("api calling....");
	 		
	 		RestTemplate restTemplate = new RestTemplate();
	 		
			try {
				 
				JSONObject jsonData = new JSONObject(mainService);
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.set("Content-Type", "Application/json");
		 		
		 		HttpEntity<String> mainEntity = new HttpEntity<String>(jsonData.toString(), httpHeaders);
		 		
		 		System.out.println("==="+mainEntity);
		 		// Calling api
		 		ResponseEntity<String> response = restTemplate.postForEntity("http://88.99.5.236:2980/saveServiceData", mainEntity, String.class);
		 		res = response.toString();
		 		System.out.println(response);
				
			} catch (Exception e) {
				e.printStackTrace();
				res="exception";
				
			}
			
	 		
	 	}else {
	 		System.out.println("api not calling...");
	 	}
		return res;
	}
	

	

}
