<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="alexandria" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<alexandria:layout pageName="stories">
    <h1>Stories</h1>
    
    <sec:authorize access="hasAnyAuthority('author')">
    <div class="row spaced">
    	<div class="col-md-12">
    		<button class="btn btn-default" onclick="location.href = '/stories/new';">Create</button>
    	</div>
    </div>
    </sec:authorize>
    
	<table id="storiesTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Cover</th>
				<th style="width: 200px;">Title</th>
				<th>Genre</th>
				<th>Last update</th>
				<th>Author</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${stories}" var="story">
				<tr>
					<td>
						<c:if test="${not empty story.urlCover}"> 
							<img src="<c:out value="${story.urlCover}"/>" alt="<c:out value="${story.urlCover}"/>"  width="200" />
						</c:if>
					</td>
					<td><c:out value="${story.title}" />
						<c:if test="${story.isAdult}">
							&nbsp<span class="label label-danger">+18</span>
						</c:if>
						<c:if test="${story.storyStatus eq 'DRAFT'}">
							&nbsp<span class="label label-info">Draft</span>
						</c:if>
					</td>
					<td><c:out value="${story.genre}" /></td>
					<fmt:formatDate value="${story.updatedDate}" type="date" pattern="yyyy/MM/dd HH:mm" var="parsedUpdatedDate"/>
					<td><c:out value="${parsedUpdatedDate}" /></td>
					<td>
						<p>
							<c:out value="${story.author.firstName}"/>&nbsp<c:out value="${story.author.lastName}"/>
						</p>
					</td>			
					<td><a href="/stories/<c:out value="${story.id}" />/show">Show</a>&nbsp
					<sec:authorize access="hasAnyAuthority('author')">
						<c:if test="${not empty principal 
						and principal.username eq story.author.user.username 
						and story.storyStatus eq 'DRAFT'}">
							<spring:url value="/stories/{storyId}/delete" var="deleteUrl">
								<spring:param name="storyId" value="${story.id}"/>
							</spring:url>
							<spring:url value="/stories/{storyId}/edit" var="editUrl">
								<spring:param name="storyId" value="${story.id}"/>
							</spring:url>
							<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit</a>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger">Delete</a>
						</c:if>
					</sec:authorize>
					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>        
</alexandria:layout>