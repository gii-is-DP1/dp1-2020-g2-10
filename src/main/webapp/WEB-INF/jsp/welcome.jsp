<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <h1><fmt:message key="welcome"/></h1>
    <div class="row">

    </div>
    <div class="row">
        <div class="col-md-8">
            <spring:url value="/resources/images/alexandria-library.jpg" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
        <div class="col-md-4">
        	<p>
	            <h2> Project ${title}</h2>
			    <p><h3> Group ${group}</h3></p>
			    <p><ul>
			    <c:forEach items="${persons}" var="person">
			    	<li> ${person.firstName} ${person.lastName}</li>
			    </c:forEach>
			    </ul>
		    </p>
		    <spring:url value="/resources/images/logouni.png" htmlEscape="true" var="logouni"/>
            <img class="img-responsive" src="${logouni}"/>
        </div>
        
    </div>
</petclinic:layout>
