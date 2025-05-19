<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Welcome - Prime Reel</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

  <!-- Page-specific CSS -->
  <c:if test="${not empty pageCss}">
    <link rel="stylesheet" href="${pageContext.request.contextPath}${pageCss}"/>
  </c:if>
</head>
<body>
<%-- Include the common header --%>
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>
<main>
  <%-- Dynamic content based on path --%>
  <jsp:include page="${contentPage}"/>
</main>
</body>
</html>
