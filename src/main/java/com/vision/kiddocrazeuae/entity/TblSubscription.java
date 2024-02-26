package com.vision.kiddocrazeuae.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="tbl_subscription")
public class TblSubscription 
{
	@Id
	@Column(name="id")
	private Integer id;
	

	

}
