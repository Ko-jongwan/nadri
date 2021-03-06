package com.nadri.train.web.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainPaymentDto {
	private long totalPrice;
	private int totalCount;
	private String reservationNo;
	private List<Integer> no;
	private List<String> name;
}
