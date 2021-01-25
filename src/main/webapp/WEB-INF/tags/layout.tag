<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageName" required="true" %>
<%@ attribute name="customScript" required="false" fragment="true"%>

<!doctype html>
<html>
<petclinic:htmlHeader/>

<body>

<petclinic:bodyHeader menuName="${pageName}"/>

<div class="container-fluid">
    <div class="container xd-container">
    <petclinic:alert message="${message}" type="${messageType}"/>
        <jsp:doBody/>

        <petclinic:pivotal/>
    </div>
</div>
<petclinic:footer/>
<jsp:invoke fragment="customScript" />
</body>

</html>
