<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<<petclinic:layout pageName="chapters">

	<jsp:body>
        <h2>New Chapter</h2>
        <form:form modelAttribute="chapter" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Title"
					name="title" />
                <petclinic:inputField label="Body of the chapter"
					name="body" />
				<input type="checkbox" name="">
            </div>
            
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    	<c:when test="${buttonCreate}">
                            <button class="btn btn-default" type="submit">Create Chapter</button>
                            <a class="btn btn-default" href="/chapters" >Return</a>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Chapter</button>
								 <a class="btn btn-default" href="/chapters" >Return</a>
								
                        </c:otherwise>
                        </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>
    

</petclinic:layout>
