<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="stories">
    
	<h2>Story ${story.title}: ${story.genre}</h2>

	<table id="chapterTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;"></th>
				

			</tr>
		</thead>
		<tbody>
				<tr>
				<td><c:out value="${story.author.firstName}" /></td>
				<td><c:out value="${story.description}" /></td>
				<td><c:out value="${story.storyStatus}" /></td>
					
					
				</tr>
			
		</tbody>
	</table>        

</petclinic:layout>