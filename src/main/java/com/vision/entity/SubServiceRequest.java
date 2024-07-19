package com.vision.entity;
import java.util.List;

import org.springframework.validation.annotation.Validated;

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
public class SubServiceRequest 
{
	//@NotEmpty
	private String serviceName;
	
	//@NotEmpty
	private String subServiceName;
	
	//@NotEmpty
	private String status;
	
	//@NotEmpty
	private String misDate;
	
	//@NotEmpty
	private String subscriptions;
	
	//@NotEmpty
	private String renewals;
	
	//@NotEmpty
	private String subscriptionRevenue;
	
	//@NotEmpty
	private String renewalRevenue;
	
	//@NotEmpty
	private String totalRevenue;
	
	private Integer totalBase;
	
	private Integer totalActiveBase;
	
	private Integer unsubscription;
		
	//@NotNull
	private List<PackRequest> pack;
	

}