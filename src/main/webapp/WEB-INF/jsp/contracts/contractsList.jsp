<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="contracts">
    <h2>Contracts</h2>


	<table id="contractsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Date</th>
				<th style="width: 150px;">Header</th>
				<th style="width: 150px;">Remuneration</th>
				<th style="width: 150px;">Start date</th>
				<th style="width: 150px;">End date</th>
				<th style="width: 150px;">Company</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${contracts}" var="contract">
				<tr>
					<td><c:out value="${contract.offerDate}" /></td>
					<td><c:out value="${contract.header}" /></td>
					<td><c:out value="${contract.remuneration}" />€</td>
					<td><c:out value="${contract.startDate}" /></td>
					<td><c:out value="${contract.endDate}" /></td>
					<td><img src="<c:out value="${contract.company.urlLogo}" /> "  width="200" > </td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>        
</petclinic:layout>