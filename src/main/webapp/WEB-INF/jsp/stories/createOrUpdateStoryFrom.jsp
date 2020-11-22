<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

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
                <petclinic:inputField label="Genre" name="genre"/>
                <petclinic:inputField label="StoryStatus" name="storyStatus"/>
                <petclinic:inputField label="Adult" name="isAdult"/>
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
