package com.vision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision.service.FitoffyAndKidsMisService;
import com.vision.service.GameHubMisService;
import com.vision.service.HtwoNSerialeoneMisService;
import com.vision.service.OmanOreduMisService;
import com.vision.service.TrueMoveService;
import com.vision.service.YogaOreduOmanMisService;

//@RestController
@Component
public class DataController 
{
	
	@Autowired
	private FitoffyAndKidsMisService service;
	@Autowired
	private YogaOreduOmanMisService oreduService;
	@Autowired
	private TrueMoveService trueMoveService;
	@Autowired
	private HtwoNSerialeoneMisService serialeoneService;
	@Autowired
	private GameHubMisService gameHubService;
	@Autowired
	private OmanOreduMisService omanOreduService;

	//@PostMapping("/sub")
	@Scheduled(cron = "0 0 10 * * *",zone="IST")
	public void portalData()
	{
		System.out.println("======YogMania Data saving start==========");
		service.getTblSubRevenue();
		System.out.println("=======YogMania Data saving end===========");
	}
	
	//@GetMapping("/ore")
	@Scheduled(cron = "0 10 10 * * *",zone="IST")
	public void oredooYogaFuMisData()
	{
		System.out.println("======Oredoo Yoga F u Data saving start==========");
		oreduService.saveMisData();
		System.out.println("=======Oredoo Yoga F u Data saving end===========");
	}
	
	//@GetMapping("/truemove")
	@Scheduled(cron = "0 15 10 * * *",zone="IST")
	public void trueMoveMisData()
	{
		System.out.println("======trueMove Data saving start==========");
		
		trueMoveService.saveGameoffyMis();
		System.out.println("=======trueMove Data saving end===========");
	}
	
	//@GetMapping("/mis")
	@Scheduled(cron = "0 20 10 * * *",zone="IST")
	public void h2nSerialeoneMisData()
	{
		System.out.println("======h2nSerialeoneMisData saving start==========");
		serialeoneService.saveMis();
		System.out.println("=======h2nSerialeoneMisData Data saving end===========");
	}
	
	@Scheduled(cron = "0 25 10 * * *",zone="IST")
	//@GetMapping("/gamehub")
	public void h2nGameHubMisData()
	{
		System.out.println("======GameHub saving start==========");
		gameHubService.saveGameHubMis();
		System.out.println("=======GameHub Data saving end===========");
	}
	//@GetMapping("/oman")
	@Scheduled(cron = "0 30 10 * * *",zone="IST")
	public void omanOreduMisData()
	{
		System.out.println("======OmanOredu saving start==========");
		omanOreduService.omanOreduMis();
		System.out.println("=======OmanOredu Data saving end===========");
	}
		
	
}
