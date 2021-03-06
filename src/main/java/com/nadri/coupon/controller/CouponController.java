package com.nadri.coupon.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nadri.coupon.form.CouponInsertForm;
import com.nadri.coupon.service.CouponService;
import com.nadri.coupon.service.UserCouponService;
import com.nadri.coupon.vo.Coupon;
import com.nadri.coupon.vo.UserCoupon;

@Controller
@RequestMapping("/coupon")
public class CouponController {

	@Autowired
	CouponService couponService;
	
	@Autowired
	UserCouponService userCouponService;
	
	@PostMapping("/addcou.nadri")
	public String save(CouponInsertForm form) throws IOException{
		
		Coupon coupon = new Coupon();
		
		BeanUtils.copyProperties(form, coupon);
		couponService.addNewCoupon(coupon);
		
		return "redirct:coupon/coulist";
	}
	
	@GetMapping("/coulist.nadri")
	public String list(@RequestParam(name = "page", required = false, defaultValue = "1")String page, Model model) {
		return "coupon/coulist";
		
	}
	
	@GetMapping("/delete.nadri")
	public String deleteform(@RequestParam(name="no")int no, Model model) {
		couponService.removeCoupon(no);
		return "redirect:coulist.nadri";
	}

	/* 생성관련 */
	@GetMapping("/insertform.nadri")
	public String insertform(Model model) {
		
		return "coupon/insertform";
	}
	
	@PostMapping("/insert.nadri")
	public String insert(Coupon coupon, Model model) {
		couponService.addNewCoupon(coupon);
		return "redirect:coulist.nadri";
	}
	
	/* 수정관련 */
	@GetMapping("/modiform.nadri")
	public String modifyform(int no, Model model) {		
		Coupon coupon = couponService.couponDetail(no);
		model.addAttribute("coupon", coupon);		
		return "coupon/modiform";	
	}
	
	@PostMapping("/update.nadri")
	public String modify(Coupon coupon, Model model) {	
		couponService.modifyCoupon(coupon);
		return "redirect:coulist.nadri";
	}
	
	@GetMapping("/zone.nari")
	public String couponZone(Model model) {
		List<UserCoupon> coupons = userCouponService.getAllValidCoupons();
		model.addAttribute("coupons",coupons);
		return "coupon/couponzone";
	}
	
}
