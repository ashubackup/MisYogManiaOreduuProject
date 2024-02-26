package com.vision.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;
import com.vision.jangukids.entity.TblSubscription;
import com.vision.jangukids.repository.JanguKidsRepository;
import com.vision.util.UtilityService;

@Service
public class JanguKidsMisService {
	@Autowired
	private JanguKidsRepository janguRepo;
	@Autowired
	private UtilityService service;
	
	public void saveJanguKidsMis()
	{
		int date = 1;
		
		Double usd = service.getUsdValue("NGN");
			
		System.out.println("Date value is "+ date);
		Integer totalBaseCount = janguRepo.baseCount(date-1);
		totalBaseCount=totalBaseCount==null?0:totalBaseCount;
		Integer totalActiveCount = janguRepo.activeCount(date-1);
		totalActiveCount=totalActiveCount==null?0:totalActiveCount;
		
		Integer dailySubCount = janguRepo.dailySubCount(date);
		if(dailySubCount ==null)
			dailySubCount =0;
		
		Integer weeklySubCount=janguRepo.weeklySubCount(date);
		weeklySubCount=weeklySubCount==null?0:weeklySubCount;
		
		Integer monthlySubCount=janguRepo.monthlySubCount(date);
		monthlySubCount=monthlySubCount==null?0:monthlySubCount;
		
		Integer dailyRenCount = janguRepo.dailyRenCount(date);
		if(dailyRenCount ==0)
			dailyRenCount =0;
		
		Integer weeklyRenCount=janguRepo.weeklyRenCount(date);
		weeklyRenCount=weeklyRenCount==null?0:weeklyRenCount;
		
		Integer monthlyRenCount=janguRepo.monthlyRenCount(date);
		monthlyRenCount=monthlyRenCount==null?0:monthlyRenCount;
		
		
		Double dailySubRevenue = janguRepo.dailySubRevenue(date);
		if(dailySubRevenue == null)
			dailySubRevenue =0.0;
		Double weeklySubRevenue = janguRepo.weeklySubRevenue(date);
		weeklySubRevenue=weeklySubRevenue==null?0.0:weeklySubRevenue;
		Double monthlySubRevenue=janguRepo.monthlySubRevenue(date);
		monthlySubRevenue=monthlySubRevenue==null?0.0:monthlySubRevenue;
		
		Double totalSubRevenue = dailySubRevenue+weeklySubRevenue+monthlySubRevenue;
		totalSubRevenue=totalSubRevenue==null?0.0:totalSubRevenue;
		
	
		Double dailyRenRevenue = janguRepo.dailyRenRevenue(date);
		if(dailyRenRevenue == null)
			dailyRenRevenue =0.0;
		Double weeklyRenRevenue=janguRepo.weeklyRenRevenue(date);
		weeklyRenRevenue=weeklyRenRevenue==null?0.0:weeklyRenRevenue;
		Double monthlyRenRevenue = janguRepo.monthlyRenRevenue(date);
		monthlyRenRevenue=monthlyRenRevenue==null?0.0:monthlyRenRevenue;
		
		Double totalRenRevenue = dailyRenRevenue+weeklyRenRevenue+monthlyRenRevenue;
		totalRenRevenue=totalRenRevenue==null?0.0:totalRenRevenue;
		
		
		
		
		
		Double totalRevenue = totalSubRevenue+totalRenRevenue;
		totalRevenue=totalRevenue==null?0.0:totalRevenue;
		
		Integer unsubCount = janguRepo.unsubCount(date);
		
		
		
		Double dailyPrice=janguRepo.getDailyPrice();
		dailyPrice = dailyPrice==null?20.0:dailyPrice;
		Double weeklyPrice=janguRepo.getWeeklyPrice();
		weeklyPrice=weeklyPrice==null?50.0:weeklyPrice;
		Double monthlyPrice=janguRepo.getMonthlyPrice();
		monthlyPrice=monthlyPrice==null?200.0:monthlyPrice;
		
	
		
		System.out.println("Get Usd : " + usd );
		Double usdRevenue = totalRevenue * usd;
		
		System.out.println("Usd Revenue: " + usdRevenue);
		
		//1
		LocalDate currentDateTime = LocalDate.now();
	    String minusOneDay = currentDateTime.minusDays(date).toString();
	    System.out.println("mis date ====" + minusOneDay);
	    
		PackRequest dailyPack = service.setDailyPackRequest("Daily", "Daily", String.valueOf(dailyPrice), "JanguKids", minusOneDay,
				String.valueOf(dailySubCount), 
				String.valueOf(dailyRenCount), String.valueOf(unsubCount), String.valueOf(dailySubRevenue), 
				String.valueOf(dailyRenRevenue), String.valueOf(totalRevenue));
		
		PackRequest weeklyPack = service.setDailyPackRequest("Weekly", "Weekly", String.valueOf(weeklyPrice), "JanguKids", minusOneDay,
				String.valueOf(weeklySubCount), 
				String.valueOf(weeklyRenCount), String.valueOf(unsubCount), String.valueOf(weeklySubRevenue), 
				String.valueOf(weeklyRenRevenue), String.valueOf(totalRevenue));
		
		PackRequest monthlyPack = service.setDailyPackRequest("monthly", "monthly", String.valueOf(monthlyPrice), "JanguKids", minusOneDay,
				String.valueOf(monthlySubCount), 
				String.valueOf(monthlyRenCount), String.valueOf(unsubCount), String.valueOf(monthlySubRevenue), 
				String.valueOf(monthlyRenRevenue), String.valueOf(totalRevenue));
		
	
		List<PackRequest> packList = new ArrayList<>();
		packList.add(dailyPack);
		packList.add(weeklyPack);
		packList.add(monthlyPack);		
		// SubServiceRequest 
		SubServiceRequest subService = service.setDailySubServiceRequest("JanguKids", "JanguKids", "1",
				minusOneDay, String.valueOf(dailySubCount+weeklySubCount+monthlySubCount), String.valueOf(dailyRenCount+weeklyRenCount),
				String.valueOf(dailySubRevenue+weeklySubRevenue+monthlySubRevenue),String.valueOf(dailyRenRevenue+weeklyRenRevenue+weeklyRenRevenue),
				String.valueOf(totalRevenue), packList);
		
		List<SubServiceRequest> subList = new ArrayList<>();
		subList.add(subService);		
		
	
		MainServiceRequest mainService = service.setMainServiceRequest("JanguKids", minusOneDay, 
				String.valueOf(totalBaseCount), 
				String.valueOf(totalActiveCount),
				String.valueOf(dailySubCount+weeklySubCount+monthlySubCount), 
				String.valueOf(dailyRenCount+dailyRenCount+monthlyRenCount),
				String.valueOf(unsubCount), 
				String.valueOf(dailySubRevenue+weeklySubRevenue+monthlySubRevenue), 
				String.valueOf(dailyRenRevenue+weeklyRenRevenue+monthlyRenRevenue),
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
