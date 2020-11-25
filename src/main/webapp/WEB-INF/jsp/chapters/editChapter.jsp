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
            	<petclinic:inputField label="Index" name="index"/>
                <petclinic:inputField label="Title" name="title"/>
                <petclinic:inputField label="Body" name="body"/>
<%--                 <petclinic:inputField label="Publish" name="isPublished"/> --%>
                <form:radiobutton path="chapter.isPublished" value="true"/>
                <form:radiobutton path="chapter.isPublished" value="false"/>
                
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
								 <a class="btn btn-default" href="/authors/${authorId}/stories/${storyId}/chapters" >Return</a>
                        </c:otherwise>
                        </c:choose>
                        
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>