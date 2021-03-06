<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/tags.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>나드리::통계</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://d3js.org/d3.v6.min.js"></script>
	<script src="../../resources/css/billboard.js"></script>
	<link rel="stylesheet" href="../../resources/css/billboard.css">
</head>
<style>
	html, body {
		width: 100%;
		height: 100%;
		overflow: hidden;
	}

	.container-fluid
	{
    position:fixed;
    overflow: auto;
    padding:0;
    margin:0;

    top:0;
    left:0;

    width: 100%;
    height: 100%;
	}
	.store li {
		cursor: pointer;
		font-size: 23px;
		margin-right: 20px;
		font-weight: bold;
	}
	.store span {
		color: #606060;
	}
	span.active {
		color: #7C474A;
	}
	#famousList img {
		border-radius: 3px;
		width: 280px;
		height: 200px;
	}
	#famousList {
		display: grid;
		grid: '. . . .';
	 	gap: 50px;
	 	margin: auto;
	 	text-align: center;
	 	padding: 10px 50px 30px 50px ;
	}
	#famousList div {
		padding: 0;
		width: 281px;
	}
	.border {
		background-color: white;
	}
	
	.bb svg {
    font: 12px sans-serif;
    }
</style>
<body>
<c:set var="menu" value="statistics"/>
<%@ include file="common/navbar.jsp" %>
<div class="container-fluid bg-light">
	<div class="row">
		<div class="col-2" style="position: fixed; height: 100%; min-width: 280px;">
			<%@ include file="common/navbar.jsp" %>
		</div>
		<div class="col-10" style="margin-left: 310px;">
			<div class="row border m-4">
				<div class="col" style="background-color: white;">
					<ul class="nav justify-content-end store" style="height: 50px;">
						<li class="nav-item" data-category="train">
							<span class="nav-link active">기차</span>
						</li>
						<li class="nav-item" data-category="restaurant">
							<span class="nav-link">음식점</span>
						</li>
						<li class="nav-item" data-category="attr">
							<span class="nav-link">여행지</span>
						</li>
					</ul>
				</div>
			</div>
			<div class="border m-4">
				<c:set var="today" value="<%=new java.util.Date()%>" />
				<h4 class="mt-4 ms-5">
				<strong><fmt:formatDate value="${today}" pattern="MM" />월달</strong>
				<strong class="ms-2">예약순위</strong></h4>
				<div class="mx-4" id="famousList">
					<c:forEach var="famous" items="${famousList }">
					<div class="border">
						<img alt="${famous.destination }" src="../../resources/images/train/route/${famous.image }">
						<div class="text-start ps-3 mt-3">
							<h5><strong>${famous.destination }</strong></h5>
						</div>
						<div class="text-start pb-2 px-3">
							예약 건수: ${famous.count }
						</div>
					</div>
					</c:forEach>
				</div>
			</div>
			<div class="row border mt-4 mx-4" style="background-color: white;">
				<div class="col">
					<div class="text-center" style="font-size: 20px; font-weight: bold;">
						이번달 연령별/성별 누적금액 현황
					</div>
				</div>
			</div>
			<div class="row mx-4 mt-2">
				<div class="col border p-3">
					<div class="row">
						<div class="col-6" >
							<div class="  border p-3">
								<div id="barChart_1"></div>
							</div>
						</div>
						<div class="col-6" >
							<div class=" border p-3">
								<div id="barChart_2"></div>
							</div>
						</div>
					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	$(function() {
		function addCommas(nStr) {
		    nStr += '';
		    x = nStr.split('.');
		    x1 = x[0];
		    x2 = x.length > 1 ? '.' + x[1] : '';
		    var rgx = /(\d+)(\d{3})/;
		    while (rgx.test(x1)) {
		        x1 = x1.replace(rgx, '$1' + ',' + '$2');
		    }
		    return x1 + x2;
		}	
		
		$.getJSON('/rest/admin/statistics',
			function(response) {
				console.log(response)
				let restaurantPayment = response.agePayment.map(data => data.restaurantPayment)
				let attractionPayment = response.agePayment.map(data => data.attractionPayment)
				let trainPayment = response.agePayment.map(data => data.trainPayment)
				
				let restaurantGender = response.genderPayment.map(data => data.restaurantPayment)
				let attractionGender = response.genderPayment.map(data => data.attractionPayment)
				let trainGender = response.genderPayment.map(data => data.trainPayment)
				var chart = bb.generate({
					  data: {
					    x: "x",
					    columns: [
					  ["x", "10~19", "20~29", "30~39", "40~49", "50~"],
						["음식점", ...restaurantPayment],
						["기차", ...trainPayment],
						["여행지", ...attractionPayment]
					    ],
					    type: "bar", // for ESM specify as: bar()
					    labels: {
					    	format: {
						    	기차: function(x) { return d3.format(',')(x)},
						    	음식점: function(x) { return d3.format(',')(x)},
						    	여행지: function(x) { return d3.format(',')(x)}
					    	},
					    	colors:"black"
					    },
					    colors: {
					      기차: "#83B1DF",
					      음식점: "#B393D2",
					      여행지: "#CD868B"
					    },
					    options: {
					            scale: {
					                pointLabels: {
					                    fontSize: 50
					                }
					           }
					    }
					  },
					  bar: {
					    width: {
					      ratio: 0.7
					    }
					  },
					  axis: {
					    x: {
					      type: "category",
					      tick: {
					        rotate: 0,
					        multiline: false,
					        tooltip: true
					      },
					      height: 35,
					    }
					  },
					  bindto: "#barChart_1"
					});
				
				var chart2 = bb.generate({
					  data: {
					    x: "x",
					    columns: [
					  ["x", "여성", "남성"],
						["음식점", ...restaurantGender],
						["기차", ...attractionGender],
						["여행지", ...trainGender]
					    ],
					    type: "bar", // for ESM specify as: bar()
					    labels: {
					    	format: {
						    	기차: function(x) { return d3.format(',')(x)},
						    	음식점: function(x) { return d3.format(',')(x)},
						    	여행지: function(x) { return d3.format(',')(x)}
					    	},
					    	colors:"black"
					    },
					    colors: {
					      기차: "#83B1DF",
					      음식점: "#B393D2",
					      여행지: "#CD868B"
					    }
					  },
					  bar: {
					    width: {
					      ratio: 0.7
					    }
					  },
					  axis: {
					    x: {
					      type: "category",
					      tick: {
					        rotate: 0,
					        multiline: false,
					        tooltip: true
					      },
					      height: 35,
					    }
					  },
					  bindto: "#barChart_2"
					});
			}
		)
			
		
		
		
		$(".store span").click(function() {
			$(this).parent().siblings().children().removeClass('active')
			let category = $(this).addClass('active').parent().attr('data-category')
			console.log(category)

			$.ajax({type:'GET',
				url:'/rest/admin/famous/'+category,
				contentType:'application/json',
				dataType:'json',
				success:function(response) {
					$("#famousList").empty()
					if (response.famousList.length == 0) {
						let list;
						list = '<div class="text-center" style="height: 160px; margin-left: 100%; margin-top: 100px;"><h5><strong>예약 정보가 존재하지 않습니다.</strong></h5></div>'
						$("#famousList").append(list)
					} else {
						$.each(response.famousList, function(index, data) {
							let list;
							list = '<div class="border">'
							if (category == 'restaurant') {
								list += '<img alt="' + data.destination + '" src="' + data.image +'">'
							} else if (category == 'attr') {
								list += '<img alt="' + data.destination + '" src="../../resources/images/att/' + data.image + '">'
							} else {
								list += '<img alt="' + data.destination + '" src="../../resources/images/train/route/' + data.image + '">'
							}
							list += '<div class="text-start ps-3 mt-3">'
							list += '<h5><strong>' + data.destination +'</strong></h5>'
							list += '</div>'
							list += '<div class="text-start pb-2 px-3">예약 건수: ' + data.count + '</div>'
							list += '</div>'
							
							$("#famousList").append(list)
						})
					}
				}, 
				error:function() {
					
				}
			})
			
		})
		
		
		
		
		
		
		
	})
	
</script>
</html>