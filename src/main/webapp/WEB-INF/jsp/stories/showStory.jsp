<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="stories">
	
	<div class="row">
	  <div class="col-sm-4">
		<c:if test="${not empty story.urlCover}">
			<img src="<c:out value="${story.urlCover}"/>" alt="<c:out value="${story.urlCover}"/>" class="img-responsive center-block" />
		</c:if>
	  </div>
	  <div class="col-sm-8">
	  <div class="row row-no-padding">
	  <div class="col-sm-8">
		<h1><c:out value="${story.title}"/>&nbsp
		
		  	<c:if test="${story.isAdult}">
				&nbsp<span class="label label-danger">+18</span>
			</c:if>

		  	<c:if test="${story.storyStatus eq 'DRAFT'}">
				&nbsp<span class="label label-info">Draft</span>
			</c:if>&nbsp<small>(<c:out value="${story.genre}" />)</small>
		</h1>
	 	 </div>
	 	 <div class="col-sm-4 pull-right">
	 		 <spring:url value="/stories/{storyId}/reviews/new" var="reviewStoryUrl">
             	<spring:param name="storyId" value="${story.id}"/>
             </spring:url>
             <a href="${fn:escapeXml(reviewStoryUrl)}" class="btn btn-default">Review story</a>
             <!--
             <button onclick="${fn:escapeXml(reviewStoryUrl)}">Review story</button>
             -->
	  </div>
	  </div>
	  
	  
	  <section>
	  	<h3>Description</h3>
	  	<p>${story.description}</p>
	  </section>
	  
	  <section>
		  <div class="row spaced">
		  	<div class="col-xs-4"><label>Author</label></div>
		  	<div class="col-xs-4"><p class="text-left"><c:out value="${story.author.firstName}"/>&nbsp<c:out value="${story.author.lastName}"/></p></div>
		  </div>
		  <div class="row">
		  <div class="col-xs-12">
		  <fmt:formatDate value="${story.updatedDate}" type="date" pattern="yyyy/MM/dd HH:mm" var="parsedUpdatedDate"/>
		  <p>Last update on <c:out value="${parsedUpdatedDate}"/></p></div>
		  </div>
	  </section>
	  </div>
	</div>
	
	<div class="row spaced">
		<div class="col-sm-12">
		<section>
		  	<h3>Dedication</h3>
		  	<p>${story.dedication}</p>
	  </section>
	<div class="row spaced">
				<spring:url value="/stories/{storyId}/chapters/{chapterId}" var="showChaptersUrl">
                        	<spring:param name="storyId" value="${story.id}"/>
							<spring:param name="chapterId" value="${stories.chapters.id}"/>
                    	</spring:url>
                    	<a href="${fn:escapeXml(showChaptersUrl)}">  Show chapters</a>
                 		</div>
       <button class="btn btn-default" type="reset" onclick="location.href = '/stories/list';">Return</button>
     
     
	</div>      

</petclinic:layout>