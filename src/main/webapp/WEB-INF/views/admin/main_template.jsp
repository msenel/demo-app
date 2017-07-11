<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Demo App</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
		<link href="<c:url value="/resources/css/bootstrap.min.css" />"	rel="stylesheet" type="text/css">
		<link href="<c:url value="/resources/css/custom.css" />" rel="stylesheet" type="text/css">
		<style type="text/css">
   			 @import url(http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css);
		</style>
	</head>

	<body ng-app="myApp" class="ng-cloak">
		
		<nav class="navbar">
			<div class="container">
				<a class="navbar-brand" href="<c:url value="/" />">Demo App</a>
				<div class="navbar-right">
					<div class="container minicart"></div>
				</div>
			</div>
		</nav>
		
		<div class="container-fluid breadcrumbBox text-center">
			<ol class="breadcrumb">
				<li><a href="<c:url value="/admin/" />">Home</a></li>
			</ol>
		</div>
		
		<div class="container text-center">
			<tiles:insertAttribute name="content" />		
		</div>

	
		<!-- JavaScript includes -->

	</body>
</html>