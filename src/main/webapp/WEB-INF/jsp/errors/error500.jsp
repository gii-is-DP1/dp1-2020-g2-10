<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="alexandria" tagdir="/WEB-INF/tags" %>

<% response.setStatus( 500 ); %>
<alexandria:layout pageName="error">
<div class="center">
<h1>Internal Server Error (HTTP 500)</h1>
	<img class="error-illustration" src="/resources/images/errors/httperror500-ship.jpg" />
</div>
</alexandria:layout>