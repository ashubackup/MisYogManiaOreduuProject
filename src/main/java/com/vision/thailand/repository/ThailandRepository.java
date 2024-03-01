package com.vision.thailand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vision.thailand.entity.TblSubscription;

@Repository
public interface ThailandRepository extends JpaRepository<TblSubscription, Integer>{
	

	@Query(value="SELECT COUNT(msisdn) FROM indexapp_subscription WHERE DATE(sub_date_time) < DATE(SUBDATE(NOW(),:date)) "
			+ "",nativeQuery = true)
	Integer baseCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM indexapp_subscription WHERE DATE(sub_date_time)=DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer activeCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(DISTINCT ani) FROM indexapp_billing_sucess WHERE DATE(DATETIME)=DATE(SUBDATE(NOW(),:date)) AND type_event='sub'\r\n"
			+ "",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(DISTINCT ani) FROM indexapp_billing_sucess WHERE DATE(DATETIME) = DATE(SUBDATE(NOW(), :date)) AND type_event = 'ren'\r\n"
			+ "",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(deducted_amount) FROM indexapp_billing_sucess WHERE DATE(DATETIME)=DATE(SUBDATE(NOW(),:date)) AND type_event='sub'\r\n"
			+ "",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(deducted_amount) FROM indexapp_billing_sucess WHERE DATE(DATETIME)=DATE(SUBDATE(NOW(),:date)) AND type_event='ren'\r\n"
			+ "",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(msisdn) FROM indexapp_subscription_unsub WHERE DATE(un_sub_date_time)=DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer unsubCount(@Param("date") int date);
	
	@Query(value="SELECT deducted_amount FROM indexapp_billing_sucess LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getPrice();
	

}
