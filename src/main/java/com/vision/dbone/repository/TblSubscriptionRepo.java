package com.vision.dbone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vision.dbone.entity.TblSubscription;


public interface TblSubscriptionRepo extends JpaRepository<TblSubscription, Integer>
{
	// Base Count 0
	@Query(value="SELECT COUNT(ani) FROM tbl_subscription WHERE DATE(sub_date_time)<DATE(SUBDATE(NOW(),0))", nativeQuery = true )
	Integer getTblSubBaseCount();
	
	// Active Count with nextBilledDate 0
	@Query(value="SELECT COUNT(ani) FROM tbl_subscription WHERE DATE(next_billed_date)>=DATE(SUBDATE(NOW(),1)) AND SubscriptionStatus='ACTIVE' ", nativeQuery = true )
	Integer getTblSubActiveCount();
	
	//----------------tbl_billing_success-----------
	
	// Daily sub type ani count 
	@Query(value="SELECT COUNT(ani) FROM tbl_billing_success WHERE DATE(DATETIME) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND type_event = 'SUB';\r\n"
			+ "", nativeQuery = true )
	Integer getTblBillSuccDailySubCount();
	
	// Daily ren type ani count 
	@Query(value="SELECT COUNT(ani) FROM tbl_billing_success WHERE DATE(DATETIME) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND type_event = 'REN'", nativeQuery = true )
	Integer getTblBillSuccDailyRenCount();
	
	// Daily unsub ani count 
	@Query(value="SELECT COUNT(ani) FROM tbl_unsub WHERE DATE(unsub_datetime) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY));\r\n"
			+ "", nativeQuery = true )
	Integer getTblUnsubCount();
	
	
	// Daily sub revenue count 
	@Query(value="SELECT SUM(deducted_amount) FROM tbl_billing_success WHERE DATE(DATETIME) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND type_event = 'SUB';\r\n"
			+ "", nativeQuery = true )
	Double getSubRevenue();
		
	// Daily ren revenue
	@Query(value="SELECT SUM(deducted_amount) FROM tbl_billing_success WHERE DATE(DATETIME) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND type_event = 'REN';\r\n"
			+ "", nativeQuery = true )
	Double getRenRevenue();
	
	
	// Daily ren revenue
	@Query(value="SELECT deducted_amount FROM tbl_billing_success WHERE deducted_amount IS NOT NULL LIMIT 1;\r\n"
			+ "", nativeQuery = true )
	Double getPice();
	

}
