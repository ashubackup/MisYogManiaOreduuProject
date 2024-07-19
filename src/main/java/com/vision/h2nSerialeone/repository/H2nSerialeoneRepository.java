package com.vision.h2nSerialeone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vision.h2nSerialeone.entity.TableSubscription;


@Repository
public interface H2nSerialeoneRepository extends JpaRepository<TableSubscription, Integer>{
	
	@Query(value="SELECT COUNT(ani) FROM tbl_subscription WHERE DATE(sub_date_time) < DATE(SUBDATE(NOW(),:date))"
			+ "",nativeQuery = true)
	Integer baseCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(ani) FROM tbl_subscription WHERE DATE(next_billed_date) IS NOT NULL; ",nativeQuery = true)
	Integer activeCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(ani) FROM tbl_billing_success WHERE DATE(lastBilledDate)=DATE(SUBDATE(NOW(),:date)) AND type_event='sub' AND packType='Daily' ",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(ani) FROM tbl_billing_success WHERE DATE(lastBilledDate)=DATE(SUBDATE(NOW(),:date)) AND type_event='ren' AND packType='Daily' \r\n"
			+ "",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(lastBilledDate)=DATE(SUBDATE(NOW(),:date)) AND type_event='sub' AND packType='Daily'",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(lastBilledDate)=DATE(SUBDATE(NOW(),:date)) AND type_event='ren' AND packType='Daily'",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date);
	
	
	//--------weekly--------
	@Query(value="SELECT COUNT(ani) FROM tbl_billing_success WHERE DATE(lastBilledDate)=DATE(SUBDATE(NOW(),:date)) AND type_event='sub' AND packType='Weekly' ",nativeQuery = true)
	Integer weeklySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(ani) FROM tbl_billing_success WHERE DATE(lastBilledDate)=DATE(SUBDATE(NOW(),:date)) AND type_event='ren' and packType='Weekly' \r\n"
			+ "",nativeQuery = true)
	Integer weeklyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(lastBilledDate)=DATE(SUBDATE(NOW(),:date)) AND type_event='sub' AND packType='Weekly'",nativeQuery = true)
	Double weeklySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(lastBilledDate)=DATE(SUBDATE(NOW(),:date)) AND type_event='ren' AND packType='Weekly'",nativeQuery = true)
	Double weeklyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(ani) FROM tbl_subscription_unsub WHERE DATE(unsub_date_time)=DATE(SUBDATE(NOW(),:date)) ",nativeQuery = true)
	Integer unsubCount(@Param("date") int date);
	
	@Query(value="SELECT amount FROM tbl_billing_success WHERE amount IS NOT NULL  and packType='Daily' LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getDailyPrice();
	
	@Query(value="SELECT amount FROM tbl_billing_success WHERE amount IS NOT NULL  and packType='Weekly' LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getWeeklyPrice();
	
}
