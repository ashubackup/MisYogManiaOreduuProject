package com.vision.dbthree.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vision.dbone.entity.TblSubscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="tbl_subscription")
public class TableSubscription {
	
	@Id
	@Column(name="id")
	private Integer id;

}
