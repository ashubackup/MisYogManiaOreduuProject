package com.vision.ethopiagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vision.ethopiagames.entity.TblSubscription;

@Repository
public interface EthopiaGamesRepository extends JpaRepository<TblSubscription, Integer>{
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(sub_date_time) < DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer baseCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(next_billed_date) IS NOT NULL;",nativeQuery = true)
	Integer activeCount(@Param("date") int date);
	
	//---------new
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(sub_date_time) < DATE(SUBDATE(NOW(),:date)) AND serviceName=:serviceName",nativeQuery = true)
	Integer baseCountByService(@Param("date") int date, @Param("serviceName") String serviceName);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE serviceName=:serviceName AND DATE(next_billed_date) IS NOT NULL;",nativeQuery = true)
	Integer activeCountByService(@Param("serviceName") String serviceName);
	

	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(sub_date_time)=DATE(SUBDATE(NOW(),:date)) AND PASSWORD<>123 AND serviceName='game-station' ",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(process_datetime)=DATE(SUBDATE(NOW(),:date)) AND serviceName='game-station' ",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(amount) FROM tbl_subscription WHERE DATE(sub_date_time)=DATE(SUBDATE(NOW(),:date)) AND serviceName='game-station' ",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(deducted_amount) FROM tbl_billing_success WHERE DATE(process_datetime)=DATE(SUBDATE(NOW(),:date)) AND serviceName='game-station' ",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription_unsub WHERE DATE(unsub_date_time)=DATE(SUBDATE(NOW(),:date)) AND serviceName='game-station' ",nativeQuery = true)
	Integer unsubCount(@Param("date") int date);
	
	@Query(value="SELECT deducted_amount FROM tbl_billing_success WHERE deducted_amount IS NOT NULL LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getPrice();
	
	
	//----------------------sub service esports----
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(sub_date_time)=DATE(SUBDATE(NOW(),:date)) AND PASSWORD<>123  AND serviceName='esport'\r\n"
			+ "",nativeQuery = true)
	Integer esportDailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(process_datetime)=DATE(SUBDATE(NOW(),:date)) AND serviceName='esport'\r\n"
			+ "",nativeQuery = true)
	Integer esportDailyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(amount) FROM tbl_subscription WHERE DATE(sub_date_time)=DATE(SUBDATE(NOW(),:date)) AND serviceName='esport'\r\n"
			+ "",nativeQuery = true)
	Double esportDailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(deducted_amount) FROM tbl_billing_success WHERE DATE(process_datetime)=DATE(SUBDATE(NOW(),:date)) AND serviceName='esport'\r\n"
			+ "",nativeQuery = true)
	Double esportDailyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription_unsub WHERE DATE(unsub_date_time)=DATE(SUBDATE(NOW(),:date)) AND serviceName='esport'\r\n"
			+ "",nativeQuery = true)
	Integer esportUnsubCount(@Param("date") int date);
	

	
	
	
	
}
