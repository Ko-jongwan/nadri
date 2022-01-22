<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/tags.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div class="container">
	

	<div class="row mb-3">
		<div class="col">
			<img alt="${restaurant.picture }" src="">
		</div>
	</div>
	<div class="row mb-3">
		<div class="col">
			<h3>${restaurant.name }</h3>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col">
			<p>${restaurant.content }</p>
		</div>
	</div>

</div>
</body>
</html>