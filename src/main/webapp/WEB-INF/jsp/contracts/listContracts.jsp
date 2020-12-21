<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>




<petclinic:layout pageName="contracts">
	<h2>Contracts</h2>

	<table id="contractsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Offer Date</th>
				<th style="width: 200px;">Header</th>
				<th style="width: 150px;">Remuneration</th>
<!-- 				<th style="width: 150px;">Contract Status</th> -->
				<th style="width: 200px;">Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${contracts}" var="contracts">
				<tr>
					<td><c:out value="${contracts.offerDate}" /></td>
					<td><c:out value="${contracts.header}" /></td>
					<td><c:out value="${contracts.remuneration}" /></td>
<%-- 					<td><c:out value="${contracts.contractStatus}" /></td> --%>
					<td> 
                        <spring:url value="/companies/{companyId}/contracts/{contractId}" var="showUrl">
                        	<spring:param name="companyId" value="${companyId}"/>
							<spring:param name="contractId" value="${contracts.id}"/>
                    	</spring:url>
                    	<a href="${fn:escapeXml(showUrl)}">Show</a>
                    	</td>
                    
					

				</tr>
			</c:forEach>
		</tbody>
	</table>        
</petclinic:layout>