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
//		while(date>=1)
//		{
		
			
		LocalDate currentDateTime = LocalDate.now();
	    String minusOneDay = currentDateTime.minusDays(date).toString();
	    System.out.println("date is "+ minusOneDay);
		
		Double usd = service.getUsdValue("ETB");
			
		System.out.println("Date value is "+ date);
		Integer totalBaseCount = ethoRepo.baseCount(date-1);
		totalBaseCount=totalBaseCount==null?0:totalBaseCount;
		Integer totalActiveCount = ethoRepo.activeCount(date-1);
		totalActiveCount=totalActiveCount==null?0:totalActiveCount;
		
		Integer baseCountOfGameStation = ethoRepo.baseCountByService(date, "game-station");
		Integer activeCountOfGameStation = ethoRepo.activeCountByService("game-station");
		
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
		
		
		Double gameTotalRevenue = dailySubRevenue+dailyRenRevenue;
		gameTotalRevenue=gameTotalRevenue==null?0.0:gameTotalRevenue;
		
		Integer dailyUnsubCount = ethoRepo.unsubCount(date);
		if(dailyUnsubCount == null)
			dailyUnsubCount = 0;
		
		Double price = ethoRepo.getPrice();
		if(price == null)
			price = 2.0;
		
		
		
		
		//------------esports-----
		
		
		Integer baseCountOfEsportStation = ethoRepo.baseCountByService(date, "esport");
		Integer activeCountOfEsportStation = ethoRepo.activeCountByService("esport");
		
		Integer esportDailySubCount = ethoRepo.esportDailySubCount(date);
		if(esportDailySubCount ==null)
			esportDailySubCount =0;
		
		Integer esportDailyRenCount = ethoRepo.esportDailyRenCount(date);
		if(esportDailyRenCount ==null)
			esportDailyRenCount =0;
		
		Double esportDailySubRevenue = ethoRepo.esportDailySubRevenue(date);
		if(esportDailySubRevenue ==null)
			esportDailySubRevenue =0.0;
		Double esportDailyRenRevenue = ethoRepo.esportDailyRenRevenue(date);
		if(esportDailyRenRevenue == null)
			esportDailyRenRevenue =0.0;
		
		Integer esportUnsubCount = ethoRepo.esportUnsubCount(date);
		if(esportUnsubCount == null)
			esportUnsubCount = 0;
		
		Double esportTotalRevenue = esportDailySubRevenue+esportDailyRenRevenue;
		esportTotalRevenue=esportTotalRevenue==null?0.0:esportTotalRevenue;
		
		
		
		Double totalRevenue = dailySubRevenue+dailyRenRevenue+esportDailySubRevenue+esportDailyRenRevenue;
		totalRevenue=totalRevenue==null?0.0:totalRevenue;

		Double usdRevenue = totalRevenue * usd;
		
		
		//1
		
	    
		PackRequest pack = service.setDailyPackRequest("Daily", "EthopiaGames", String.valueOf(price), "EthopiaGames", minusOneDay,
				String.valueOf(dailySubCount), 
				String.valueOf(dailyRenCount), String.valueOf(dailyUnsubCount), String.valueOf(dailySubRevenue), 
				String.valueOf(dailyRenRevenue), String.valueOf(gameTotalRevenue));
		
		PackRequest esportPack = service.setDailyPackRequest("Daily", "EthopiaGames", String.valueOf(price), "EthopiaGames", minusOneDay,
				String.valueOf(esportDailySubCount), 
				String.valueOf(esportDailyRenCount), String.valueOf(esportUnsubCount), String.valueOf(esportDailySubRevenue), 
				String.valueOf(esportDailyRenRevenue), String.valueOf(esportTotalRevenue));
		
	
		List<PackRequest> packList = new ArrayList<>();
		packList.add(pack);
		packList.add(esportPack);
		
		
		// SubServiceRequest 
		SubServiceRequest subService = service.setDailySubServiceRequest("EthopiaGames", "GameStation", "1",
				minusOneDay, String.valueOf(dailySubCount), String.valueOf(dailyRenCount),
				String.valueOf(dailySubRevenue),String.valueOf(dailyRenRevenue),
				String.valueOf(gameTotalRevenue),baseCountOfGameStation,activeCountOfGameStation,dailyUnsubCount, packList);
				
		SubServiceRequest subServiceEsport = service.setDailySubServiceRequest("EthopiaGames", "Esport", "1",
				minusOneDay, String.valueOf(esportDailySubCount), String.valueOf(esportDailyRenCount),
				String.valueOf(esportDailySubRevenue),String.valueOf(esportDailyRenRevenue),
				String.valueOf(esportTotalRevenue),baseCountOfEsportStation,activeCountOfEsportStation,esportUnsubCount, packList);
		
		List<SubServiceRequest> subList = new ArrayList<>();
		subList.add(subService);		
		subList.add(subServiceEsport);
		
	
		MainServiceRequest mainService = service.setMainServiceRequest("EthopiaGames", minusOneDay, 
				String.valueOf(totalBaseCount), 
				String.valueOf(totalActiveCount),
				String.valueOf(dailySubCount+esportDailySubCount), 
				String.valueOf(dailyRenCount+esportDailyRenCount),
				String.valueOf(dailyUnsubCount+esportUnsubCount), 
				String.valueOf(dailySubRevenue+esportDailySubRevenue), 
				String.valueOf(dailyRenRevenue+esportDailyRenRevenue),
				String.valueOf(totalRevenue), 
				String.valueOf(usdRevenue),
				"0",
				"0",
				"0",
				"0",
				"Ethio",
				"Ethopai",
				subList);
		

		System.out.println("=======Data save api calling now========");
		UtilityService.saveServiceApi(mainService);
		//date--;
		//break;
		
		//}
	}
}
