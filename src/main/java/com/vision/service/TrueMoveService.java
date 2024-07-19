package com.vision.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;
import com.vision.truemove.repository.ITrueMoveRepository;
import com.vision.util.UtilityService;

@Service
public class TrueMoveService {
	
	@Autowired
	private ITrueMoveRepository trueMoveRepo;
	@Autowired
	private UtilityService service;
	
	public void saveGameoffyMis()
	{
		int date = 1;
		
		Double usd = service.getUsdValue("THB");
			
		System.out.println("Date value is "+ date);
		Integer totalBaseCount = trueMoveRepo.baseCount(date-1);
		Integer totalActiveCount = trueMoveRepo.activeCount(date-1);
		
		Integer dailySubCount = trueMoveRepo.subCount(date);
		if(dailySubCount ==null)
			dailySubCount =0;
		Integer dailyRenCount = trueMoveRepo.renCount(date);
		if(dailyRenCount ==0)
			dailyRenCount =0;
		
		Double dailySubRevenue = trueMoveRepo.subRevenue(date);
		if(dailySubRevenue == null)
			dailySubRevenue =0.0;
		Double dailyRenRevenue = trueMoveRepo.renRevenue(date);
		if(dailyRenRevenue == null)
			dailyRenRevenue =0.0;
		
		Double totalRevenue = dailySubRevenue+dailyRenRevenue;
		totalRevenue=totalRevenue==null?0.0:totalRevenue;
		
		Integer dailyUnsubCount = trueMoveRepo.unsubCount(date);
		if(dailyUnsubCount == null)
			dailyUnsubCount = 0;
		
		Double price = trueMoveRepo.getPrice();
		if(price == null)
			price = 3.0;
		
		
		System.out.println("tatal base : "+totalBaseCount);
		System.out.println("active base : "+totalActiveCount);
		
		System.out.println("sub count : "+dailySubCount);
		System.out.println("ren count : "+dailyRenCount);
		
		System.out.println("sub rev : "+dailySubRevenue);
		System.out.println("ren rev : "+dailyRenRevenue);
		
		System.out.println("daily unsub count : "+dailyUnsubCount);
		System.out.println("Price  : "+price);
		
		
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
		PackRequest pack = service.setDailyPackRequest("Daily", "Daily", String.valueOf(price), "TrueMove", minusOneDay,
				String.valueOf(dailySubCount), 
				String.valueOf(dailyRenCount), String.valueOf(dailyUnsubCount), String.valueOf(dailySubRevenue), 
				String.valueOf(dailyRenRevenue), String.valueOf(totalRevenue));
		
		
		/*
		 * String serviceName,String subServiceName,String status, String misDate,
		 * String subscriptions,String renewals, String subscriptionRevenue,String
		 * renewalsRevenue,String totalRevenue, List<PackRequest> packList
		 */
		List<PackRequest> packList = new ArrayList<>();
		packList.add(pack);
		
		
		// SubServiceRequest 
		SubServiceRequest subService = service.setDailySubServiceRequest("TrueMove", "gameoffy", "1",
				minusOneDay, String.valueOf(dailySubCount), String.valueOf(dailyRenCount),
				String.valueOf(dailySubRevenue),String.valueOf(dailyRenRevenue),
				String.valueOf(totalRevenue),totalBaseCount,totalActiveCount,dailyUnsubCount, packList);
		
		List<SubServiceRequest> subList = new ArrayList<>();
		subList.add(subService);		
		
		
		// MainServiceRequest
		MainServiceRequest mainService = service.setMainServiceRequest("TrueMove", minusOneDay, 
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
				"",
				"Thailand",
				subList);
		
		System.out.println(mainService);
		
		
		
		System.out.println("=======Data save api calling now========");
		UtilityService.saveServiceApi(mainService);
		
	}

}
