<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="stories">
    <h2>Authors</h2>

    <table id="storiesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Dedication</th>
            <th>Genre</th>
            <th>storyStatus </th>
            <th>isAdult </th>
            <th>updatedDate </th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${stories.storyList}" var="story">
            <tr>
                <td>
                    <c:out value="${story.title} "/>
                </td>
                <td>
                    <c:out value="${story.description} "/>
                 
                </td>
                <td>
                    <c:out value="${story.dedication} "/>
                </td>
                <td>
                    <c:out value="${story.genre} "/>
                </td>
                <td>
                    <c:out value="${story.storyStatus} "/>

                </td>
                <td>
                    <c:out value="${story.isAdult} "/>

                </td>
                <td>
                    <c:out value="${story.updatedDate} "/>
                 
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/stories.xml" htmlEscape="true" />">View as XML</a>
            </td>            
        </tr>
    </table>
</petclinic:layout>
