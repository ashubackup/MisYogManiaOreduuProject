package com.vision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision.kiddocrazeuae.repository.KiddocrazeUaeRepository;
import com.vision.service.EthiopiaMisService;
import com.vision.service.FitoffyAndKidsMisService;
import com.vision.service.GameDubiaMisService;
import com.vision.service.GameHubMisService;
import com.vision.service.HtwoNSerialeoneMisService;
import com.vision.service.JanguKidsMisService;
import com.vision.service.KiddocrazeMisService;
import com.vision.service.KiddocrazeUaeMisService;
import com.vision.service.OmanOreduMisService;
import com.vision.service.ThailandHoroscopeMisService;
import com.vision.service.TrueMoveService;
import com.vision.service.TselMoSmsAndyMisService;
import com.vision.service.TselMoSmsVisiontrekMisService;
import com.vision.service.YogaOreduOmanMisService;
import com.vision.service.ZainIraqToonflixMisService;

@RestController
//@Component
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
	@Autowired
	private KiddocrazeMisService kiddoService;
	@Autowired
	private ZainIraqToonflixMisService toonflixService;
	@Autowired
	private JanguKidsMisService janguKidsService;
	@Autowired
	private EthiopiaMisService ethoService;
	@Autowired
	private GameDubiaMisService gamedubiaService;
	@Autowired
	private KiddocrazeUaeMisService kiddocrazeUaeService;
	@Autowired
	private ThailandHoroscopeMisService horoscopeService;
	@Autowired
	private TselMoSmsVisiontrekMisService tselService;
	
	@Autowired
	private TselMoSmsAndyMisService tselAndyBubllyService;

	//@PostMapping("/sub")
	@Scheduled(cron = "0 0 7 * * *",zone="IST")
	public void portalData()
	{
		System.out.println("======YogMania Data saving start==========");
		service.getTblSubRevenue();
		System.out.println("=======YogMania Data saving end===========");
	}
	
	//@GetMapping("/ore")
	@Scheduled(cron = "0 10 7 * * *",zone="IST")
	public void oredooYogaFuMisData()
	{
		System.out.println("======Oredoo Yoga F u Data saving start==========");
		oreduService.saveMisData();
		System.out.println("=======Oredoo Yoga F u Data saving end===========");
	}
	
	//@GetMapping("/truemove")
	@Scheduled(cron = "0 15 7 * * *",zone="IST")
	public void trueMoveMisData()
	{
		System.out.println("======trueMove Data saving start==========");
		
		trueMoveService.saveGameoffyMis();
		System.out.println("=======trueMove Data saving end===========");
	}
	
	//@GetMapping("/mis")
	@Scheduled(cron = "0 20 7 * * *",zone="IST")
	public void h2nSerialeoneMisData()
	{
		System.out.println("======h2nSerialeoneMisData saving start==========");
		serialeoneService.saveMis();
		System.out.println("=======h2nSerialeoneMisData Data saving end===========");
	}
	
	@Scheduled(cron = "0 25 7 * * *",zone="IST")
	//@GetMapping("/gamehub")
	public void h2nGameHubMisData()
	{
		System.out.println("======GameHub saving start==========");
		gameHubService.saveGameHubMis();
		System.out.println("=======GameHub Data saving end===========");
	}
	//@GetMapping("/oman")
	@Scheduled(cron = "0 30 7 * * *",zone="IST")
	public void omanOreduMisData()
	{
		System.out.println("======OmanOredu saving start==========");
		omanOreduService.omanOreduMis();
		System.out.println("=======OmanOredu Data saving end===========");
	}
	
	//@GetMapping("/kiddo")
	@Scheduled(cron = "0 35 7 * * *",zone="IST")
	public void kiddocrazeMisData()
	{
		System.out.println("======Kiddocraze saving start==========");
		kiddoService.saveKiddocrazeMis();
		System.out.println("=======Kiddocraze Data saving end===========");
	}
	
	//@GetMapping("/toonflix")
	@Scheduled(cron = "0 40 7 * * *",zone="IST")
	public void zainIraqToonflixMisData()
	{
		System.out.println("======zainIraqToonflix saving start==========");
		toonflixService.saveToonflixIraqMis();
		System.out.println("=======zainIraqToonflix Data saving end===========");
	}
	
	
	//@GetMapping("/jangukid")
	@Scheduled(cron = "0 50 7 * * *",zone="IST")
	public void janguKidsMisData()
	{
		System.out.println("======janguKids saving start==========");
		janguKidsService.saveJanguKidsMis();
		System.out.println("=======jangukids Data saving end===========");
	}
	
	//@GetMapping("/ethopia")
	@Scheduled(cron = "0 0 8 * * *",zone="IST")
	public void ethopiaMisData()
	{
		System.out.println("======ethopia saving start==========");
		ethoService.saveEthopiaMis();
		System.out.println("=======ethopia Data saving end===========");
	}
	
	//@GetMapping("/gamedubia")
	@Scheduled(cron = "0 10 8 * * *",zone="IST")
	public void gamedubiaMisData()
	{
		System.out.println("======gamedubia saving start==========");
		gamedubiaService.saveGameDubiaMis();
		System.out.println("=======gamedubia Data saving end===========");
	}
	
	//@GetMapping("/kiddoUae")
	@Scheduled(cron = "0 20 8 * * *",zone="IST")
	public void kiddocrazeUaeMisData()
	{
		System.out.println("======kiddocrazeUaeService saving start==========");
		kiddocrazeUaeService.saveKiddocrazeUaeMis();
		System.out.println("=======kiddocrazeUaeService Data saving end===========");
	}
	
	
	@GetMapping("/horoscope")
	//@Scheduled(cron = "0 25 8 * * *",zone="IST")
	public void thailandHoroscopeMisData()
	{
		System.out.println("======thailandHoroscopeMisData saving start==========");
		horoscopeService.saveThailandHoroscopeMis();
		System.out.println("=======thailandHoroscopeMisData Data saving end===========");
	}
	
	//@GetMapping("/tsel")
	@Scheduled(cron = "0 30 8 * * *",zone="IST")
	public void tselMoSmsVisiontrekMis()
	{
		System.out.println("======tselMoSmsVisiontrek saving start==========");
		tselService.tselMoSmsVisiontrekMisSave();
		System.out.println("=======tselMoSmsVisiontrek Data saving end===========");
	}
	
	//@GetMapping("/tselandy")
	@Scheduled(cron = "0 35 8 * * *",zone="IST")
	public void tselMoSmsAndyMis()
	{
		System.out.println("======tselMoSmsAndyMisSave saving start==========");
		tselAndyBubllyService.tselMoSmsAndyMisSave();
		System.out.println("=======tselMoSmsAndyMisSave Data saving end===========");
	}

}
