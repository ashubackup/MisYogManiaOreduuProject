package com.vision.entity;
//import javax.validation.constraints.NotEmpty;
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
public class PackRequest 
{
	
	private String type;
//	@NotEmpty
	private String name;
//	@NotEmpty
	private String price;
//	@NotEmpty
	private String serviceName;
//	@NotEmpty
	private String misDate;
//	@NotEmpty
	private String subscriptions;
//	@NotEmpty
	private String renewals;
//	@NotEmpty
	private String unsubscriptions;
//	@NotEmpty
	private String subscriptionRevenue;
//	@NotEmpty
	private String renewalsRevenue;
//	@NotEmpty

//	@NotEmpty
	private String totalRevenue;
	
		
}