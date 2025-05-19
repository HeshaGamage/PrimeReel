<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .rented-title {
        font-size: 28px;
        margin-bottom: 20px;
        color: #2c3e50;
        font-weight: bold;
    }

    .movie-list {
        display: flex;
        flex-wrap: wrap;
        gap: 24px;
        justify-content: flex-start;
        padding: 10px;
    }

    .movie-card {
        background-color: #ffffff;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        overflow: hidden;
        width: 280px;
        display: flex;
        flex-direction: column;
        transition: transform 0.2s ease-in-out;
    }

    .movie-card:hover {
        transform: scale(1.02);
    }

    .movie-card img.poster {
        width: 100%;
        height: 360px;
        object-fit: cover;
    }

    .movie-card-content {
        padding: 16px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        flex-grow: 1;
    }

    .movie-card-content h3 {
        font-size: 20px;
        color: #333;
        margin: 0 0 8px 0;
    }

    .movie-card-content p {
        font-size: 14px;
        color: #666;
        margin-bottom: 12px;
    }

    .action-button {
        background-color: #f39c12;
        color: white;
        border: none;
        padding: 8px 12px;
        border-radius: 4px;
        cursor: pointer;
        font-weight: bold;
        display: inline-flex;
        align-items: center;
        gap: 6px;
        font-size: 14px;
        margin-top: 8px;
    }

    .action-button:hover {
        background-color: #d68910;
    }

    .unrent-button {
        background-color: #e74c3c;
    }

    .unrent-button:hover {
        background-color: #c0392b;
    }

    .material-icons {
        font-size: 18px;
        vertical-align: middle;
    }

    .empty-message {
        font-size: 16px;
        color: #7f8c8d;
        padding: 20px 0;
    }
</style>

<h2 class="rented-title">My Rented Movies</h2>

<c:if test="${empty movies}">
    <p class="empty-message">You haven't rented any movies yet.</p>
</c:if>

<div class="movie-list">
    <c:forEach var="movie" items="${movies}">
        <div class="movie-card">
            <a href="${pageContext.request.contextPath}/movie/view/${movie.id}">
                <c:choose>
                    <c:when test="${not empty movie.posterUrl}">
                        <img class="poster" src="${movie.posterUrl}" alt="${movie.title}" />
                    </c:when>
                    <c:otherwise>
                        <img class="poster" src="https://www.shutterstock.com/image-vector/default-ui-image-placeholder-wireframes-600nw-1037719192.jpg" alt="Default Poster" />
                    </c:otherwise>
                </c:choose>
            </a>

            <div class="movie-card-content">
                <h3>${movie.title}</h3>
                <p>${movie.description}</p>
                <div class="action-button">
                    <span class="material-icons">star_rate</span> ${movie.rating}
                </div>

                <!-- Unrent Form -->
                <form action="${pageContext.request.contextPath}/rentals/unrent" method="post">
                    <input type="hidden" name="movieId" value="${movie.id}" />
                    <input type="hidden" name="userId" value="${sessionScope.userId}" />
                    <button type="submit" class="action-button unrent-button">
                        <span class="material-icons">remove_shopping_cart</span> Unrent
                    </button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>
