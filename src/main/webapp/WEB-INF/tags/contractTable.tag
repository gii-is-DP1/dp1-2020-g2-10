<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ attribute name="contractList" required="true" type="java.util.List"
              description="List of contracts to populate the table" %>
<%@ attribute name="tableId" required="true" rtexprvalue="true"
              description="HTML id attribute of the table so it can be referenced" %>
<%@ attribute name="startHidden" required="false" type="java.lang.Boolean"
              description="If true, table will start initially as hidden" %>

<c:set var="inlineStyle" value="${(startHidden) ? 'display:none;' : ''}" />
<table id="${tableId}" class="table table-striped" style="${inlineStyle}">
			<thead>
				<tr>
					<th>Offer Date</th>
					<th style="width: 40%;">Header</th>
					<th>Remuneration</th>
					<sec:authorize access="hasAnyAuthority('author')">
						<th>Company</th>
					</sec:authorize>
					<sec:authorize access="hasAnyAuthority('company')">
						<th>Author</th>
					</sec:authorize>
	<!-- 				<th style="width: 150px;">Contract Status</th> -->
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${contractList}" var="contract">
					<tr>
						<fmt:formatDate value="${contract.offerDate}" type="date" pattern="yyyy/MM/dd" var="parsedOfferDate"/>
						<td><c:out value="${parsedOfferDate}" /></td>
						<td>
							<c:out value="${contract.header}" />
							<c:if test="${contract.isExclusive}">
								&nbsp<span class="label label-warning">Exclusive</span>
							</c:if>
							<jsp:useBean id="now" class="java.util.Date"/>
							<c:if test="${now > contract.startDate}">
								&nbsp<span class="label label-default">Expired</span>
							</c:if>
							<c:if test="${contract.contractStatus == 'REJECTED'}">
								&nbsp<span class="label label-danger">Rejected</span>
							</c:if>
						</td>
						<td>
						<fmt:formatNumber type = "number" minFractionDigits="2" maxFractionDigits="2" groupingUsed="true" value = "${contract.remuneration}" />&euro;
						</td>
						<sec:authorize access="hasAnyAuthority('author')">
							<td><img src="<c:out value="${contract.company.urlLogo}" /> "  width="100" > </td>
						</sec:authorize>
						<sec:authorize access="hasAnyAuthority('company')">
							<td>
								<p>
									<a href="#" class="navbar-link"><c:out value="${contract.author.user.username}"/></a>
								</p>
									<c:out value="${contract.author.firstName}"/>&nbsp
									<c:out value="${contract.author.lastName}"/>
							</td>
						</sec:authorize> 
	                    <td><spring:url value="/contracts/{contractId}/show" var="showUrl">
								<spring:param name="contractId" value="${contract.id}"/>
	                    	</spring:url>
	                    	<a href="${fn:escapeXml(showUrl)}">Show</a>
	                    </td>
	                    
					</tr>
				</c:forEach>
			</tbody>
</table>