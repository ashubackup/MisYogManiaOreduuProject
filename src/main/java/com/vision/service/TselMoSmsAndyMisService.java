package com.vision.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;
import com.vision.tselmosmsandy.repository.TselMoSmsAndyRepo;
import com.vision.util.UtilityService;

@Service
public class TselMoSmsAndyMisService {
	@Autowired
	private TselMoSmsAndyRepo tselRepo;
	@Autowired
	private UtilityService service;
	
	public void tselMoSmsAndyMisSave()
	{
		int date = 1;
		Double usd = service.getUsdValue("IDR");
			
		Integer totalBaseCount = tselRepo.baseCount(date-1);
		totalBaseCount=totalBaseCount==null?0:totalBaseCount;
		Integer totalActiveCount = tselRepo.activeCount(date);
		totalActiveCount=totalActiveCount==null?0:totalActiveCount;
		
		Integer dailySubCount = tselRepo.dailySubCount(date);
		if(dailySubCount ==null)
			dailySubCount =0;
		Integer dailyRenCount = tselRepo.dailyRenCount(date);
		if(dailyRenCount ==0)
			dailyRenCount =0;
		
		Double dailySubRevenue = tselRepo.dailySubRevenue(date);
		if(dailySubRevenue == null)
			dailySubRevenue =0.0;
		Double dailyRenRevenue = tselRepo.dailyRenRevenue(date);
		if(dailyRenRevenue == null)
			dailyRenRevenue =0.0;
		
		Double totalRevenue = dailySubRevenue+dailyRenRevenue;
		totalRevenue=totalRevenue==null?0.0:totalRevenue;
		
		Integer dailyUnsubCount = tselRepo.unsubCount(date);
		if(dailyUnsubCount == null)
			dailyUnsubCount = 0;
		
		Double price = tselRepo.getPrice();
		if(price == null)
			price = 2220.0;
		
		System.out.println("Get Usd : " + usd );
		Double usdRevenue = totalRevenue * usd;
		
		System.out.println("Usd Revenue: " + usdRevenue);
		
		//1
		LocalDate currentDateTime = LocalDate.now();
	    String minusOneDay = currentDateTime.minusDays(date).toString();
	    System.out.println("mis date ====" + minusOneDay);
	    
		PackRequest pack = service.setDailyPackRequest("Daily", "Daily", String.valueOf(price), "Bublly", minusOneDay,
				String.valueOf(dailySubCount), 
				String.valueOf(dailyRenCount), String.valueOf(dailyUnsubCount), String.valueOf(dailySubRevenue), 
				String.valueOf(dailyRenRevenue), String.valueOf(totalRevenue));
		
	
		List<PackRequest> packList = new ArrayList<>();
		packList.add(pack);
		
		
		// SubServiceRequest 
		SubServiceRequest subService = service.setDailySubServiceRequest("Bublly", "BubllyGames", "1",
				minusOneDay, String.valueOf(dailySubCount), String.valueOf(dailyRenCount),
				String.valueOf(dailySubRevenue),String.valueOf(dailyRenRevenue),
				String.valueOf(totalRevenue),totalBaseCount,totalActiveCount,dailyUnsubCount, packList);
		
		List<SubServiceRequest> subList = new ArrayList<>();
		subList.add(subService);		
		
	
		MainServiceRequest mainService = service.setMainServiceRequest("Bublly", minusOneDay, 
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
				"Tsel",
				"Indonesia",
				subList);
		
		System.out.println(mainService);
		

		System.out.println("=======Data save api calling now========");
		UtilityService.saveServiceApi(mainService);
			
		
	}
}
