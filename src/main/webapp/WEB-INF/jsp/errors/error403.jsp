<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="alexandria" tagdir="/WEB-INF/tags" %>

<% response.setStatus( 403 ); %>
<alexandria:layout pageName="error">

	<div class="center">
		<h1>Access Denied (HTTP 403)</h1>
		<img class="error-illustration" src="/resources/images/errors/httperror403-ship.jpg" />
	</div>
</alexandria:layout>