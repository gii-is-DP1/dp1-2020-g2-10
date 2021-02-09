<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>




<petclinic:layout pageName="chapters">
	<h2>Chapters</h2>

	<table id="chaptersTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Index</th>
				<th style="width: 200px;">Title</th>
				<th style="width: 200px;">Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${chapters}" var="chapters">
				<tr>
					<td><c:out value="${chapters.index}" /></td>
					<td><c:out value="${chapters.title}" /></td>
					<td> 
                        <spring:url value="/stories/{storyId}/chapters/{chapterId}" var="showUrl">
                        	<spring:param name="storyId" value="${chapters.story.id}"/>
							<spring:param name="chapterId" value="${chapters.id}"/>
                    	</spring:url>
                    	<a href="${fn:escapeXml(showUrl)}">Show</a>
                    	</td>
                    
					

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<sec:authorize access="hasAnyAuthority('author')">   
	<a class="btn btn-default" href= "/stories/${storyId}/chapters/new">Create Chapter</a>   
	</sec:authorize> 
 	<a class="btn btn-default" href="/stories/${storyId}/show" >Return</a>  
	
</petclinic:layout>