<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="alexandria" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<alexandria:layout pageName="stories">
    <jsp:body>
        <h1>
            <c:if test="${story['new']}">New </c:if> Story
        </h1>
        <c:out value="username: ${story.author.user.username}"/>
        <form:form modelAttribute="story"
                    class="form-horizontal">
            <input type="hidden" name="id" value="${story.id}"/>
            <input type="hidden" name="version" value="${story.version}"/>
            <input type="hidden" name="authorId" value="${story.author.id}"/>
            <fmt:formatDate value="${story.updatedDate}" type="date" pattern="yyyy/MM/dd HH:mm" var="parsedUpdatedDate"/>
            <input type="hidden" name="updatedDate" value="${parsedUpdatedDate}"/>
            
            <div class="form-group has-feedback">
                <alexandria:inputField label="Title" name="title"/>
                <alexandria:textareaField label="Description" name="description"/>
                <alexandria:textareaField label="Dedication" name="dedication"/>
                <alexandria:inputField label="Cover" name="urlCover"/>
                <alexandria:checkboxField label="Is the story only for Adults?" name="isAdult"/>
<%--                 <form:checkbox label="Adult" path="isAdult"/> --%>
                <alexandria:selectEnumField options="${genres}" label="Genre" name="genre"/>
<%--                 <form:select path="genre"> --%>
<%--      				<form:options items="${genres}" /> --%>
<%-- 				</form:select> --%>
				<alexandria:selectEnumField options="${storyStatus}" label="Status" name="storyStatus"/>
                
            </div>
            
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${story['new']}">
                            <button class="btn btn-default" type="submit">Create</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update</button>
                        </c:otherwise>
                    </c:choose>
                    <button class="btn btn-default" type="reset" onclick="location.href = '/stories/list';">Cancel</button>
                </div>
            </div>
        </form:form>
        <c:if test="${!story['new']}">
        </c:if>
    </jsp:body>
</alexandria:layout>
