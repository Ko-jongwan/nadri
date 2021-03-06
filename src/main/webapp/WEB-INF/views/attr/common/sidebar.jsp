<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="col-3 mt-1">
<div class="sidebar">	
<form id="form-search" action="list.nadri" method="get">
   	<div class="col text-center">
   		<h2><strong>${place==""?'전국':place }</strong></h2>
 		<input name="place" value="${place }" type="hidden">
	</div>
	<div class="d-flex flex-column flex-shrink-0 p-1"
		style="width: 250px;">
		<span class="fs-4"><strong>지역</strong></span>
		<hr>
		<ul class="nav flex-column">
			<li>
				<a class="btn" 
				href="list.nadri?place=&category=${param.category }&keyword=${param.keyword }&startdate=${param.startdate }&enddate=${param.enddate }" 
				aria-current="page" style="font-weight:${place==''?'bold':''};">
				전국
				</a>
			</li>
			<li class="nav-item place">
				<a class="btn" href="list.nadri?place=서울&category=${param.category }&keyword=${param.keyword }&startdate=${param.startdate }&enddate=${param.enddate }" 
					aria-current="page" style="font-weight:${place=='서울'?'bold':''};">
				서울
				</a>
			</li>
			<li class="nav-item">
				<a class="btn active" href="list.nadri?place=경기도&category=${param.category }&keyword=${param.keyword }&startdate=${param.startdate }&enddate=${param.enddate }" 
					aria-current="page" style="font-weight:${place=='경기도'?'bold':''};">
				경기도
				</a>
			</li>
			<li class="nav-item">
				<a class="btn" href="list.nadri?place=강원도&category=${param.category }&keyword=${param.keyword }&startdate=${param.startdate }&enddate=${param.enddate }" 
					aria-current="page" style="font-weight:${place=='강원도'?'bold':''};">
				강원도
				</a>
			</li>
			<li class="nav-item">
				<a class="btn" href="list.nadri?place=전라도&category=${param.category }&keyword=${param.keyword }&startdate=${param.startdate }&enddate=${param.enddate }" 
					aria-current="page" style="font-weight:${place=='전라도'?'bold':''};">
				전라도
				</a>
			</li>
			<li class="nav-item">
				<a class="btn" href="list.nadri?place=충청도&category=${param.category }&keyword=${param.keyword }&startdate=${param.startdate }&enddate=${param.enddate }" 
					aria-current="page" style="font-weight:${place=='충청도'?'bold':''};">
				충청도
				</a>
			</li>
			<li class="nav-item">
				<a class="btn" href="list.nadri?place=경상도&category=${param.category }&keyword=${param.keyword }&startdate=${param.startdate }&enddate=${param.enddate }" 
					aria-current="page" style="font-weight:${place=='경상도'?'bold':''};">
				경상도
				</a>
			</li>
			<li class="nav-item">
				<a class="btn" href="list.nadri?place=제주도&category=${param.category }&keyword=${param.keyword }&startdate=${param.startdate }&enddate=${param.enddate }" 
					aria-current="page" style="font-weight:${place=='제주도'?'bold':''};">
				제주도
				</a>
			</li>
		</ul>
		<hr>
	</div>

	<div class="d-flex flex-column flex-shrink-0 p-1"
		style="width: 250px;">
		<span class="fs-4"><strong>상세검색</strong></span>
		<hr>
			<ul class="nav nav-pills flex-column mb-auto">
				<li class="mb-2">
					카테고리&nbsp;&nbsp;&nbsp;
					<select name="category">
						<option value="" ${param.category==""?'selected':'' }>모두</option>
						<option value="투어" ${param.category=="투어"?'selected':'' }>투어</option>
						<option value="입장권" ${param.category=="입장권"?'selected':'' }>입장권</option>
						<option value="액티비티" ${param.category=="액티비티"?'selected':'' }>액티비티</option>
						<option value="클래스" ${param.category=="클래스"?'selected':'' }>클래스</option>			
					</select>
				</li>
				<li class="mb-2">
					키워드&nbsp;&nbsp;&nbsp;
					<input type="text" name="keyword" style="width:100px;" value="${param.keyword }">
				</li>
				<li class="nav-item mb-2">
					일정<br>
					<input type="date" id="startdate" name="startdate" value="${param.startdate }">부터
					<input type="date" id="enddate" name="enddate" value="${param.enddate }">까지
				</li>																			
			</ul>
		<hr>
		<div class="d-grid gap-2 d-md-flex justify-content-md-end">
			<a class="btn btn-outline-secondary me-md-2" href="list.nadri">초기화</a>
			<button class="btn btn-outline-primary me-md-2" type="submit">검색</button>
		</div>
	</div>
	</form>	
	</div>
	
</div>
<script type="text/javascript">
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	
	var tommorrow = new Date();

	 if(dd<10){
	        dd='0'+dd
	    } 
	    if(mm<10){
	        mm='0'+mm
	    } 	
	today = yyyy+'-'+mm+'-'+dd;
	tommorrow =yyyy+'-'+mm+'-'+(dd+1);
	document.getElementById("startdate").setAttribute("min", today);
/* 	document.getElementById("startdate").setAttribute("value", today); */
	document.getElementById("enddate").setAttribute("min", tommorrow);	
</script>