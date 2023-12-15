package com.vision.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;
import com.vision.omanoredu.repository.OmanOreduRepository;
import com.vision.util.UtilityService;

@Service
public class OmanOreduMisService {
	
	@Autowired
	private OmanOreduRepository omanRepo;
	@Autowired
	private UtilityService service;
	
	public void omanOreduMis()
	{
		int date = 1;
		
		Double usd = service.getUsdValue("OMR");
			
		System.out.println("Date value is "+ date);
		Integer totalBaseCount = omanRepo.baseCount(date-1);
		totalBaseCount=totalBaseCount==null?0:totalBaseCount;
		Integer totalActiveCount = omanRepo.activeCount(date-1);
		totalActiveCount=totalActiveCount==null?0:totalActiveCount;
		
		Integer dailySubCount = omanRepo.dailySubCount(date);
		if(dailySubCount ==null)
			dailySubCount =0;
		Integer dailyRenCount = omanRepo.dailyRenCount(date);
		if(dailyRenCount ==0)
			dailyRenCount =0;
		
		Double dailySubRevenue = omanRepo.dailySubRevenue(date);
		if(dailySubRevenue == null)
			dailySubRevenue =0.0;
		Double dailyRenRevenue = omanRepo.dailyRenRevenue(date);
		if(dailyRenRevenue == null)
			dailyRenRevenue =0.0;
		
		Double totalDailyRevenue = dailySubRevenue+dailyRenRevenue;
		totalDailyRevenue=totalDailyRevenue==null?0.0:totalDailyRevenue;
		
		Integer dailyUnsubCount = omanRepo.dailyUnsubCount(date);
		if(dailyUnsubCount == null)
			dailyUnsubCount = 0;
		
		Double dailyPrice = omanRepo.getDailyPrice();
		if(dailyPrice == null)
			dailyPrice = 200.0;
		
		//-----Weekly-----
		
		
		Integer weeklySubCount = omanRepo.weeklySubCount(date);
		if(weeklySubCount ==null)
			weeklySubCount =0;
		Integer weeklyRenCount = omanRepo.weeklyRenCount(date);
		if(weeklyRenCount ==0)
			weeklyRenCount =0;
		
		Double weeklySubRevenue = omanRepo.weeklySubRevenue(date);
		if(weeklySubRevenue == null)
			weeklySubRevenue =0.0;
		Double weeklyRenRevenue = omanRepo.weeklyRenRevenue(date);
		if(weeklyRenRevenue == null)
			weeklyRenRevenue =0.0;
		
		Double totalWeeklyRevenue = weeklySubRevenue+weeklyRenRevenue;
		totalWeeklyRevenue=totalWeeklyRevenue==null?0.0:totalWeeklyRevenue;
		
		Integer weeklyUnsubCount = omanRepo.weeklyUnsubCount(date);
		if(weeklyUnsubCount == null)
			weeklyUnsubCount = 0;
		
		Double weeklyPrice = omanRepo.getWeeklyPrice();
		if(weeklyPrice == null)
			weeklyPrice = 300.0;
		
		
		Double totalRevenue = totalDailyRevenue+totalWeeklyRevenue;

		System.out.println("Get Usd : " + usd );
		Double usdRevenue = totalRevenue * usd;
		
		System.out.println("Usd Revenue: " + usdRevenue);
		
		//1
		LocalDate currentDateTime = LocalDate.now();
	    String minusOneDay = currentDateTime.minusDays(date).toString();
	    System.out.println("mis date ====" + minusOneDay);
	    
		PackRequest pack = service.setDailyPackRequest("Daily", "Daily", String.valueOf(dailyPrice), "OmanOredu", minusOneDay,
				String.valueOf(dailySubCount), 
				String.valueOf(dailyRenCount), String.valueOf(dailyUnsubCount), String.valueOf(dailySubRevenue), 
				String.valueOf(dailyRenRevenue), String.valueOf(totalDailyRevenue));
		
		PackRequest weeklyPack = service.setDailyPackRequest("Weekly", "Weekly", String.valueOf(weeklyPrice), "OmanOredu", minusOneDay,
				String.valueOf(weeklySubCount), 
				String.valueOf(weeklyRenCount), String.valueOf(weeklyUnsubCount), String.valueOf(weeklySubRevenue), 
				String.valueOf(weeklyRenRevenue), String.valueOf(totalWeeklyRevenue));
		
	
		List<PackRequest> packList = new ArrayList<>();
		packList.add(pack);
		packList.add(weeklyPack);
		
		
		// SubServiceRequest 
		SubServiceRequest subService = service.setDailySubServiceRequest("OmanOredu", "BabbuTv", "1",
				minusOneDay, String.valueOf(dailySubCount+weeklySubCount), String.valueOf(dailyRenCount+weeklyRenCount),
				String.valueOf(dailySubRevenue+weeklySubRevenue),String.valueOf(dailyRenRevenue+weeklyRenRevenue),
				String.valueOf(totalRevenue), packList);
		
		List<SubServiceRequest> subList = new ArrayList<>();
		subList.add(subService);		
		
	
		MainServiceRequest mainService = service.setMainServiceRequest("OmanOredu", minusOneDay, 
				String.valueOf(totalBaseCount), 
				String.valueOf(totalActiveCount),
				String.valueOf(dailySubCount+weeklySubCount), 
				String.valueOf(dailyRenCount+weeklyRenCount),
				String.valueOf(dailyUnsubCount+weeklyUnsubCount), 
				String.valueOf(dailySubRevenue+weeklySubRevenue), 
				String.valueOf(dailyRenRevenue+weeklyRenRevenue),
				String.valueOf(totalRevenue), 
				String.valueOf(usdRevenue),
				"0",
				"0",
				"0",
				"0",
				subList);
		
		System.out.println(mainService);
		
		
		
		System.out.println("=======Data save api calling now========");
		UtilityService.saveServiceApi(mainService);
		
	}
	
	

}
