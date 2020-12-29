<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="options" required="true" rtexprvalue="true" type="java.lang.Object"
              description="List of options" %>
<%@ attribute name="size" required="false" rtexprvalue="true"
              description="Size of Select" %>
<%@ attribute name="placeholder" required="optional" rtexprvalue="true"
              description="Text for the empty option placeholder" %>

<spring:bind path="${name}">
    <c:set var="cssGroup" value="form-group ${status.error ? 'error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <c:if test="${empty placeholder}">
    	<c:set var="placeholder" value="Select an option"/>
    </c:if>
    <c:if test="${empty size}">
    	<c:set var="size" value=""/>
    </c:if>
    <div class="${cssGroup}">
        <label class="col-sm-2 control-label">${label}</label>

        <div class="col-sm-10">
            <form:select multiple="false" path="${name}">
            	<option disabled selected><c:out value="${placeholder}"></c:out></option>
            	<form:options items="${options}"/>
            </form:select>
            <c:if test="${valid}">
                <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
            </c:if>
            <c:if test="${status.error}">
                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                <span class="help-inline">${status.errorMessage}</span>
            </c:if>
        </div>
    </div>
</spring:bind>
