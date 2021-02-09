<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of corresponding property in bean object"%>
<%@ attribute name="label" required="true" rtexprvalue="true"
	description="Label appears in red color if input is considered as invalid after submission"%>

 
<spring:bind path="${name}">
<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
     <div class="${cssGroup}">
	<label class="col-sm-2 control-label">${label}</label>
		<div class="stars col-sm-2">
				<form:radiobutton path="${name}" class="star star-5" id="star-5" name="star" value="5" /> <label class="star star-5" for="star-5"></label> 
				<form:radiobutton path="${name}" class="star star-4" id="star-4" name="star" value="4" /> <label class="star star-4" for="star-4"></label> 
				<form:radiobutton path="${name}" class="star star-3" id="star-3" name="star" value="3" /> <label class="star star-3" for="star-3"></label> 
				<form:radiobutton path="${name}" class="star star-2" id="star-2" name="star" value="2" /> <label class="star star-2" for="star-2"></label> 
				<form:radiobutton path="${name}" class="star star-1" id="star-1" name="star" value="1" /> <label class="star star-1" for="star-1"></label>
		<c:if test="${valid}">
                <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
        </c:if>
        <c:if test="${status.error}">
                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                <span class="help-inline">${status.errorMessage}</span>
        </c:if>
		</div>
	<div class="col-sm-8">
    </div>
	</div>
</spring:bind>