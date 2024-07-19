package com.vision.omanoredu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vision.omanoredu.entity.TblSubscription;




@Repository
public interface OmanOreduRepository extends JpaRepository<TblSubscription, Integer>{
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(sub_date_time) < DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer baseCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(nextBilledDateTime) IS NOT NULL;",nativeQuery = true)
	Integer activeCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billedDateTime)=DATE(SUBDATE(NOW(),:date)) AND typeEvent='sub' AND packType='DAILY'"
			+ "",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billedDateTime)=DATE(SUBDATE(NOW(),:date)) AND typeEvent='ren' AND packType='DAILY'"
			+ "",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(totalCharged) FROM tbl_billing_success WHERE DATE(billedDateTime)=DATE(SUBDATE(NOW(),:date)) AND typeEvent='sub' AND packType='DAILY'"
			+ "",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(totalCharged) FROM tbl_billing_success WHERE DATE(billedDateTime)=DATE(SUBDATE(NOW(),:date)) AND typeEvent='ren' AND packType='DAILY'"
			+ "",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date);
	
	//---Weekly----------
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billedDateTime)=DATE(SUBDATE(NOW(),:date)) AND typeEvent='sub' AND packType='WEEKLY'"
			+ "",nativeQuery = true)
	Integer weeklySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billedDateTime)=DATE(SUBDATE(NOW(),:date)) AND typeEvent='ren' AND packType='WEEKLY'"
			+ "",nativeQuery = true)
	Integer weeklyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(totalCharged) FROM tbl_billing_success WHERE DATE(billedDateTime)=DATE(SUBDATE(NOW(),:date)) AND typeEvent='sub' AND packType='WEEKLY'"
			+ "",nativeQuery = true)
	Double weeklySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(totalCharged) FROM tbl_billing_success WHERE DATE(billedDateTime)=DATE(SUBDATE(NOW(),:date)) AND typeEvent='ren' AND packType='WEEKLY'"
			+ "",nativeQuery = true)
	Double weeklyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_unsubscription WHERE DATE(unsub_date_time)=DATE(SUBDATE(NOW(),:date)) AND packType='DAILY'\r\n"
			+ "",nativeQuery = true)
	Integer dailyUnsubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_unsubscription WHERE DATE(unsub_date_time)=DATE(SUBDATE(NOW(),:date)) AND packType='WEEKLY'\r\n"
			+ "",nativeQuery = true)
	Integer weeklyUnsubCount(@Param("date") int date);
	
	@Query(value="SELECT totalCharged FROM tbl_billing_success WHERE totalCharged IS NOT NULL AND packType='DAILY' LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getDailyPrice();
	
	@Query(value="SELECT totalCharged FROM tbl_billing_success WHERE totalCharged IS NOT NULL AND packType='WEEKLY' LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getWeeklyPrice();
	
	
	
}
