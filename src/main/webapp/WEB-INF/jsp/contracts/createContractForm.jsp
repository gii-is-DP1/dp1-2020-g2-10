<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="alexandria" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<alexandria:layout pageName="contracts">
    <jsp:body>
        <h2>
            <c:if test="${contract['new']}">New </c:if> Contract
        </h2>
        <c:out value="username: ${contract.author.user.username}"/>
        <form:form modelAttribute="contract"
                    class="form-horizontal">
            <input type="hidden" name="id" value="${contract.id}"/>
            <input type="hidden" name="authorId" value="${contract.author.id}"/>
            
            <div class="form-group has-feedback">
            
                <alexandria:inputField label="Header" name="header"/>
                <alexandria:textareaField label="Text" name="text"/>
                <alexandria:textareaField label="Remuneration" name="remuneration"/>
                <fmt:formatDate label= "Offer Date" name= "offerDate" value="${contract.offerDate}" type="date" pattern="yyyy/MM/dd HH:mm" var="parsedOfferDate"/>
                <fmt:formatDate label= "Answer Date" name= "answerDate" value="${contract.answerDate}" type="date" pattern="yyyy/MM/dd HH:mm" var="parsedAnswerDate"/>
                <fmt:formatDate label= "Start Date" name= "startDate" value="${contract.startDate}" type="date" pattern="yyyy/MM/dd HH:mm" var="parsedStartDate"/>
                <fmt:formatDate label= "End Date" name= "endDate" value="${contract.endDate}" type="date" pattern="yyyy/MM/dd HH:mm" var="parsedEndDate"/>
                
                
                <alexandria:selectEnumField options="${contractStatus}" label="Status" name="contractStatus"/>
                <alexandria:checkboxField label="Exlusivity" name="isExclusive">
                 <option value="Yes">Yes</option>
                 <option value="No">No</option>
                </alexandria:checkboxField>
            </div>
            
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${contract['new']}">
                            <button class="btn btn-default" type="submit">Create</button>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!contract['new']}">
        </c:if>
    </jsp:body>
</alexandria:layout>
