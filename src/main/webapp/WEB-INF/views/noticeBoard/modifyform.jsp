<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>나드리::공지사항</title>
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
			<h1>공지사항 수정</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col">
			<form class="border bg-light p-3" action="modify.nadri" method="post">
				<input type="hidden" name="no" value="${noticeBoard.no }" />
				<div class="mb-3">
					<label class="form-label">제목</label>
					<input type="text" class="form-control" name="title" value="${noticeBoard.title }" />
				</div>
				<div class="mb-3">
  					<label class="form-label">내용</label>
 					<textarea class="form-control" rows="10" name="content">${noticeBoard.content }</textarea>
				</div>
				<div class="text-end">
					<a href="list.nadri" class="btn btn-secondary">취소</a>
					<button type="submit" class="btn btn-primary">등록</button>
				</div>
			</form>
		</div>
	</div>

</div>
</body>
</html>