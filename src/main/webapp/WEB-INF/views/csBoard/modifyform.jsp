<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>나드리::문의사항</title>
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
			<h1>Q&A 수정</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col">
			<form class="border bg-light p-3" action="modify.nadri" method="post">
				<input type="hidden" name="no" value="${csBoard.no }">
				<div class="mb-3">
					<label class="form-label">타입</label>
					<select class="form-select" name="csType">
						<option value="사이트이용" ${'사이트이용' eq param.csType ? 'selected' : ''}>사이트이용</option>
						<option value="프로모션" ${'프로모션' eq param.csType ? 'selected' : ''}>프로모션</option>
						<option value="예약/취소/환불" ${'예약/취소/환불' eq param.csType ? 'selected' : ''}>예약/취소/환불</option>
						<option value="쿠폰/포인트" ${'쿠폰/포인트' eq param.csType ? 'selected' : ''}>쿠폰/포인트</option>
						<option value="기타" ${'기타' eq param.csType ? 'selected' : ''}>기타</option>
					</select>
				</div>
				<div class="mb-3">
					<label class="form-label">제목</label>
					<input type="text" class="form-control" name="title" value="${csBoard.title }"/>
				</div>
				<div class="mb-3">
  					<label class="form-label">내용</label>
 					<textarea class="form-control" rows="10" name="content">${csBoard.content }</textarea>
				</div>
				<div class="text-end">
					<a href="detail.nadri?no=${csBoard.no }" class="btn btn-secondary">취소</a>
					<button type="submit" class="btn btn-primary">등록</button>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>