<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<petclinic:layout pageName="chapters">
    <jsp:body>         
            
            
    	<h2>Panel for editing your chapter</h2>
        <br></br>
         <form:form modelAttribute="chapter" class="form-horizontal">

            <div class="form-group has-feedback">
            	<input type="hidden" name="version" value="${chapter.version}"/>
            	<petclinic:inputField label="Index" name="index"/>
                <petclinic:inputField label="Title" name="title"/>
                <petclinic:inputField label="Body" name="body"/>
                
                <c:if test="${errorNullPublish}">
					<h3 style="color: DarkRed;">&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;You must indicate state of this story.</h3>
				</c:if>
				<c:if test="${errorPublished}">
					<h3 style="color: DarkRed;">&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;You cannot publish your chapter because your story is not.</h3>
				</c:if>
				
				
				<%--                &emsp sirve para implementar espacios --%>
				
				&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Published&emsp;<form:radiobutton path="isPublished" value="true"/>
                &emsp;&emsp;&emsp;Draft&emsp;<form:radiobutton path="isPublished" value="false"/>
                
           	</div>
				

<!-- Buttons -->
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                    	<c:when test="${buttonCreate}"> <%-- Comprobamos si la variable buttonCreate esta activada o no --%>
                            <button class="btn btn-default" type="submit">Create Chapter</button>
                            <a class="btn btn-default" href="/authors/${authorId}" >Return</a>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Chapter</button>
<!--                             Aún no implementada la vista de return -->
 								 <a class="btn btn-default" href="/"<%--authors/${authorId}/stories/${storyId}/chapters"--%> >Return</a> 
                        </c:otherwise>
                        </c:choose>
                        
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>