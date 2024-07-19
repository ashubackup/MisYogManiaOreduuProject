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

import com.vision.dbthree.repository.ITableSubscriptionRepo;
import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;
import com.vision.util.UtilityService;

@Service
public class YogaOreduOmanMisService {
	
	@Autowired
	private ITableSubscriptionRepo oreduRepo;
	
	@Autowired
	private UtilityService service;
	
	public void saveMisData()
	{
		Integer baseCount = oreduRepo.getBaseCount();
		baseCount = baseCount == null?0:baseCount;
		Integer activeCount = oreduRepo.getActiveCount();
		activeCount = activeCount == null?0:activeCount;
		Integer dailySubCount = oreduRepo.getDailySubCount();
		dailySubCount = dailySubCount==null?0:dailySubCount;
		Integer dailyRenCount = oreduRepo.getDailyRenCount();
		dailyRenCount = dailyRenCount == null?0:dailyRenCount;
		
		
		Integer unSubCount = oreduRepo.getUnsubCount();
		unSubCount = unSubCount == null?0:unSubCount;
		
		
		Double subRevenue = oreduRepo.getSubRevenue();
		subRevenue = subRevenue == null?0.0:subRevenue;
		Double renRevenue = oreduRepo.getRenRevenue();
		renRevenue = renRevenue == null?0.0:renRevenue;
		Double price = oreduRepo.getPice();
		
		Double totalRevenue = subRevenue + renRevenue;
		Double usd = service.getUsdValue("KWD");
	 	Double usdRevenue = totalRevenue * usd;
		
		LocalDate currentDateTime = LocalDate.now();
	    String minusOneDay = currentDateTime.minusDays(1).toString();
	 	
	 	// Set Daily 
	 	PackRequest packDaily =  service.setDailyPackRequest("Daily", "YogaForYou", String.valueOf(price), "YogaForU", minusOneDay, String.valueOf(dailySubCount),  
	 			String.valueOf(dailyRenCount),  String.valueOf(unSubCount), 
	 			String.valueOf(subRevenue), String.valueOf(renRevenue), String.valueOf(totalRevenue));
	 	
	 	List<PackRequest> packRequestList = new ArrayList<>();
	 	packRequestList.add(packDaily);
	 	
	 	SubServiceRequest subServiceRequest = service.setDailySubServiceRequest("YogaForYou", "YogaForU", "1", minusOneDay,
	 			String.valueOf(dailySubCount), String.valueOf(dailyRenCount), String.valueOf(subRevenue),
	 			String.valueOf(renRevenue), String.valueOf(totalRevenue),baseCount,activeCount,unSubCount, packRequestList);
	 	
	 	List<SubServiceRequest> subServiceRequestList = new ArrayList<>();
	 	subServiceRequestList.add(subServiceRequest);
	 	
	 	MainServiceRequest mainServiceRequest = service.setMainServiceRequest("YogaForYou", minusOneDay, String.valueOf(baseCount),String.valueOf(activeCount), 
	 			String.valueOf(dailySubCount), String.valueOf(dailyRenCount), String.valueOf(unSubCount), String.valueOf(subRevenue), String.valueOf(renRevenue),
	 			String.valueOf(totalRevenue), String.valueOf(usdRevenue),
	 			"0",
				"0",
				"0",
				"0",
				"Ooredoo",
				"Kuwait",
	 			subServiceRequestList);
	 	
	 // Calling the api
	 	System.out.println(mainServiceRequest);
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
	 		 		
	 		 		ResponseEntity<String> response = restTemplate.postForEntity("http://88.99.5.236:2980/saveServiceData", mainEntity, String.class);
	 		 		System.out.println("Response : " + response);
	 		 			
	 				
	 			} catch (Exception e) {
	 				e.printStackTrace();
	 			}
	 	 		
	 	 			
	 	 	}else {
	 	 		System.out.println("api not calling...");
	 	 	}
	}

}
