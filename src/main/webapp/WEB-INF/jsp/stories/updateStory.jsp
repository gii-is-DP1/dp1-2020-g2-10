<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<petclinic:layout pageName="story">
    <h2>
        <c:if test="${story['new']}">Edit </c:if> Story
    </h2>
    <form:form modelAttribute="story" class="form-horizontal" id="add-story-form">
        <div class="form-group has-feedback">
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
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Story</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
