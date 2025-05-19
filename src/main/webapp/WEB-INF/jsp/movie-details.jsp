<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${movie.title} - Details</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .star-rating {
    direction: rtl;
    display: inline-flex;
}

.star-rating input[type="radio"] {
    display: none;
}

.star-rating label {
    font-size: 24px;
    color: #ccc;
    cursor: pointer;
    padding: 0 3px;
}

.star-rating input[type="radio"]:checked ~ label {
    color: #ffcc00;
}

.star-rating label:hover,
.star-rating label:hover ~ label {
    color: #ffcc00;
}


        /* Header styles */
        .navbar {
            background-color: #1e1e2f;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-family: 'Segoe UI', sans-serif;
            flex-wrap: wrap;
        }

        .navbar-left {
            display: flex;
            align-items: center;
        }

        .navbar-logo {
            font-size: 24px;
            font-weight: bold;
            color: #ffcc00;
            margin-right: 30px;
            text-decoration: none;
        }

        .navbar nav a {
            margin-right: 20px;
            color: #ffffff;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .navbar nav a:hover {
            color: #ffcc00;
        }

        .navbar-right {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .navbar-user {
            margin: 0;
            font-size: 14px;
            color: #ccc;
        }

        .btn {
            background-color: #ffcc00;
            border: none;
            color: #1e1e2f;
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #e6b800;
        }

        .logout-form {
            margin: 0;
        }

        /* Main content */
        .container {
            padding: 30px;
        }

        .movie-details {
            display: flex;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        .poster {
            max-width: 250px;
            margin-right: 30px;
            border-radius: 6px;
        }

        .info h1 {
            margin: 0 0 10px 0;
        }

        .info p {
            margin: 5px 0;
        }

        .review-form, .reviews {
            background-color: white;
            margin-top: 30px;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        input[type="number"], textarea {
            width: 100%;
            padding: 10px;
            margin-top: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            resize: vertical;
        }

        button {
            margin-top: 10px;
            padding: 10px 18px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
        }

        button:hover {
            background-color: #2980b9;
        }

        .review {
            border-bottom: 1px solid #eee;
            padding: 15px 0;
        }

        .review strong {
            color: #333;
        }

        .delete-btn {
            background-color: #e74c3c;
            margin-left: 10px;
        }

        .delete-btn:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>

<!-- Header -->
<header class="navbar">
    <div class="navbar-left">
        <a href="${pageContext.request.contextPath}/home" class="navbar-logo">Prime Reel</a>
        <nav>
            <a href="${pageContext.request.contextPath}/wishlist">Wishlist</a>
            <a href="${pageContext.request.contextPath}/rented">Rented</a>
        </nav>
    </div>
    <div class="navbar-right">
        <p class="navbar-user">Logged in as: <b>${sessionScope.username}</b></p>
        <form action="${pageContext.request.contextPath}/auth/logout" method="post" class="logout-form">
            <button type="submit" class="btn">Logout</button>
        </form>
    </div>
</header>

<div class="container">
    <!-- Movie Details -->
    <div class="movie-details">
        <c:choose>
    <c:when test="${not empty movie.posterUrl}">
        <img class="poster" src="${movie.posterUrl}" alt="${movie.title}" />
    </c:when>
    <c:otherwise>
        <img class="poster" src="https://www.shutterstock.com/image-vector/default-ui-image-placeholder-wireframes-600nw-1037719192.jpg" alt="Default Poster" />
    </c:otherwise>
</c:choose>

        <div class="info">
            <h1>${movie.title}</h1>
            <p><strong>Description:</strong> ${movie.description}</p>
            <span class="material-icons">star_rate</span> 
<fmt:formatNumber value="${movie.rating}" type="number" maxFractionDigits="1" />

            <p><strong>Status:</strong>
                <c:choose>
                    <c:when test="${movie.available}">Available</c:when>
                    <c:otherwise>Not Available</c:otherwise>
                </c:choose>
            </p>
        </div>
    </div>

    <!-- Review Form -->
    
    <div class="review-form">
        <h2>Add a Review</h2>
        <form action="/movie/${movie.id}/reviews" method="post">
            <input type="hidden" name="userId" value="${sessionScope.userId}" />
            <input type="hidden" name="username" value="${sessionScope.username}" />
    
            <label>Rating (1-5 stars):</label>
<div class="star-rating">
    <c:forEach var="i" begin="1" end="5">
        <c:set var="star" value="${6 - i}" />
        <input type="radio" id="star${star}" name="rating" value="${star}" required />
        <label for="star${star}">&#9733;</label>
    </c:forEach>
</div>

    
            <label>Review:</label>
            <textarea name="content" rows="4" placeholder="Write your review..." required></textarea>
            <button type="submit">Submit Review</button>
        </form>
    </div>
    


    <!-- Review List -->
    <div class="reviews">
        <h2>Reviews</h2>
        <c:if test="${not empty movie.reviews}">
            <c:forEach var="review" items="${movie.reviews}">
                <div class="review">
                    <strong>${review.username}</strong> -
<c:forEach var="i" begin="1" end="5">
    <c:choose>
        <c:when test="${i <= review.rating}">&#9733;</c:when>
        <c:otherwise>&#9734;</c:otherwise>
    </c:choose>
</c:forEach>
<br/>

                    <p>${review.content}</p>

                    <c:if test="${review.userId == currentUserId}">
    <form method="post" action="/movie/${movie.id}/reviews/${review.reviewId}?userId=${currentUserId}">
        <button type="submit" class="delete-btn">Delete</button>
    </form>

    <c:choose>
        <c:when test="${editingReviewId == review.reviewId}">
            <!-- Edit Form Starts -->
            <form method="post" action="/movie/${movie.id}/reviews/${review.reviewId}/edit">
                <input type="hidden" name="userId" value="${currentUserId}" />
                
                <label>Update Rating:</label>
                <div class="star-rating">
                    <c:forEach var="i" begin="1" end="5">
                        <c:set var="star" value="${6 - i}" />
                        <input type="radio" id="edit-star${star}" name="rating" value="${star}"
                            <c:if test="${star == review.rating}">checked</c:if> />
                        <label for="edit-star${star}">&#9733;</label>
                    </c:forEach>
                </div>

                <label>Update Review:</label>
                <textarea name="content" rows="3">${review.content}</textarea>
                <button type="submit" class="btn">Update</button>
            </form>
            <!-- Edit Form Ends -->
        </c:when>

        <c:otherwise>
    <a class="btn" href="/movie/view/${movie.id}?editingReviewId=${review.reviewId}">Edit</a>
</c:otherwise>

    </c:choose>
</c:if>


                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty movie.reviews}">
            <p>No reviews yet. Be the first to add one!</p>
        </c:if>
    </div>
</div>

</body>
</html>
