<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">

    <spring:url value="/resources/images/pets.png" var="petsImage"/>
    <div class="row">
	     <div class="col-md-12">
            <spring:url value="/resources/images/alexandria-library-fire-2.jpeg" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
    </div>

    <h2>Something happened...</h2>

    <p>${exception.message}</p>

</petclinic:layout>
