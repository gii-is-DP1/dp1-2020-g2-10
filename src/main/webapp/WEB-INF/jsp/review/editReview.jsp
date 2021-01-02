<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
<%--Para el rating de review  --%>
<link href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'>
<link href='<c:url value="/resources/css/rating.css" />' rel="stylesheet">
<link href='/css/rating2.css' rel="stylesheet">
<%--Scripts rating 1 y 2 --%>
<script src="<c:url value="/resources/js/rating.js" />"></script>
<script src="/js/rating2.js"></script> 

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="reviews">
    <jsp:body>         
            
            
    	<h2>Panel for creating a review for ${story.id}</h2>
        <br></br>
                <form:form modelAttribute="review" class="form-horizontal">
                
                <input type="hidden" name="storyId" value="${review.story.id}"/>
            	<input type="hidden" name="authorId" value="${review.author.id}"/>
          	 	<fmt:formatDate value="${review.publicationDate}" type="date" pattern="yyyy/MM/dd HH:mm" var="parsedPublicationDate"/>
            		<input type="hidden" name="publicationDate" value="${parsedPublicationDate}"/>

            <div class="form-group has-feedback">
            	<petclinic:inputField label="Title" name="title"/>
            	
                <petclinic:inputField label="Text" name="text"/>
                
                <!--<petclinic:rating label="Rating" name="rating"/> -->
                
                <petclinic:inputField label="Rating" name="rating"/>
                
           	</div>
				

<!-- Buttons -->
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">                    	
                	<button class="btn btn-default" type="submit">Create review</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>