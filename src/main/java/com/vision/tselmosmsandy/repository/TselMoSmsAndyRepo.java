package com.vision.tselmosmsandy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vision.tselmosmsandy.entity.TblSubscription;

@Repository
public interface TselMoSmsAndyRepo extends JpaRepository<TblSubscription, Integer>{
	

	@Query(value="SELECT COUNT(MSISDN) FROM tbl_subscription WHERE DATE(SUB_DATE_TIME) < DATE(SUBDATE(NOW(),:date)) ",nativeQuery = true)
	Integer baseCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(MSISDN) FROM tbl_subscription WHERE DATE(NEXT_REN_DATE)>=DATE(SUBDATE(NOW(),:date))"
			+ "",nativeQuery = true)
	Integer activeCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(MSISDN) FROM tbl_renewal_success WHERE DATE(REN_DATE)=DATE(SUBDATE(NOW(),:date)) AND TYPE='SUB'\r\n"
			+ "",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(MSISDN) FROM tbl_renewal_success WHERE DATE(REN_DATE)=DATE(SUBDATE(NOW(),:date)) AND TYPE='REN'\r\n"
			+ "",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(AMOUNT) FROM tbl_renewal_success WHERE DATE(REN_DATE)=DATE(SUBDATE(NOW(),:date)) AND TYPE='SUB'\r\n"
			+ "",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(AMOUNT) FROM tbl_renewal_success WHERE DATE(REN_DATE)=DATE(SUBDATE(NOW(),:date)) AND TYPE='REN'\r\n"
			+ "",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(MSISDN) FROM tbl_unsubscription WHERE DATE(DATE_TIME)=DATE(SUBDATE(NOW(),:date))\r\n"
			+ "",nativeQuery = true)
	Integer unsubCount(@Param("date") int date);
	
	@Query(value="SELECT AMOUNT FROM tbl_renewal_success WHERE AMOUNT IS NOT NULL LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getPrice();
	

}
