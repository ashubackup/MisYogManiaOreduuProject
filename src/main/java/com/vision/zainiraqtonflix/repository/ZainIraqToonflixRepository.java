package com.vision.zainiraqtonflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vision.zainiraqtonflix.entity.TblSubscription;





@Repository
public interface ZainIraqToonflixRepository extends JpaRepository<TblSubscription, Integer>{
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(subDateTime) < DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer baseCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(nextBillingDate)>=DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer activeCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billingDateTime)=DATE(SUBDATE(NOW(),:date)) AND type_event='sub'\r\n"
			+ "",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billingDateTime)=DATE(SUBDATE(NOW(),:date)) AND type_event='ren'\r\n"
			+ "",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(price) FROM tbl_billing_success WHERE DATE(billingDateTime)=DATE(SUBDATE(NOW(),:date)) AND type_event='sub'\r\n"
			+ "",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(price) FROM tbl_billing_success WHERE DATE(billingDateTime)=DATE(SUBDATE(NOW(),:date)) AND type_event='ren'\r\n"
			+ "",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_unsubscription WHERE DATE(unSubDateTime)=DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer unsubCount(@Param("date") int date);
	
	@Query(value="SELECT price FROM tbl_billing_success WHERE price IS NOT NULL LIMIT 1;",nativeQuery = true)
	Double getPrice();
	
	
	
}
