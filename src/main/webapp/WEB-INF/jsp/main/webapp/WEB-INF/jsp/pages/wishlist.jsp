<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
  .wishlist-container {
    padding: 20px;
  }

  .movie-list {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
  }

  .movie-card {
    background-color: #fff;
    width: 240px;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    overflow: hidden;
    transition: transform 0.2s;
    position: relative;
  }

  .movie-card:hover {
    transform: translateY(-5px);
  }

  .movie-poster {
    width: 100%;
    height: 340px;
    object-fit: cover;
  }

  .movie-card-content {
    padding: 15px;
  }

  .movie-card-content h2 {
    font-size: 18px;
    margin: 0 0 10px;
    color: #333;
  }

  .movie-card-content p {
    font-size: 14px;
    color: #666;
    min-height: 40px;
  }

  .action-button {
    background-color: #e74c3c;
    color: #fff;
    border: none;
    padding: 8px 14px;
    border-radius: 4px;
    font-weight: bold;
    cursor: pointer;
    margin-top: 10px;
    display: inline-flex;
    align-items: center;
    gap: 5px;
  }

  .action-button:hover {
    background-color: #c0392b;
  }

  .material-icons {
    vertical-align: middle;
  }

  .empty-message {
    padding: 40px;
    font-size: 18px;
    text-align: center;
    color: #777;
  }
</style>

<script>
  function removeFromWishlist(movieId) {
    const userId = '${sessionScope.userId}';

    fetch('${pageContext.request.contextPath}/wishlist/remove', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({ userId: userId, movieId: movieId })
    })
    .then(response => {
      if (response.ok) {
        alert("Movie removed from wishlist!");
        const card = document.getElementById("movie-card-" + movieId);
        if (card) card.remove();

        // Optional: show message if all cards are gone
        if (document.querySelectorAll('.movie-card').length === 0) {
          document.querySelector('.movie-list').innerHTML = '<p class="empty-message">You have no movies in your wishlist.</p>';
        }

      } else {
        alert("Failed to remove movie.");
      }
    });
  }
</script>

<div class="wishlist-container">
  <c:choose>
    <c:when test="${empty movies}">
      <p class="empty-message">You have no movies in your wishlist.</p>
    </c:when>
    <c:otherwise>
      <div class="movie-list">
        <c:forEach var="movie" items="${movies}">
          <div class="movie-card" id="movie-card-${movie.id}">
            <a href="${pageContext.request.contextPath}/movie/view/${movie.id}">
              <c:choose>
                <c:when test="${not empty movie.posterUrl}">
                  <img src="${movie.posterUrl}" alt="${movie.title}" class="movie-poster"/>
                </c:when>
                <c:otherwise>
                  <img src="https://www.shutterstock.com/image-vector/default-ui-image-placeholder-wireframes-600nw-1037719192.jpg"
                       alt="Default Poster" class="movie-poster"/>
                </c:otherwise>
              </c:choose>
            </a>
            <div class="movie-card-content">
              <h2>${movie.title}</h2>
              <p>${movie.description}</p>
              <button class="action-button" onclick="removeFromWishlist('${movie.id}')">
                <span class="material-icons">delete</span> Remove
              </button>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
</div>
