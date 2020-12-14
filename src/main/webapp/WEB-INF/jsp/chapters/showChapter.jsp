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
	<div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                    	<c:when test="${buttonCreate}">
                            <button class="btn btn-default" type="submit">Create Report</button>
                        </c:when>
                       
                        </c:choose>
                        
                </div>
            </div>     
	<!-- 	Con la variable del controlador ya puedo hacer bien el return -->
   <!-- <a class="btn btn-default" href="/stories/${storyId}/chapters">Return</a> -->
</petclinic:layout>