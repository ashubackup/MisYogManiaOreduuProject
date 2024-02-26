package com.vision.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.entity.MainServiceRequest;
import com.vision.entity.PackRequest;
import com.vision.entity.SubServiceRequest;
import com.vision.ethopiagames.repository.EthopiaGamesRepository;
import com.vision.util.UtilityService;


@Service
public class EthiopiaMisService {
	@Autowired
	private EthopiaGamesRepository ethoRepo;
	
	@Autowired
	private UtilityService service;
	
	public void saveEthopiaMis()
	{
		int date = 1;
//		while(date>=3)
//		{
			
		
		Double usd = service.getUsdValue("ETB");
			
		System.out.println("Date value is "+ date);
		Integer totalBaseCount = ethoRepo.baseCount(date-1);
		totalBaseCount=totalBaseCount==null?0:totalBaseCount;
		Integer totalActiveCount = ethoRepo.activeCount(date-1);
		totalActiveCount=totalActiveCount==null?0:totalActiveCount;
		
		// there is no sub and ren type in db
		// all ren so sub is 0
		Integer dailySubCount = ethoRepo.dailySubCount(date);
		if(dailySubCount ==0)
			dailySubCount =0;
		
		Integer dailyRenCount = ethoRepo.dailyRenCount(date);
		if(dailyRenCount ==0)
			dailyRenCount =0;
		
		Double dailySubRevenue = ethoRepo.dailySubRevenue(date);
		if(dailySubRevenue ==0)
			dailySubRevenue =0.0;
		Double dailyRenRevenue = ethoRepo.dailyRenRevenue(date);
		if(dailyRenRevenue == null)
			dailyRenRevenue =0.0;
		
		
		System.out.println("daily sub rev "+ dailySubRevenue);
		Double totalRevenue = dailySubRevenue+dailyRenRevenue;
		totalRevenue=totalRevenue==null?0.0:totalRevenue;
		
		Integer dailyUnsubCount = ethoRepo.unsubCount(date);
		if(dailyUnsubCount == null)
			dailyUnsubCount = 0;
		
		Double price = ethoRepo.getPrice();
		if(price == null)
			price = 2.0;
		
	
		System.out.println("Get Usd : " + usd );
		System.out.println("total rev "+ totalRevenue);
		Double usdRevenue = totalRevenue * usd;
		
		System.out.println("Usd Revenue: " + usdRevenue);
		
		//1
		LocalDate currentDateTime = LocalDate.now();
	    String minusOneDay = currentDateTime.minusDays(date).toString();
	    System.out.println("mis date ====" + minusOneDay);
	    
		PackRequest pack = service.setDailyPackRequest("Daily", "Daily", String.valueOf(price), "EthopiaGames", minusOneDay,
				String.valueOf(dailySubCount), 
				String.valueOf(dailyRenCount), String.valueOf(dailyUnsubCount), String.valueOf(dailySubRevenue), 
				String.valueOf(dailyRenRevenue), String.valueOf(totalRevenue));
		
	
		List<PackRequest> packList = new ArrayList<>();
		packList.add(pack);
		
		
		// SubServiceRequest 
		SubServiceRequest subService = service.setDailySubServiceRequest("EthopiaGames", "GameStation", "1",
				minusOneDay, String.valueOf(dailySubCount), String.valueOf(dailyRenCount),
				String.valueOf(dailySubRevenue),String.valueOf(dailyRenRevenue),
				String.valueOf(totalRevenue), packList);
		
		List<SubServiceRequest> subList = new ArrayList<>();
		subList.add(subService);		
		
	
		MainServiceRequest mainService = service.setMainServiceRequest("EthopiaGames", minusOneDay, 
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
				subList);
		
		System.out.println(mainService);
		
		
		
		System.out.println("=======Data save api calling now========");
		UtilityService.saveServiceApi(mainService);
//		date--;
//		
//		}
	}
}
