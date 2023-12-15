package com.vision.dbtwo.entity;

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
public class TblSub 
{
	@Id
	@Column(name="id")
	private Integer id;
	

	

}
