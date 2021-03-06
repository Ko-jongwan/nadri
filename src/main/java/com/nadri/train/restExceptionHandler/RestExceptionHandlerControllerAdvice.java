package com.nadri.train.restExceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.nadri.train.exception.KakaoException;
import com.nadri.train.exception.LoginException;
import com.nadri.train.exception.ReservationException;
import com.nadri.train.service.TrainService;
import com.nadri.train.web.model.ResponseDto;

@RestControllerAdvice
public class RestExceptionHandlerControllerAdvice {
	
	@Autowired
	TrainService service;
	
	@ExceptionHandler(LoginException.class)
	public ResponseDto<?> handleLoginException(LoginException e) {
		ResponseDto<?> response = new ResponseDto<>();
		response.setStatus("FAIL");
		response.setError(e.getMessage());
		return response;
	}
	@ExceptionHandler(ReservationException.class)
	public ResponseDto<?> handleReservationException(ReservationException e) {
		ResponseDto<?> response = new ResponseDto<>();
		response.setStatus("FAIL");
		response.setError(e.getMessage());
		return response;
	}
	
	// exceptionHandler ajax의 error로 객체 값이 가는 것인가?
	// 요청 바디부에 있는 ajax로 부터 받은 값을 사용할 수 있나?
	@ExceptionHandler(KakaoException.class)
	public ResponseDto<?> handlerKakaoException(KakaoException e) {
		ResponseDto<?> response = new ResponseDto<>();
		
		response.setStatus("FAIL");
		response.setError(e.getMessage());
		return response;
	}
}
