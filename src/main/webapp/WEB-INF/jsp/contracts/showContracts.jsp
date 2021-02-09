<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



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
				<th style="width: 150px;">Exclusive</th>
				<th style="width: 150px;">Contract Status</th>
			</tr>
		</thead>
		<tbody>
				<tr>
					<td><c:out value="${contract.offerDate}" />
						<jsp:useBean id="now" class="java.util.Date"/>
							<c:if test="${now > contract.startDate}">
						&nbsp<span class="label label-default">Expired</span>
						</c:if>
							</td>
					<td><c:out value="${contract.body}" /></td>
					<td><c:out value="${contract.remuneration}" /></td>
					<td><c:out value="${contract.answerDate}" /></td>
					<td><c:out value="${contract.startDate}" /></td>
					<td><c:out value="${contract.endDate}" /></td>
					<td><c:out value="${contract.isExclusive}" /></td>
					<td><c:out value="${contract.contractStatus}" /></td>

				
				</tr>
			
		</tbody>
	</table>
	<spring:url value="/contracts/{contractId}/accept" var="acceptUrl">
		<spring:param name="contractId" value="${contract.id}"/>
	</spring:url>
	<spring:url value="/contracts/{contractId}/reject" var="rejectUrl">
		<spring:param name="contractId" value="${contract.id}"/>
	</spring:url>
	<sec:authorize access="hasAnyAuthority('author')">
		<jsp:useBean id="today" class="java.util.Date"/>
		<c:if test="${today < contract.startDate and contract.contractStatus == 'PENDING'}">
		<div class="row">
			<div class="center">
				<div class="btn-group-wrap ">
					<div id="btn-group" class="btn-group mx-auto">
						<a class="btn btn-default" href="<c:out value="${acceptUrl}" />" >Accept</a>
						<a class="btn btn-danger"  href="<c:out value="${rejectUrl}" />" >Reject</a>
					</div>
				</div>
			</div>
		</div>
		</c:if>
	</sec:authorize>
		<a class="btn btn-default" href="/contracts/list" >Return</a>          
</petclinic:layout>