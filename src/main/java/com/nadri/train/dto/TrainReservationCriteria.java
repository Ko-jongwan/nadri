package com.nadri.train.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainReservationCriteria {
	private int userNo;
	private Date startDate;
	private Date endDate;
	private int pageNo;
	private int begin;
	private int end;
}
