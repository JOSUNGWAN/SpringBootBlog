<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()"> <!-- isAuthenticated() : 인증이 되었는지 확인해준다. -->
	<sec:authentication property="principal" var="principal"/> <!-- 현재 사용자를 나타내는 주 개체에 엑세스 해줌 -->
</sec:authorize>


<!DOCTYPE html>
<html lang="en">
<head>
<title>Wanis Blog</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		<a class="navbar-brand" href="/">홈</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
		
		<c:choose>
			<c:when test="${empty principal }">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="/auth/loginForm">로그인</a></li> <!-- 인증없이도 사용하기위해 /auth를 써준다.-->
					<li class="nav-item"><a class="nav-link" href="/auth/joinForm">회원가입</a></li> <!-- 인증없이도 사용하기위해 /auth를 써준다. -->
				</ul>
			</c:when>
			<c:otherwise>
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="/board/saveForm">글쓰기</a></li> <!-- 로그인 후에 이용해야하기 때문에 /auth를 안쓴다. -->
					<li class="nav-item"><a class="nav-link" href="/user/updateForm">회원정보</a></li>
					<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
				</ul>
			</c:otherwise>
		</c:choose>
			
			
		</div>
	</nav>
	<br/>