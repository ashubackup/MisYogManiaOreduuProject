package com.vision.kiddocrazeuae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vision.kiddocrazeuae.entity.TblSubscription;

@Repository
public interface KiddocrazeUaeRepository extends JpaRepository<TblSubscription, Integer>{
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE DATE(subDateTime) < DATE(SUBDATE(NOW(),:date)) AND pack_name=:packname ",nativeQuery = true)
	Integer baseCount(@Param("date") int date, @Param("packname") String packname);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription WHERE pack_name=:packname AND DATE(next_billed_date) IS NOT NULL; ",nativeQuery = true)
	Integer activeCount(@Param("packname") String packname);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='SUBSCRIBE' AND pack_name=:packname ",nativeQuery = true)
	Integer dailySubCount(@Param("date") int date, @Param("packname") String packname);
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='RENEW' AND pack_name=:packname",nativeQuery = true)
	Integer dailyRenCount(@Param("date") int date, @Param("packname") String packname);
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='SUBSCRIBE' AND pack_name=:packname",nativeQuery = true)
	Double dailySubRevenue(@Param("date") int date, @Param("packname") String packname);
	
	@Query(value="SELECT SUM(amount) FROM tbl_billing_success WHERE DATE(billing_date)=DATE(SUBDATE(NOW(),:date)) AND type_event='RENEW' AND pack_name=:packname ",nativeQuery = true)
	Double dailyRenRevenue(@Param("date") int date, @Param("packname") String packname);
	
	
	@Query(value="SELECT COUNT(msisdn) FROM tbl_subscription_unsub WHERE DATE(unsubdatetime)=DATE(SUBDATE(NOW(),:date)) AND pack_name=:packname ",nativeQuery = true)
	Integer unsubCount(@Param("date") int date, @Param("packname") String packname);
	
	@Query(value="SELECT amount FROM tbl_billing_success WHERE amount IS NOT NULL LIMIT 1;",nativeQuery = true)
	Double getPrice();
	
	
	
}
