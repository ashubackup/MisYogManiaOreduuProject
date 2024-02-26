package com.vision.kiddocrazeuae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vision.kiddocrazeuae.entity.TblSubscription;



@Repository
public interface KiddocrazeUaeRepository extends JpaRepository<TblSubscription, Integer>{
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(subDateTime) < DATE(SUBDATE(NOW(),:date)) AND pack_name='KIDDOCRAZE'",nativeQuery = true)
	Integer baseCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(next_billed_date)>=DATE(SUBDATE(NOW(),:date)) AND pack_name='KIDDOCRAZE'",nativeQuery = true)
	Integer activeCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),1)) AND type_event='sub' AND pack_name='KIDDOCRAZE'\r\n"
			+ " ",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),1)) AND type_event='ren' AND pack_name='KIDDOCRAZE'\r\n"
			+ "",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date);
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),1)) AND type_event='sub' AND pack_name='KIDDOCRAZE'\r\n"
			+ "",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date);
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),1)) AND type_event='ren' AND pack_name='KIDDOCRAZE'\r\n"
			+ "",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date);
	
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription_unsub WHERE DATE(unsubdatetime)=DATE(SUBDATE(NOW(),1)) AND pack_name='KIDDOCRAZE'\r\n"
			+ "",nativeQuery = true)
	Integer unsubCount(@Param("date") int date);
	
	@Query(value="SELECT amount FROM tbl_billing_success WHERE amount IS NOT NULL LIMIT 1;\r\n"
			+ "",nativeQuery = true)
	Double getPrice();
	
	
	
}
