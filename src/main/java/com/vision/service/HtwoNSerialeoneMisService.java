package com.vision.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;
import com.vision.h2nSerialeone.repository.H2nSerialeoneRepository;
import com.vision.util.UtilityService;

@Service
public class HtwoNSerialeoneMisService {
	@Autowired
	private H2nSerialeoneRepository serialeoneRepo;
	@Autowired
	private UtilityService service;
	
	public void saveMis()
	{
		int date = 1;
		
		Double usd = service.getUsdValue("SLE");
			
		System.out.println("Date value is "+ date);
		Integer totalBaseCount = serialeoneRepo.baseCount(date-1);
		Integer totalActiveCount = serialeoneRepo.activeCount(date-1);
		
		Integer dailySubCount = serialeoneRepo.dailySubCount(date);
		if(dailySubCount ==null)
			dailySubCount =0;
		Integer dailyRenCount = serialeoneRepo.dailyRenCount(date);
		if(dailyRenCount ==0)
			dailyRenCount =0;
		
		Double dailySubRevenue = serialeoneRepo.dailySubRevenue(date);
		if(dailySubRevenue == null)
			dailySubRevenue =0.0;
		Double dailyRenRevenue = serialeoneRepo.dailyRenRevenue(date);
		if(dailyRenRevenue == null)
			dailyRenRevenue =0.0;
		
		Double totalDailyRevenue = dailySubRevenue+dailyRenRevenue;
		totalDailyRevenue=totalDailyRevenue==null?0.0:totalDailyRevenue;
		
		//---------Weekly------
		
		Integer weeklySubCount = serialeoneRepo.weeklySubCount(date);
		if(weeklySubCount ==null)
			dailySubCount =0;
		Integer weeklyRenCount = serialeoneRepo.weeklyRenCount(date);
		if(weeklyRenCount ==0)
			weeklyRenCount =0;
		
		Double weeklySubRevenue = serialeoneRepo.weeklySubRevenue(date);
		if(weeklySubRevenue == null)
			weeklySubRevenue =0.0;
		Double weeklyRenRevenue = serialeoneRepo.weeklyRenRevenue(date);
		if(weeklyRenRevenue == null)
			weeklyRenRevenue =0.0;
		
		Double totalWeeklyRevenue = weeklySubRevenue+weeklyRenRevenue;
		totalWeeklyRevenue=totalWeeklyRevenue==null?0.0:totalWeeklyRevenue;
		
		Double totalRevenue = totalDailyRevenue+totalWeeklyRevenue;
		
		Integer unsubCount = serialeoneRepo.unsubCount(date);
		if(unsubCount == null)
			unsubCount = 0;
		
		Double dailyPrice = serialeoneRepo.getDailyPrice();
		if(dailyPrice == null)
			dailyPrice = 2.0;
		Double weeklyPrice = serialeoneRepo.getDailyPrice();
		if(weeklyPrice == null)
			weeklyPrice = 10.0;
		
		
		
		System.out.println("Get Usd : " + usd );
		Double usdRevenue = totalRevenue * usd;
		
		System.out.println("Usd Revenue: " + usdRevenue);
		
		//1
		LocalDate currentDateTime = LocalDate.now();
	    String minusOneDay = currentDateTime.minusDays(date).toString();
	    System.out.println("mis date ====" + minusOneDay);
	    
	    
	    // Data setting 
	    
//	    String type, String name, String price, String serviceName, String misDate,
//		String subscriptions,String renewals,String unsubscriptions,
//		String subscriptionRevenue,String renewalsRevenue, String totalRevenue
	    
		// PackRequest entity data set 
		PackRequest pack = service.setDailyPackRequest("Daily", "Serialeone", String.valueOf(dailyPrice), "Serialeone", minusOneDay,
				String.valueOf(dailySubCount), 
				String.valueOf(dailyRenCount), String.valueOf(unsubCount), String.valueOf(dailySubRevenue), 
				String.valueOf(dailyRenRevenue), String.valueOf(totalDailyRevenue));
		
		// PackRequest entity data set 
		PackRequest weeklyPack = service.setDailyPackRequest("Weekly", "Serialeone", String.valueOf(weeklyPrice), "Serialeone", minusOneDay,
						String.valueOf(weeklySubCount), 
						String.valueOf(weeklyRenCount), String.valueOf(unsubCount), String.valueOf(dailySubRevenue), 
						String.valueOf(weeklyRenRevenue), String.valueOf(totalWeeklyRevenue));
		
		
		/*
		 * String serviceName,String subServiceName,String status, String misDate,
		 * String subscriptions,String renewals, String subscriptionRevenue,String
		 * renewalsRevenue,String totalRevenue, List<PackRequest> packList
		 */
		List<PackRequest> packList = new ArrayList<>();
		packList.add(pack);
		packList.add(weeklyPack);
		
		
		// SubServiceRequest 
		SubServiceRequest subService = service.setDailySubServiceRequest("Serialeone", "KidzMania", "1",
				minusOneDay, String.valueOf(dailySubCount+weeklySubCount), String.valueOf(dailyRenCount+weeklyRenCount),
				String.valueOf(dailySubRevenue+weeklySubRevenue),String.valueOf(dailyRenRevenue+weeklyRenRevenue),
				String.valueOf(totalRevenue),totalBaseCount,totalActiveCount,unsubCount, packList);
		
		List<SubServiceRequest> subList = new ArrayList<>();
		subList.add(subService);		
		
		
		// MainServiceRequest
		MainServiceRequest mainService = service.setMainServiceRequest("Serialeone", minusOneDay, 
				String.valueOf(totalBaseCount), 
				String.valueOf(totalActiveCount),
				String.valueOf(dailySubCount+weeklySubCount), 
				String.valueOf(dailyRenCount+weeklyRenCount),
				String.valueOf(unsubCount), 
				String.valueOf(dailySubRevenue+weeklySubRevenue), 
				String.valueOf(dailyRenRevenue+weeklyRenRevenue),
				String.valueOf(totalRevenue), 
				String.valueOf(usdRevenue),
				"0",
				"0",
				"0",
				"0",
				"Orange",
				"SieraLeone",
				subList);
		
		System.out.println(mainService);
		
		
		
		System.out.println("=======Data save api calling now========");
		UtilityService.saveServiceApi(mainService);
		
	}

}
