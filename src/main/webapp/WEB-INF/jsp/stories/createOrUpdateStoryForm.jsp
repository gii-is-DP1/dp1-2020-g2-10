<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="stories">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${story['new']}">New </c:if> Story
        </h2>
        <form:form modelAttribute="story"
                    class="form-horizontal">
            <input type="hidden" name="id" value="${story.id}"/>
            <fmt:formatDate value="${story.updatedDate}" type="date" pattern="yyyy/MM/dd HH:mm" var="parsedUpdatedDate"/>
             <input type="hidden" name="updatedDate" value="${parsedUpdatedDate}"/>
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Author</label>
                    <div class="col-sm-10">
                        <c:out value="${story.author.firstName} ${story.author.lastName}"/>
                    </div>
                </div>
                <petclinic:inputField label="Title" name="title"/>
                <petclinic:inputField label="Description" name="description"/>
                <petclinic:inputField label="Dedication" name="dedication"/>
<%--                 <petclinic:inputField label="Genre" name="genre"/> --%>
<%--                 <petclinic:inputField label="StoryStatus" name="storyStatus"/> --%>
                <form:checkbox label="Adult" path="isAdult"/>
                <form:select path="genre">
     				<form:options items="${genres}" />
				</form:select>
				<form:select path="storyStatus">
     				<form:options items="${storyStatus}" />
				</form:select>
                
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${story['new']}">
                            <button class="btn btn-default" type="submit">Add Pet</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Pet</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!story['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>
