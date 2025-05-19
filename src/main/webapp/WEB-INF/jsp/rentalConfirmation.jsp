<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rental Confirmation</title>
</head>
<body>
<c:choose>
    <!-- If there's an error message, show it -->
    <c:when test="${not empty error}">
        <h1>Error</h1>
        <p>${error}</p>
        <a href="/">Go Back to Home</a>
    </c:when>

    <!-- If the rented movie is available, show movie details -->
    <c:otherwise>
        <h1>Rental Confirmation</h1>
        <h2>Thank you for renting, ${rentedMovie.title}!</h2>
        <p>${rentedMovie.description}</p>
        <a href="/">Go Back to Home</a>
    </c:otherwise>
</c:choose>
</body>
</html>
