package com.vision.gamedubia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vision.gamedubia.entity.TblSubscription;



@Repository
public interface GameDubiaRepository extends JpaRepository<TblSubscription, Integer>{
	
	@Query(value="SELECT COUNT(ani) FROM tbl_subscription WHERE DATE(sub_date_time) < DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer baseCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(ani) FROM tbl_subscription WHERE DATE(sub_date_time)=DATE(SUBDATE(NOW(),:date))",nativeQuery = true)
	Integer activeCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(ANI) FROM tbl_billing_success WHERE DATE(PROCESS_DATETIME)=DATE(SUBDATE(NOW(),:date)) AND TYPE_EVENT='SUB'\r\n"
			+ "",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(ANI) FROM tbl_billing_success WHERE DATE(PROCESS_DATETIME)=DATE(SUBDATE(NOW(),:date)) AND TYPE_EVENT='REN'\r\n"
			+ "",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(DEDUCTED_AMOUNT) FROM tbl_billing_success WHERE DATE(PROCESS_DATETIME)=DATE(SUBDATE(NOW(),:date)) AND TYPE_EVENT='SUB'\r\n"
			+ "",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(DEDUCTED_AMOUNT) FROM tbl_billing_success WHERE DATE(PROCESS_DATETIME)=DATE(SUBDATE(NOW(),:date)) AND TYPE_EVENT='REN'\r\n"
			+ "",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(ANI) FROM tbl_subscription_unsub WHERE DATE(unsub_date_time)=DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer unsubCount(@Param("date") int date);
	
	@Query(value="SELECT DEDUCTED_AMOUNT FROM tbl_billing_success WHERE DEDUCTED_AMOUNT IS NOT NULL LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getPrice();
	
	
	
}
