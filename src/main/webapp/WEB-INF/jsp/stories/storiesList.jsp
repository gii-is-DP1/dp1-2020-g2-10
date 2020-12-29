<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="stories">
    <h2>Stories</h2>


	<table id="chaptersTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Title</th>
				<th style="width: 150px;">Description</th>
				<th style="width: 150px;">Dedication</th>
				<th style="width: 150px;">Genre</th>
				<th style="width: 150px;">storyStatus</th>
				<th style="width: 150px;">isAdult</th>
				<th style="width: 150px;">updatedDate</th>
				<th style="width: 150px;">author</th>
				<th style="width: 150px;">urlCover</th>
				<th style="width: 150px;">moderator</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${stories}" var="story">
				<tr>
					<td><c:out value="${story.title}" /></td>
					<td><c:out value="${story.description}" /></td>
					<td><c:out value="${story.dedication}" /></td>
					<td><c:out value="${story.genre}" /></td>
					<td><c:out value="${story.storyStatus}" /></td>
					<td><c:out value="${story.isAdult}" /></td>
					<td><c:out value="${story.updatedDate}" /></td>
					<td><c:out value="${story.author.firstName}" /></td>
					<td><c:out value="${story.urlCover}" /> 
					<img src="<c:out value="${story.urlCover}" /> "  width="200" > </td>
					<td><c:out value="${story.moderator.lastName}" /></td>
					
					
	
				</tr>
			</c:forEach>
		</tbody>
	</table>        
</petclinic:layout>