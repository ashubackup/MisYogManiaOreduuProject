package com.vision.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;
import com.vision.kiddocrazeuae.repository.KiddocrazeUaeRepository;
import com.vision.util.UtilityService;


@Service
public class KiddocrazeUaeMisService {
	
	@Autowired
	private KiddocrazeUaeRepository kiddoUae;
	@Autowired
	private UtilityService service;
	
	public void saveKiddocrazeUaeMis(String serviceName)
	{
		int date = 1;
		
		
		Double usd = service.getUsdValue("AED");
//		while(date >0)
//		{
			
		System.out.println("Date value is "+ date);
		Integer totalBaseCount = kiddoUae.baseCount(date-1,serviceName);
		totalBaseCount=totalBaseCount==null?0:totalBaseCount;
		Integer totalActiveCount = kiddoUae.activeCount(serviceName);
		totalActiveCount=totalActiveCount==null?0:totalActiveCount;
		
		Integer dailySubCount = kiddoUae.dailySubCount(date,serviceName);
		if(dailySubCount ==null)
			dailySubCount =0;
		Integer dailyRenCount = kiddoUae.dailyRenCount(date,serviceName);
		if(dailyRenCount ==0)
			dailyRenCount =0;
		
		Double dailySubRevenue = kiddoUae.dailySubRevenue(date,serviceName);
		if(dailySubRevenue == null)
			dailySubRevenue =0.0;
		Double dailyRenRevenue = kiddoUae.dailyRenRevenue(date,serviceName);
		if(dailyRenRevenue == null)
			dailyRenRevenue =0.0;
		
		Double totalRevenue = dailySubRevenue+dailyRenRevenue;
		totalRevenue=totalRevenue==null?0.0:totalRevenue;
		
		Integer dailyUnsubCount = kiddoUae.unsubCount(date,serviceName);
		if(dailyUnsubCount == null)
			dailyUnsubCount = 0;
		
		Double price = kiddoUae.getPrice();
		if(price == null || price ==0)
			price = 11.0;
		
		
		
		
		
		System.out.println("Get Usd : " + usd );
		Double usdRevenue = totalRevenue * usd;
		
		System.out.println("Usd Revenue: " + usdRevenue);
		
		//1
		LocalDate currentDateTime = LocalDate.now();
	    String minusOneDay = currentDateTime.minusDays(date).toString();
	    System.out.println("mis date ====" + minusOneDay);
	    
		PackRequest pack = service.setDailyPackRequest("Weekly", "Weekly", String.valueOf(price), serviceName+"Uae", minusOneDay,
				String.valueOf(dailySubCount), 
				String.valueOf(dailyRenCount), String.valueOf(dailyUnsubCount), String.valueOf(dailySubRevenue), 
				String.valueOf(dailyRenRevenue), String.valueOf(totalRevenue));
		
	
		List<PackRequest> packList = new ArrayList<>();
		packList.add(pack);
		
		
		// SubServiceRequest 
		SubServiceRequest subService = service.setDailySubServiceRequest(serviceName+"Uae", serviceName+"Uae", "1",
				minusOneDay, String.valueOf(dailySubCount), String.valueOf(dailyRenCount),
				String.valueOf(dailySubRevenue),String.valueOf(dailyRenRevenue),
				String.valueOf(totalRevenue),totalBaseCount,totalActiveCount,dailyUnsubCount, packList);
		
		List<SubServiceRequest> subList = new ArrayList<>();
		subList.add(subService);		
		
	
		MainServiceRequest mainService = service.setMainServiceRequest(serviceName+"Uae", minusOneDay, 
				String.valueOf(totalBaseCount), 
				String.valueOf(totalActiveCount),
				String.valueOf(dailySubCount), 
				String.valueOf(dailyRenCount),
				String.valueOf(dailyUnsubCount), 
				String.valueOf(dailySubRevenue), 
				String.valueOf(dailyRenRevenue),
				String.valueOf(totalRevenue), 
				String.valueOf(usdRevenue),
				"0",
				"0",
				"0",
				"0",
				"Etisalat",
				"UAE",
				subList);
		
		System.out.println(mainService);
		
		
		
		System.out.println("=======Data save api calling now========");
		UtilityService.saveServiceApi(mainService);
//		date --;
//		}
		
	}
}
