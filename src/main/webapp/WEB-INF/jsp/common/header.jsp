<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
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

    .back-link {
        display: inline-flex;
        align-items: center;
        gap: 6px;
        font-size: 0.9rem;
        color: #ccc;
        text-decoration: none;
        transition: color 0.3s ease;
        margin: 12px 0;
    }

    .back-link:hover {
        color: #ffcc00;
    }

    .logout-form {
        margin: 0;
    }

    @media (max-width: 768px) {
        .navbar {
            flex-direction: column;
            align-items: flex-start;
        }

        .navbar-right {
            margin-top: 10px;
        }
    }
</style>

<header class="navbar">
    <!-- Left: Logo and Navigation -->
    <div class="navbar-left">
        <a href="${pageContext.request.contextPath}/home" class="navbar-logo">Prime Reel</a>
        <nav>
            <a href="${pageContext.request.contextPath}/wishlist">Wishlist</a>
            <a href="${pageContext.request.contextPath}/rented">Rented</a>

            <!-- Admin link (only for ADMIN role) -->
            <c:if test="${sessionScope.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin">Admin Panel</a>
            </c:if>
        </nav>
    </div>

    <!-- Right: User Info, Back Button, Logout -->
    <div class="navbar-right">
        <!-- Show Back link if not already on /home -->
        <c:if test="${not empty originalPath and originalPath ne '/home'}">
            <a href="${pageContext.request.contextPath}/home" class="back-link">Back to Home</a>
        </c:if>

        <p class="navbar-user">Logged in as: <b>${sessionScope.username}</b></p>
        <form action="${pageContext.request.contextPath}/auth/logout" method="post" class="logout-form">
            <button type="submit" class="btn">Logout</button>
        </form>
    </div>
</header>
