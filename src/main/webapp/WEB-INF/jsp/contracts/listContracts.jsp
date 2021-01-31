<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="alexandria" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<alexandria:layout pageName="contracts">

	<jsp:attribute name="customScript">
		<script type ="text/javascript">
			function showPendingContracts() {
				document.getElementById('pendingContracts').style.display="";
				document.getElementById('acceptedContracts').style.display="none";
				document.getElementById('allContracts').style.display="none";
				/*Grupo de botones*/
				document.getElementById('pendingContractsCheck').classList.add("selected");
				document.getElementById('acceptedContractsCheck').classList.remove("selected");
				document.getElementById('allContractsCheck').classList.remove("selected");
				document.getElementById('pendingContractsCheck').disabled = true;
				document.getElementById('acceptedContractsCheck').disabled = false;
				document.getElementById('allContractsCheck').disabled = false;
			}
			function showAcceptedContracts() {
				document.getElementById('pendingContracts').style.display="none";
				document.getElementById('acceptedContracts').style.display="";
				document.getElementById('allContracts').style.display="none";
				/*Grupo de botones*/
				document.getElementById('pendingContractsCheck').classList.remove("selected");
				document.getElementById('acceptedContractsCheck').classList.add("selected");
				document.getElementById('allContractsCheck').classList.remove("selected");
				document.getElementById('pendingContractsCheck').disabled = false;
				document.getElementById('acceptedContractsCheck').disabled = true;
				document.getElementById('allContractsCheck').disabled = false;
			}
			function showAllContracts() {
				document.getElementById('pendingContracts').style.display="none";
				document.getElementById('acceptedContracts').style.display="none";
				document.getElementById('allContracts').style.display="";
				/*Grupo de botones*/
				document.getElementById('pendingContractsCheck').classList.remove("selected");
				document.getElementById('acceptedContractsCheck').classList.remove("selected");
				document.getElementById('allContractsCheck').classList.add("selected");
				document.getElementById('pendingContractsCheck').disabled = false;
				document.getElementById('acceptedContractsCheck').disabled = false;
				document.getElementById('allContractsCheck').disabled = true;
			}
		</script>
	</jsp:attribute>

	<jsp:body>
		<h2>Contracts</h2>
		<div class="row">
			<div class="center">
				<div class="btn-group-wrap ">
					<div id="btn-group" class="btn-group mx-auto">
						<button type="button" id="pendingContractsCheck" class="selected" disabled onclick="showPendingContracts();">Pending</button>
						<button type="button" id="acceptedContractsCheck" onclick="showAcceptedContracts();">Accepted</button>
						<button type="button" id="allContractsCheck" onclick="showAllContracts();">All</button>
					</div>
				</div>
			</div>
		</div>
		<br/>
	
		<alexandria:contractTable contractList="${pendingContracts}" tableId="pendingContracts" />
		
		<alexandria:contractTable contractList="${acceptedContracts}" tableId="acceptedContracts" startHidden="true"/>
		
		<alexandria:contractTable contractList="${allContracts}" tableId="allContracts" startHidden="true"/>
		
		<a class="btn btn-default" href="/" >Return</a>
		<a class="btn btn-default" href="/contracts/new" >Create</a>          
		
	</jsp:body>
	
</alexandria:layout>