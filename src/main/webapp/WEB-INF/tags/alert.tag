<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="message"
               required="false"%>
<%@ attribute name="type"
               required="false"%>
<c:set var="type" value="${not empty type ? type : 'info'}" />             

<c:set var="boldMessage" value="Info: "/>
<%
	String auxBoldMessage;
   switch(type) {
      case "info":
    	 auxBoldMessage = "Info: ";
         break;
      case "success":
    	 auxBoldMessage = "Success! ";
         break;
      case "warning":
    	 auxBoldMessage = "Warning: ";
         break;
      case "danger":
    	  auxBoldMessage = "Error! ";
         break;
      default:
    	  type = "info";
    	  auxBoldMessage = "Info: ";
   }
   jspContext.setAttribute("boldMessage", auxBoldMessage);
%>
              
<c:if test="${not empty message}">
	
	<div class="alert alert-${not empty type ? type : 'info'} alert-dismissible" role="alert">
  		 <strong><c:out value="${boldMessage}" /></strong><c:out value="${message}"></c:out>
   		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    		<span aria-hidden="true">&times;</span>
  		</button> 
	</div>
</c:if>