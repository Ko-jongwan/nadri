package com.nadri.air.vo;

import java.util.Date;

import com.nadri.user.vo.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Passenger {
	private int no;
	private User user;
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String country;
	private String gender;
	private Date birth;
	private int phone;
	private String address;
}
