package com.nadri.train.web.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nadri.train.dto.TrainCriteria;
import com.nadri.train.dto.TrainSearchDto;
import com.nadri.train.exception.KakaoException;
import com.nadri.train.service.TrainService;
import com.nadri.train.util.RefundUtils;
import com.nadri.train.util.SessionUtils;
import com.nadri.train.vo.TrainReservation;
import com.nadri.train.vo.TrainTicket;
import com.nadri.train.web.model.TrainSearchForm;
import com.nadri.user.annotation.LoginedUser;
import com.nadri.user.vo.User;

@Controller
@RequestMapping("/train")
public class TrainController {
	
	static final Logger log = LogManager.getLogger(TrainController.class);
	
	@Autowired
	TrainService service;
	
	@GetMapping("/fail.nadri")
	public String error() {
		return "train/error/failPayment";
	}
	
	/**
	 * 기차 스케줄 검색 창
	 * @return
	 */
	@GetMapping
	public String search(Model model) {
		model.addAttribute("favoriteList", service.getFavoriteRoute());
		return "train/trainSearch";
	}
	
	/**
	 * 검색 정보를 바탕으로 기차 스케줄(편도면 1개, 왕복면 2개) 불러오기
	 * @param searchForm 검색 정보
	 * @return
	 * @throws ParseException 
	 */
	@PostMapping
	public String list(TrainSearchForm searchForm, Model model) {
		TrainCriteria criteria = new TrainCriteria();
		criteria.setDepartureStation(searchForm.getDepartureStation());
		criteria.setArrivalStation(searchForm.getArrivalStation());
		criteria.setDepartureDate(searchForm.getDpDate1(), searchForm.getDpTime1());
		criteria.setRowNo(searchForm.getRowNo1());
		
		List<TrainSearchDto> schedules1 = service.getSchedulesByCriteria(criteria);
		model.addAttribute("schedules1", schedules1);
		
		if ("왕복".equals(searchForm.getWay())) {
			criteria = new TrainCriteria();
			criteria.setDepartureStation(searchForm.getArrivalStation());
			criteria.setArrivalStation(searchForm.getDepartureStation());
			criteria.setDepartureDate(searchForm.getDpDate2(), searchForm.getDpTime2());
			criteria.setRowNo(searchForm.getRowNo2());
			
			List<TrainSearchDto> schedules2 = service.getSchedulesByCriteria(criteria);
			model.addAttribute("schedules2", schedules2);
			model.addAttribute("way", "왕복");
		}
		
		model.addAttribute("searchForm", searchForm); 
		return "train/trainList";
	}
	
	/**
	 * 예약 확인 페이지
	 * @return
	 */
	@GetMapping("/confirmReservation.nadri")
	public String reservedTrain(@LoginedUser User user, int reservedNo1, @RequestParam(name="reservedNo2", required=false, defaultValue= "0") int reservedNo2, Model model) {
		List<TrainReservation> reservationList = service.getReservationByNo(user.getNo() ,reservedNo1, reservedNo2);
		List<TrainTicket> ticketList = service.getTicketByReservedNo(reservedNo1, reservedNo2);
		model.addAttribute("reservation1", reservationList.get(0));
		List<TrainTicket> ticket1 = new ArrayList<>();
		List<TrainTicket> ticket2 = new ArrayList<>();
		for (TrainTicket ticket : ticketList) {
			if (ticket.getReservationNo() == reservationList.get(0).getNo()) {
				ticket1.add(ticket);
			} else {
				ticket2.add(ticket);
			}
		}
		model.addAttribute("ticket1", ticket1);
		
		if (reservedNo2 != 0) {
			model.addAttribute("reservation2", reservationList.get(1));
			model.addAttribute("ticket2", ticket2);
		}
		return "train/confirmReservation";
	}
	
	/**
	 * 사용자 승차권 목록 페이지
	 * @param user
	 * @param model
	 * @return
	 */
	@GetMapping("/reservationList.nadri")
	public String reservatioinList(@LoginedUser User user, Model model) {
		List<TrainReservation> reservationList = service.getReservationByUserNo(user.getNo());
		model.addAttribute("reservationList", reservationList);
		return "train/reservationList";
	}
	
	/**
	 * 결제 창
	 * @param user
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/payment.nadri")
	public String payment(@LoginedUser User user , HttpServletRequest request, Model model) {
		String[] reservationNo = request.getParameterValues("reservationNo");
		int reservationNo1 = Integer.parseInt(reservationNo[0]);
		int reservationNo2 = 0;
		if (reservationNo.length == 2) {
			reservationNo2 = Integer.parseInt(reservationNo[1]);
		}
		List<TrainReservation> reservationList = service.getReservationByNo(user.getNo() , reservationNo1, reservationNo2);
		List<TrainTicket> ticketList = service.getTicketByReservedNo(reservationNo1, reservationNo2);
		model.addAttribute("reservation1", reservationList.get(0));
		List<TrainTicket> ticket1 = new ArrayList<>();
		List<TrainTicket> ticket2 = new ArrayList<>();
		for (TrainTicket ticket : ticketList) {
			if (ticket.getReservationNo() == reservationList.get(0).getNo()) {
				ticket1.add(ticket);
			} else {
				ticket2.add(ticket);
			}
		}
		model.addAttribute("ticket1", ticket1);
		
		if (reservationNo.length == 2) {
			model.addAttribute("reservation2", reservationList.get(1));
			model.addAttribute("ticket2", ticket2);
		}
		return "train/paymentReservation";
	}
	
	// 결제 승인 신청
	@GetMapping("/kakaoPayment.nadri")
	public String kakaoPayment(@LoginedUser User user, String pg_token, Model model) throws IOException {
		StringBuffer outPutData = new StringBuffer();
		String reservationNo = (String)SessionUtils.getAttribute("reservationNo");
		String[] noList = reservationNo.split(" ");
		outPutData.append("cid=TC0ONETIME")
				.append("&tid=").append((String)SessionUtils.getAttribute("tid"))
				.append("&partner_order_id=").append(reservationNo)
				.append("&partner_user_id=").append(user.getId())
				.append("&pg_token=").append(pg_token);
		
		URL address = new URL("https://kapi.kakao.com/v1/payment/approve");
		HttpURLConnection conn = (HttpURLConnection) address.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "KakaoAK 5fa0f0222e9a68676ec86330e233e3e7");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		conn.setDoOutput(true);
		
		OutputStream outPut = conn.getOutputStream();
		DataOutputStream data = new DataOutputStream(outPut);
		data.writeBytes(outPutData.toString());
		data.flush();
		data.close();
		
		BufferedReader rd;
		if(conn.getResponseCode() == 200) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			if (noList.length == 1) {
				TrainReservation reservation = service.getReservationOne(user.getNo(), Integer.parseInt(noList[0]));
				reservation.setTickectStatus("결제");
				reservation.setSoldDate(new Date());
				reservation.setTid((String)SessionUtils.getAttribute("tid"));
				service.updateReservation(reservation);
				SessionUtils.removeAttribute("tid");
				SessionUtils.removeAttribute("reservationNo");
				return "redirect:/train/resultPayment.nadri?reservationNo1=" + noList[0];
			} else {
				List<TrainReservation> reservationList = service.getReservationByNo(user.getNo(), Integer.parseInt(noList[0]), Integer.parseInt(noList[1]));
				for (TrainReservation reservation: reservationList) {
					reservation.setTickectStatus("결제");
					reservation.setSoldDate(new Date());
					reservation.setTid((String)SessionUtils.getAttribute("tid"));
					service.updateReservation(reservation);
				}
				SessionUtils.removeAttribute("tid");
				SessionUtils.removeAttribute("reservationNo");
				return "redirect:/train/resultPayment.nadri?reservationNo1=" + noList[0] + "&reservationNo2=" + noList[1];
			}
		} else {
			throw new KakaoException("진행중인 거래가 있습니다. 잠시 후 다시 시도해 주세요.");
		}
	}
	
	/**
	 * 결제 후 결과 창
	 * @param user
	 * @param reservationNo1
	 * @param reservationNo2
	 * @param model
	 * @return
	 */
	@GetMapping("/resultPayment.nadri")
	public String resultPayment(@LoginedUser User user, int reservationNo1, @RequestParam(name="reservationNo2", required=false, defaultValue= "0") int reservationNo2, Model model) {
		List<TrainReservation> reservationList = service.getReservationByNo(user.getNo(), reservationNo1, reservationNo2);
		long totalPrice = 0;
		for(TrainReservation reservation : reservationList) {
			totalPrice += reservation.getTotalPrice();
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("soldDate", reservationList.get(0).getSoldDate());
		return "train/resultPayment";
	}

	@GetMapping("/failPayment.nadri")
	public String fail() {
		return "train/failPayment";
	}
	
	/**
	 * 수정 페이지 
	 * @param reservationNo
	 * @return
	 */
	@GetMapping("/modify.nadri")
	public String modify(@LoginedUser User user, int reservationNo, Model model) {
		try {
			TrainReservation reservation = service.getReservationOne(user.getNo(), reservationNo);
			model.addAttribute("reservation" ,reservation);
			List<TrainTicket> ticketList = service.getTicketByReservedNo(reservationNo, 0);
			model.addAttribute("ticketList", ticketList);
			return "train/modifyReservation";
		} catch (IndexOutOfBoundsException e) {
			return "train/reservationList";
		}
	}
	
	// 예약 번호를 받아서 refund티켓 정보를 알아내기
	@GetMapping("/refund.nadri")
	public String refundList(@LoginedUser User user, int reservationNo1, Model model) {
		TrainReservation reservation = service.getReservationOne(user.getNo(), reservationNo1);
		model.addAttribute("reservation" ,reservation);
		List<TrainTicket> ticketList = service.getTicketByReservedNo(reservationNo1, 0);
		model.addAttribute("ticketList", ticketList);
		
		return "train/refundList";
	}
	
	/**
	 * 환불페이지로 이동
	 * @param user
	 * @param ticketNums
	 * @param reservationNo
	 * @param model
	 * @return
	 */
	@PostMapping("/refund.nadri")
	public String refund(@LoginedUser User user, @RequestParam(name = "ticketNo") List<Integer> ticketNums, @RequestParam int reservationNo , Model model) {
		List<TrainTicket> ticketList = service.getTicketByNo(ticketNums);
		TrainReservation reservation = service.getReservationOne(user.getNo(), ticketList.get(0).getReservationNo());
		long refundPrice = 0;
		long refundRate = 0;
		
		for (TrainTicket ticket : ticketList) {
			log.info("가격:"+ ticket.getPrice());
			refundRate += RefundUtils.refundRate(ticket.getPrice(), reservation.getDepartureTime());
			refundPrice += ticket.getPrice();
		}
		refundPrice -= refundRate;
		model.addAttribute("refundPrice", refundPrice);
		model.addAttribute("refundRate", refundRate);
		model.addAttribute("ticketList", ticketList);
		model.addAttribute("reservation", reservation);
		
		return "train/refundPayment";
	}
	
	/**
	 * 이용내역 조회
	 * @param user
	 * @return
	 */
	@GetMapping("/search.nadri")
	public String search(@LoginedUser User user) {
		return "train/searchReservation";
	}
		
//  스케줄 값 늘리는 메소드
//	@GetMapping("/insert.do")
//	public void insertSchedule() {
//		
//		List<TrainSchedule> schedules = service.getAllSchedules();
//		List<TrainSchedule> newSchedules = new ArrayList<>();
//		// 부터 시작!
//		for (int i=31; i<41; i++) {
//			for(TrainSchedule schedule : schedules) {
//				TrainSchedule nwschedule = new TrainSchedule(); 
//				Calendar cal = Calendar.getInstance();
//					
//				cal.setTime(schedule.getDepartureTime());
//				cal.add(Calendar.DATE, i);
//				nwschedule.setDepartureTime(cal.getTime());
//				
//				cal.setTime(schedule.getArrivalTime()); 
//				cal.add(Calendar.DATE, i); 
//				nwschedule.setArrivalTime(cal.getTime());
//				
//				nwschedule.setTrainNo(schedule.getTrainNo());
//				nwschedule.setRouteNo(schedule.getRouteNo());
//				newSchedules.add(nwschedule);
//			}
//		}
//		service.addNewSchedule(newSchedules);
//	}
}
