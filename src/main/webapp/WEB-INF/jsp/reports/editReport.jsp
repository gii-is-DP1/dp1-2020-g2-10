<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<petclinic:layout pageName="reports">
    <jsp:body>         
            
            
    	<h2>Panel for editing your report</h2>
        <br></br>
                <form:form modelAttribute="report" class="form-horizontal">
			<input type="hidden" name="reportStatus" value="${report.reportStatus}"/>
            <input type="hidden"  name= "date" value="${report.date}" />
            <div class="form-group has-feedback">
          
          <c:if test="${errorReportType}">
					<h3 style="color: DarkRed;">&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;You must indicate type of report.</h3>
				</c:if>
             &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<label>Report type</label>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Hateful content&emsp;<form:radiobutton path="reportType" value="HATEFUL_CONTENT"/>
                &emsp;&emsp;&emsp;Copyright infringement&emsp;<form:radiobutton path="reportType" value="COPYRIGHT_INFRINGEMENT"/>
                &emsp;&emsp;&emsp;Wrong tag&emsp;<form:radiobutton path="reportType" value="WRONG_TAG"/>
                &emsp;&emsp;&emsp;Adult content&emsp;<form:radiobutton path="reportType" value="ADULT_CONTENT"/>
                &emsp;&emsp;&emsp;Other&emsp;<form:radiobutton path="reportType" value="OTHER"/>
            	
                
                <petclinic:inputField label="Text" name="text"/>
				
				<%--                &emsp sirve para implementar espacios --%>
				
				
                
           	</div>
				

<!-- Buttons -->
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                    	<c:when test="${buttonCreate}">
                            <button class="btn btn-default" type="submit">Create Report</button>
                            <a class="btn btn-default" href="/" >Return</a>
                        </c:when>
                        
                        </c:choose>
                        
                </div>
            </div>
            
        </form:form>
    </jsp:body>

</petclinic:layout>