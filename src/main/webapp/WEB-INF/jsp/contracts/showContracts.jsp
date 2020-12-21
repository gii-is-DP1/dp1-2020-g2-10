<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<petclinic:layout pageName="contract">
	<h2>Contract: ${contract.header}</h2>

	<table id="contractTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Offer Date</th>
				<th style="width: 200px;">Text</th>
				<th style="width: 150px;">Remuneration</th>
				<th style="width: 150px;">Answer Date</th>
				<th style="width: 150px;">Start Date</th>
				<th style="width: 150px;">End Date</th>
<!-- 				<th style="width: 150px;">Exclusive</th> -->
<!-- 				<th style="width: 150px;">Contract Status</th> -->
				

			</tr>
		</thead>
		<tbody>
				<tr>
					<td><c:out value="${contract.offerDate}" /></td>
					<td><c:out value="${contract.text}" /></td>
					<td><c:out value="${contract.remuneration}" /></td>
					<td><c:out value="${contract.answerDate}" /></td>
					<td><c:out value="${contract.startDate}" /></td>
					<td><c:out value="${contract.endDate}" /></td>
<%-- 					<td><c:out value="${contract.isExclusive}" /></td> --%>
<%-- 					<td><c:out value="${contract.contractStatus}" /></td> --%>

				
				</tr>
			
		</tbody>
	</table>   
</petclinic:layout>