<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="alexandria" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<alexandria:layout pageName="contracts">  

<jsp:attribute name="customScript">
            <script>
                $(function () {
                    $("#startDate").datepicker({ dateFormat: 'yy/mm/dd' });
                    $("#endDate").datepicker({ dateFormat: 'yy/mm/dd' });

                });
            </script>
</jsp:attribute>
        
    <jsp:body>
        <h2>
            <c:if test="${contract['new']}">New </c:if> Contract
        </h2>
        <c:out value="username: ${contract.company.user.username}"/>
         <c:out value="id: ${contract.company.id}"/>
        
        <form:form modelAttribute="contract"
                    class="form-horizontal">
            <input type="hidden" name="id" value="${contract.id}"/>
            <input type="hidden" name="company.id" value="${contract.company.id}"/>
            <fmt:formatDate value="${contract.offerDate}" type="date" pattern="yyyy/MM/dd HH:mm" var="parsedOfferDate"/>
            <input type="hidden"  name= "offerDate" value="${parsedOfferDate}" />
           
           
            <div class="form-group has-feedback">
            	
				<alexandria:inputField label="Author ID" name="author.id"/>
         
                <alexandria:inputField label="Header" name="header"/>
                <alexandria:textareaField label="Body" name="body"/>
                <alexandria:textareaField label="Remuneration" name="remuneration"/>
                <alexandria:selectEnumField options="${contractStatus}" label="Status" name="contractStatus"/>
                <alexandria:checkboxField label="Exlusivity" name="isExclusive"/>
                
           		<alexandria:inputField label= "Fecha de inicio" name= "startDate" />
           		<alexandria:inputField label="Fecha fin" name= "endDate"  />
           		
           		 <c:if test="${idAuthor}">
					<h3 style="color: DarkRed;">&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;You must put the author's id.</h3>
				</c:if>
           		 
           		  <c:if test="${endDateIsBeforeOrEqualsStartDate}">
					<h3 style="color: DarkRed;">&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;The endDate must be distinct and after startDate.</h3>
				</c:if> 
				
			
            	</div>
            
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${contract['new']}">
                            <button class="btn btn-default" type="submit">Create</button>
                        </c:when>
                    </c:choose>
						<a class="btn btn-default" href="/contracts/list" >Return</a>          
                    
                </div>
            </div>
        </form:form>
        <c:if test="${!contract['new']}">
        </c:if>
    </jsp:body>
</alexandria:layout>
