package com.vision.jangukids.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vision.jangukids.entity.TblSubscription;

@Repository
public interface JanguKidsRepository extends JpaRepository<TblSubscription, Integer>{
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(subDateTime) < DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer baseCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(next_billed_date)>=DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer activeCount(@Param("date") int date);
	
	
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Subscription' AND packtype='DAILY'\r\n"
			+ "",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Subscription' AND packtype='WEEKLY'\r\n"
			+ "",nativeQuery = true)
	Integer weeklySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Subscription' AND packtype='MONTHLY'\r\n"
			+ "",nativeQuery = true)
	Integer monthlySubCount(@Param("date") int date);
	
	
	
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Renewal' AND packtype='DAILY'\r\n"
			+ "",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date);
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Renewal' AND packtype='WEEKLY'\r\n"
			+ "",nativeQuery = true)
	Integer weeklyRenCount(@Param("date") int date);
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Renewal' AND packtype='MONTHLY'\r\n"
			+ "",nativeQuery = true)
	Integer monthlyRenCount(@Param("date") int date);
	
	
	
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Subscription' AND packtype='DAILY'\r\n"
			+ "",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Subscription' AND packtype='WEEKLY'\r\n"
			+ "",nativeQuery = true)
	Double weeklySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Subscription' AND packtype='MONTHLY'\r\n"
			+ "",nativeQuery = true)
	Double monthlySubRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Renewal' AND packtype='DAILY'\r\n"
			+ "",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date);
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Renewal' AND packtype='WEEKLY'\r\n"
			+ "",nativeQuery = true)
	Double weeklyRenRevenue(@Param("date") int date);
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='Renewal' AND packtype='MONTHLY'\r\n"
			+ "",nativeQuery = true)
	Double monthlyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription_unsub WHERE DATE(unsubdatetime)=DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer unsubCount(@Param("date") int date);
	
	@Query(value="SELECT amount FROM tbl_billing_success WHERE amount IS NOT NULL AND  packtype='DAILY' LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getDailyPrice();
	
	@Query(value="SELECT amount FROM tbl_billing_success WHERE amount IS NOT NULL AND  packtype='WEEKLY' LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getWeeklyPrice();
	
	@Query(value="SELECT amount FROM tbl_billing_success WHERE amount IS NOT NULL AND  packtype='MONTHLY' LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getMonthlyPrice();
	
	
	
}
