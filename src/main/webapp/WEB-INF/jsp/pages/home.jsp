<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<script>
    function addToWishlist(movieId) {
        const userId = '${sessionScope.userId}';
        fetch('/wishlist/add?userId=' + userId + '&movieId=' + movieId, { method: 'POST' })
            .then(response => {
                if (response.ok) {
                    alert('Movie added to your Wishlist!');
                } else {
                    alert('Failed to add movie to Wishlist.');
                }
            });
    }
</script>

<div class="search-container">
    <form action="${pageContext.request.contextPath}/home" method="get">
        <input type="text" name="query" class="search-input" placeholder="Search movies or series..." />
        <button type="submit" class="search-button">
            <span class="material-icons">search</span>
        </button>
    </form>
</div>

<p class="tagline">This is the Prime Reel home page. Explore movies, series, and more!</p>

<c:if test="${not empty param.query}">
    <h2>Search Results for "<c:out value='${param.query}'/>"</h2>
</c:if>

<c:if test="${empty movies}">
    <p>No movies found matching your search.</p>
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
                <h2>${movie.title}</h2>
                <p>${movie.description}</p>

                <div class="movie-actions">
                    <c:set var="movieIds" value="${rentedMovieIds}" />
                    <c:set var="isFound" value="false" />
                    <c:forEach var="id" items="${movieIds}">
                        <c:if test="${movie.id == id}">
                            <c:set var="isFound" value="true" />
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${isFound}">
                            <form action="/rentals/unrent" method="post">
                                <input type="hidden" name="movieId" value="${movie.id}" />
                                <input type="hidden" name="userId" value="${sessionScope.userId}" />
                                <button type="submit" class="action-button" style="background-color:#e74c3c;">
                                    <span class="material-icons">remove_shopping_cart</span> Unrent
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="/rentals" method="post">
                                <input type="hidden" name="movieId" value="${movie.id}" />
                                <input type="hidden" name="userId" value="${sessionScope.userId}" />
                                <button type="submit" class="action-button">
                                    <span class="material-icons">shopping_cart</span> Rent
                                </button>
                            </form>
                        </c:otherwise>
                    </c:choose>

                    <button class="action-button" onclick="addToWishlist('${movie.id}')">
                        <span class="material-icons">favorite</span>
                    </button>

                    <div class="action-button">
                        <span class="material-icons">star_rate</span> 
<fmt:formatNumber value="${movie.rating}" type="number" maxFractionDigits="1" />

                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
