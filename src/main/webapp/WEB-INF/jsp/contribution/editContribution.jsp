<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="alexandria" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>

<alexandria:layout pageName="contribution">
    <jsp:body>         
            
            
    	<h2>Panel for creating a contribution for ${story.id}</h2>
        <br></br>
                <form:form modelAttribute="contribution" class="form-horizontal">
                
                <input type="hidden" name="storyId" value="${contribution.story.id}"/>
            	

            <div class="form-group has-feedback">
            	<alexandria:inputField label="Author ID" name="author.id"/>
                <alexandria:selectEnumField options="${contributionType}" label="Contribution" name="contributionType"/>
                 
               
           	</div>
				

<!-- Buttons -->
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">                    	
                	<button class="btn btn-default" type="submit">Create contribution</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</alexandria:layout>