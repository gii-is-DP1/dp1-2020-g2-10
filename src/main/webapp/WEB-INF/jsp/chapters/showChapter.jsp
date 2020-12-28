<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<petclinic:layout pageName="chapter">
	<h2>Chapter ${chapter.index}: ${chapter.title}</h2>

	<table id="chapterTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;"></th>
				

			</tr>
		</thead>
		<tbody>
				<tr>
					<td><c:out value="${chapter.body}" /></td>
					
					
				</tr>
			
		</tbody>
	</table>   
	
                        
                 

	<a class="btn btn-default" href= "/stories/${chapter.story.id}/chapters/${chapter.id}/edit">Edit</a>
	<a class="btn btn-default" href= "/stories/${chapter.story.id}/chapters/${chapter.id}/delete">Delete</a>  
   <a class="btn btn-default" href="/stories/${chapter.story.id}/chapters/${chapter.id}/reports/new">Report</a>
   <a class="btn btn-default" href="/stories/${chapter.story.id}/chapters/" >Return</a>
</petclinic:layout>