package com.vision.kiddocraze.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vision.kiddocraze.entity.TblSubscription;

@Repository
public interface KiddocrazeRepository extends JpaRepository<TblSubscription, Integer>{
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(subDateTime) < DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer baseCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(SUBDATE)=DATE(SUBDATE(NOW(),:date))",nativeQuery = true)
	Integer activeCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billingDateTime)=DATE(SUBDATE(NOW(),:date)) AND type_event='sub'",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billingDateTime)=DATE(SUBDATE(NOW(),:date)) AND type_event='ren'\r\n"
			+ "",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(chargeAmount) FROM tbl_billing_success WHERE DATE(billingDateTime)=DATE(SUBDATE(NOW(),:date)) AND type_event='sub'\r\n"
			+ "",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(chargeAmount) FROM tbl_billing_success WHERE DATE(billingDateTime)=DATE(SUBDATE(NOW(),:date)) AND type_event='ren'\r\n"
			+ "",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_unsubscription WHERE DATE(unSubDateTime)=DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer unsubCount(@Param("date") int date);
	
	@Query(value="SELECT chargeAmount FROM tbl_billing_success WHERE chargeAmount IS NOT NULL LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getPrice();
	
	
	
}
