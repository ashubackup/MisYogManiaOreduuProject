package com.vision.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;
import com.vision.util.UtilityService;
import com.vision.zainiraqtonflix.repository.ZainIraqToonflixRepository;


@Service
public class ZainIraqToonflixMisService {
	@Autowired
	private ZainIraqToonflixRepository toonflixRepo;
	@Autowired
	private UtilityService service;
	
	public void saveToonflixIraqMis()
	{
		int date = 1;
		
		Double usd = service.getUsdValue("IQD");
			
		System.out.println("Date value is "+ date);
		Integer totalBaseCount = toonflixRepo.baseCount(date-1);
		totalBaseCount=totalBaseCount==null?0:totalBaseCount;
		Integer totalActiveCount = toonflixRepo.activeCount(date-1);
		totalActiveCount=totalActiveCount==null?0:totalActiveCount;
		
		Integer dailySubCount = toonflixRepo.dailySubCount(date);
		if(dailySubCount ==null)
			dailySubCount =0;
		Integer dailyRenCount = toonflixRepo.dailyRenCount(date);
		if(dailyRenCount ==null)
			dailyRenCount =0;
		
		Double dailySubRevenue = toonflixRepo.dailySubRevenue(date);
		if(dailySubRevenue == null)
			dailySubRevenue =0.0;
		Double dailyRenRevenue = toonflixRepo.dailyRenRevenue(date);
		if(dailyRenRevenue == null)
			dailyRenRevenue =0.0;
		
		Double totalRevenue = dailySubRevenue+dailyRenRevenue;
		totalRevenue=totalRevenue==null?0.0:totalRevenue;
		
		Integer dailyUnsubCount = toonflixRepo.unsubCount(date);
		if(dailyUnsubCount == null)
			dailyUnsubCount = 0;
		
		Double price = toonflixRepo.getPrice();
		if(price == null)
			price = 240.0;
		
		
		
		
		
		System.out.println("Get Usd : " + usd );
		Double usdRevenue = totalRevenue * usd;
		
		System.out.println("Usd Revenue: " + usdRevenue);
		
		//1
		LocalDate currentDateTime = LocalDate.now();
	    String minusOneDay = currentDateTime.minusDays(date).toString();
	    System.out.println("mis date ====" + minusOneDay);
	    
		PackRequest pack = service.setDailyPackRequest("Daily", "Daily", String.valueOf(price), "ToonflixIq", minusOneDay,
				String.valueOf(dailySubCount), 
				String.valueOf(dailyRenCount), String.valueOf(dailyUnsubCount), String.valueOf(dailySubRevenue), 
				String.valueOf(dailyRenRevenue), String.valueOf(totalRevenue));
		
	
		List<PackRequest> packList = new ArrayList<>();
		packList.add(pack);
		
		
		// SubServiceRequest 
		SubServiceRequest subService = service.setDailySubServiceRequest("ToonflixIq", "ToonflixIq", "1",
				minusOneDay, String.valueOf(dailySubCount), String.valueOf(dailyRenCount),
				String.valueOf(dailySubRevenue),String.valueOf(dailyRenRevenue),
				String.valueOf(totalRevenue),totalBaseCount,totalActiveCount,dailyUnsubCount, packList);
		
		List<SubServiceRequest> subList = new ArrayList<>();
		subList.add(subService);		
		
	
		MainServiceRequest mainService = service.setMainServiceRequest("ToonflixIq", minusOneDay, 
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
				"Iraq",
				subList);
		
		System.out.println(mainService);
		
		
		
		System.out.println("=======Data save api calling now========");
		UtilityService.saveServiceApi(mainService);
		
	}

}
