package com.vision.dbthree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vision.dbthree.entity.TableSubscription;

@Repository
public interface ITableSubscriptionRepo extends JpaRepository<TableSubscription, Integer>{
	
		@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(subDateTime)<DATE(SUBDATE(NOW(),0))\r\n"
				+ "", nativeQuery = true )
		Integer getBaseCount();
		
		@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(subDateTime)<DATE(SUBDATE(NOW(),1)) AND DATE(nextBilledDateTime)>=DATE(SUBDATE(NOW(),1))", nativeQuery = true )
		Integer getActiveCount();
		
		//----------------tbl_billing_success-----------
		
		@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billingDateTime) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND type_event = 'SUB'", nativeQuery = true )
		Integer getDailySubCount();
		
		@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billingDateTime) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND type_event = 'REN'", nativeQuery = true )
		Integer getDailyRenCount();
		
		@Query(value="SELECT COUNT(msisdn) FROM tbl_unsubscription WHERE DATE(un_sub_date_time) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND STATUS='UNSUBSCRIBE';\r\n"
				+ "", nativeQuery = true )
		Integer getUnsubCount();
		
		
		@Query(value="SELECT SUM(rate) FROM tbl_billing_success WHERE DATE(billingDateTime) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND type_event = 'SUB'", nativeQuery = true )
		Double getSubRevenue();
			
		@Query(value="SELECT SUM(rate) FROM tbl_billing_success WHERE DATE(billingDateTime) = DATE(DATE_SUB(NOW(), INTERVAL 1 DAY)) AND type_event = 'REN'\r\n"
				+ "", nativeQuery = true )
		Double getRenRevenue();
		
		
		@Query(value="SELECT rate FROM tbl_billing_success WHERE rate IS NOT NULL LIMIT 1;", nativeQuery = true )
		Double getPice();
		
	

}
