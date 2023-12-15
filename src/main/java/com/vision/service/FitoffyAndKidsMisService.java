package com.vision.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vision.dbone.repository.TblSubscriptionRepo;
import com.vision.dbtwo.repository.ITblSubscriptionRepo;
import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;

@Service
public class FitoffyAndKidsMisService
{
	
	@Autowired
	private TblSubscriptionRepo subRepo;
	@Autowired
	private ITblSubscriptionRepo kidsRepo;
	
	public void getTblSubRevenue()
	{
		
		//-------------Fitoffy--------
		Integer baseCount = subRepo.getTblSubBaseCount();
		Integer activeBaseCount = subRepo.getTblSubActiveCount();
			activeBaseCount=activeBaseCount==null?0:activeBaseCount;
		Integer dailySubCount =  subRepo.getTblBillSuccDailySubCount();
			dailySubCount=dailySubCount==null?0:dailySubCount;
		Integer dailyRenCount = subRepo.getTblBillSuccDailyRenCount();
			dailyRenCount=dailyRenCount==null?0:dailyRenCount;
		
		Integer unsubTotalCount = subRepo.getTblUnsubCount();
			unsubTotalCount=unsubTotalCount==null?0:unsubTotalCount;
	 	Double dailyPrice = subRepo.getPice();
	 	Double dailySubRev = subRepo.getSubRevenue();
	 		dailySubRev=dailySubRev==null?0.0:dailySubRev;
	 	Double dailyRenewalRev = subRepo.getRenRevenue();
	 		dailyRenewalRev=dailyRenewalRev==null?0.0:dailyRenewalRev;
	 	Double totalRevenue = dailySubRev + dailyRenewalRev;
	 
	 	//---------Kidszonepro----------
	 	Integer kidsBaseCount = kidsRepo.getTblSubBaseCount();
		Integer kidsActiveBaseCount = kidsRepo.getTblSubActiveCount();
		Integer kidsDailySubCount =  kidsRepo.getTblBillSuccDailySubCount();
				kidsDailySubCount=kidsDailySubCount==null?0:kidsDailySubCount;
		Integer kidsDailyRenCount = kidsRepo.getTblBillSuccDailyRenCount();
				kidsDailyRenCount=kidsDailyRenCount==null?0:kidsDailyRenCount;
		Integer kidsUnsubTotalCount = kidsRepo.getTblUnsubCount();
				kidsUnsubTotalCount=kidsUnsubTotalCount==null?0:kidsUnsubTotalCount;
	 	Double kidsDailyPrice = kidsRepo.getPice();
	 	Double kidsDailySubRev = kidsRepo.getSubRevenue();
	 			kidsDailySubRev=kidsDailySubRev==null?0.0:kidsDailySubRev;
	 	Double kidsDailyRenewalRev = kidsRepo.getRenRevenue();
	 			kidsDailyRenewalRev=kidsDailyRenewalRev==null?0.0:kidsDailyRenewalRev;
	 	Double kidsTotalRevenue = kidsDailySubRev + kidsDailyRenewalRev;
	 	/*
		 * (String service,String misDate,String totalBase, String totalActiveBase,
		 * String subscriptions,String renewals, String unsubscriptions,String
		 * subscriptionRevenue,String renewalsRevenue, String totalRevenue, String
		 * usdRevenue, List<SubServiceRequest> subService )
		 */
	 	
	 	Integer totalBase = baseCount+ kidsBaseCount;
	 	Integer totalActiveBase = activeBaseCount+kidsActiveBaseCount;
		Integer subscriptions = dailySubCount+kidsDailySubCount;
		Integer renewals = dailyRenCount+kidsDailyRenCount;
		Integer unsubscriptions = unsubTotalCount+kidsUnsubTotalCount;
		Double subscriptionRevenue = dailySubRev+kidsDailySubRev;
		Double renewalsRevenue = dailyRenewalRev+kidsDailyRenewalRev;
		Double totalRevenues = totalRevenue+kidsTotalRevenue;
		
	 	
	 	
	 	Double usd = getUsdValue();
	 	Double usdRevenue = totalRevenues * usd;
	 	
	 	
	 
	 	LocalDate currentDateTime = LocalDate.now();
	    String minusOneDay = currentDateTime.minusDays(1).toString();
	 	
	 	// Set Daily 
	 	PackRequest packDaily =  setDailyPackRequest("Daily", "Fitofyy", String.valueOf(dailyPrice), "CellcSA", minusOneDay, String.valueOf(dailySubCount),  
	 			String.valueOf(dailyRenCount),  String.valueOf(unsubTotalCount), 
	 			String.valueOf(dailySubRev), String.valueOf(dailyRenewalRev), String.valueOf(totalRevenue));
	 	
	    // Set Daily 
	   PackRequest kidsPackDaily =  setDailyPackRequest("Daily", "Kidszonepro", String.valueOf(kidsDailyPrice), "CellcSA", minusOneDay,
			   String.valueOf(kidsDailySubCount),  
 			String.valueOf(kidsDailyRenCount),  String.valueOf(kidsUnsubTotalCount), 
 			String.valueOf(kidsDailySubRev), String.valueOf(kidsDailyRenewalRev), String.valueOf(kidsTotalRevenue));
	 	 	
	 	
	 	
	 	// Adding to the list
	 	List<PackRequest> packRequestList = new ArrayList<>();
	 	packRequestList.add(packDaily);
	 	packRequestList.add(kidsPackDaily);
	 	
	 
	 	SubServiceRequest subServiceRequest = setDailySubServiceRequest("CellcSA", "Fitofyy", "1", minusOneDay,
	 			String.valueOf(dailySubCount), String.valueOf(dailyRenCount), String.valueOf(dailySubRev),
	 			String.valueOf(dailyRenewalRev), String.valueOf(totalRevenue), packRequestList);
	 	
	 	SubServiceRequest kidsSubServiceRequest = setDailySubServiceRequest("CellcSA", "Kidszonepro", "1", minusOneDay,
	 			String.valueOf(kidsDailySubCount), String.valueOf(kidsDailyRenCount), String.valueOf(kidsDailySubRev),
	 			String.valueOf(kidsDailyRenewalRev), String.valueOf(kidsTotalRevenue), packRequestList);
	 	
	 	
	 	//SubServiceRequest List
	 	List<SubServiceRequest> subServiceRequestList = new ArrayList<>();
	 	subServiceRequestList.add(subServiceRequest);
	 	subServiceRequestList.add(kidsSubServiceRequest);
	 	
	 	
		/*
		 * (String service,String misDate,String totalBase, String totalActiveBase,
		 * String subscriptions,String renewals, String unsubscriptions,String
		 * subscriptionRevenue,String renewalsRevenue, String totalRevenue, String
		 * usdRevenue, List<SubServiceRequest> subService )
		 */
	 
	 	MainServiceRequest mainServiceRequest = setMainServiceRequest("CellcSA", minusOneDay, String.valueOf(totalBase),String.valueOf(totalActiveBase), 
	 			String.valueOf(subscriptions), String.valueOf(renewals), String.valueOf(unsubscriptions), String.valueOf(subscriptionRevenue), String.valueOf(renewalsRevenue),
	 			String.valueOf(totalRevenues), String.valueOf(usdRevenue),
	 			"0",
				"0",
				"0",
				"0",
	 			subServiceRequestList);
	 	
	 
	 //	System.out.println(mainServiceRequest);
	 	
	 	// Calling the api
	 	if(mainServiceRequest != null)
	 	{
	 		//System.out.println("api calling....");
	 		
	 		RestTemplate restTemplate = new RestTemplate();
	 		
			try {
				
				JSONObject jsonData = new JSONObject(mainServiceRequest);
				//System.out.println(jsonData.toString());
				
				
				//System.out.println(jsonData.toString());
				
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.set("Content-Type", "Application/json");
		 		
		 		HttpEntity<String> mainEntity = new HttpEntity<String>(jsonData.toString(), httpHeaders);
		 		System.out.println(mainEntity);
		 		System.out.println("Project on live");
		 		// Calling api
		 		ResponseEntity<String> response = restTemplate.postForEntity("http://88.99.5.236:2980/saveServiceData", mainEntity, String.class);
		 		System.out.println("Response : " + response);
		 			
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	 		
	 			
	 	}else {
	 		System.out.println("api not calling...");
	 	}
	 	
	 	
	 	
	 	
	}
	
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
		subServiceRequest.setPack(packList);;

		return subServiceRequest;
	}
	
	
	// MainServiceRequest 
	public MainServiceRequest setMainServiceRequest
	(String service,String misDate,String totalBase, String totalActiveBase,
	String subscriptions,String renewals,
	String unsubscriptions,String subscriptionRevenue,String renewalsRevenue,
	String totalRevenue, String usdRevenue,String callbackcount,
	String SubFailed, String revenueShare, String fame,
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
		mainServiceRequest.setSubService(subService);
		return mainServiceRequest;
	} 
	
	// Currency Api 
	public Double getUsdValue() 
	{
		String accessKey = "b97167310564f21c0614cca7";
		// Setting URL
		String urlString = "https://v6.exchangerate-api.com/v6/b97167310564f21c0614cca7/latest/ZAR";
		
		// RestTemplate here also to api call 
	    RestTemplate rt = new RestTemplate();
	    ResponseEntity<String> response =  rt.getForEntity(urlString, String.class);
	    String res = response.getBody();
	       

	    ObjectMapper objectMapper = new ObjectMapper();
	       
	    Double usdValue=0.0;

	    try {
	    	
	         JsonNode jsonNode = objectMapper.readTree(res);
	         JsonNode conversionRates = jsonNode.get("conversion_rates");
	         usdValue = conversionRates.get("USD").asDouble();
	         
	       }catch (Exception e) {
	           e.printStackTrace();
	       }
	       return usdValue;
	       	
	}
	
}
