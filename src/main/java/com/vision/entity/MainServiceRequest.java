package com.vision.entity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MainServiceRequest 
{

	//@NotEmpty
	private String service;
	
	//@NotEmpty
	private String misDate;

	//@NotEmpty
	private String totalBase;
	
	//@NotEmpty
	private String totalActiveBase;

	//@NotEmpty
	private String subscriptions;
	
	//@NotEmpty
	private String renewals;
	
	//@NotEmpty
	private String unsubscriptions;
	
	//@NotEmpty
	private String subscriptionRevenue;
	
	//@NotEmpty
	private String renewalsRevenue;
	
	//@NotEmpty
	private String totalRevenue;
	
	//@NotEmpty
	private String usdRevenue;
	
	private String callbackcount;
	
	private String SubFailed;
	
	private String revenueShare;
	
	private String fame;
	
	private String operator;
	private String country;
	
	//@NotNull
	private List<SubServiceRequest> subService;
	
//	@NotNull
//	private List<@Valid SubServiceRequest> subService;
}