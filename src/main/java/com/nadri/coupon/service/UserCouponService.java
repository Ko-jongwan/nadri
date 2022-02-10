package com.nadri.coupon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nadri.coupon.dao.UserCouponDao;
import com.nadri.coupon.vo.UserCoupon;

@Service
@Transactional
public class UserCouponService {

	@Autowired
	UserCouponDao userCouponDao;
	
	/* 유저No로 쿠폰 추가 */
	public void insertCouponByUserNo(int couponNo, int userNo) {
		userCouponDao.getCoupon(couponNo, userNo);
	}
	
	/* 유저 보유 쿠폰 목록 */
	public List<UserCoupon> getCouponList(int userNo){
		return userCouponDao.couponList(userNo);
	}
	
	/* 유저 보유 쿠폰 갯수 */
	public int getCouponCount(int userNo) {
		return userCouponDao.couponCount(userNo);
	}
	
	public List<UserCoupon> getCou(){ return userCouponDao.cou(); }
	
}