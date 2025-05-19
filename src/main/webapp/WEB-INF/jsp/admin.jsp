<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Panel - Prime Reel</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 30px;
            color: #333;
        }

        .top-bar {
            background-color: #1e1e2f;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-radius: 8px;
        }

        .top-bar-left h1 {
            margin: 0;
            font-size: 22px;
        }

        .top-bar-right {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .top-bar-user {
            font-size: 14px;
            color: #ccc;
        }

        .logout-btn {
            background-color: #e74c3c;
            color: white;
            padding: 8px 14px;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
        }

        .logout-btn:hover {
            background-color: #c0392b;
        }

        h2 {
            margin-top: 40px;
            color: #34495e;
        }

        .form-section, .table-section {
            background-color: white;
            padding: 20px;
            margin-top: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        form {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            align-items: center;
        }

        input[type="text"], input[type="number"], select {
            padding: 8px;
            width: 200px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            padding: 8px 14px;
            background-color: #2ecc71;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }

        button:hover {
            background-color: #27ae60;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f1f1f1;
            text-align: left;
        }

        .danger {
            background-color: #e74c3c;
        }

        .danger:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>

<!-- Top Bar -->
<div class="top-bar">
    <div class="top-bar-left">
        <h1>Admin Dashboard</h1>
    </div>
    <div class="top-bar-right">
        <p class="top-bar-user">Logged in as: <b>${sessionScope.username}</b></p>
        <form action="${pageContext.request.contextPath}/auth/logout" method="post">
            <button type="submit" class="logout-btn">Logout</button>
        </form>
    </div>
</div>


<!-- Add Movie Form -->
<div class="form-section">
    <h2>Add New Movie</h2>
    <form action="/admin/add-movie" method="post">
        <input type="text" name="title" placeholder="Title" required />
        <input type="text" name="description" placeholder="Description" required />
        <input type="hidden" name="rating" value="0" />
        <select name="available">
            <option value="true">Available</option>
            <option value="false">Unavailable</option>
        </select>
        <input type="text" name="posterUrl" placeholder="Poster URL (optional)" />
        <button type="submit">Add Movie</button>
    </form>
</div>


<!-- Users Table -->
<div class="table-section">
    <h2>All Users</h2>
    <table>
        <tr>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
            <th>Action</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>
                    <form method="post" action="/admin/delete-user">
                        <input type="hidden" name="userId" value="${user.userId}" />
                        <button type="submit" class="danger">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<!-- Movies Table -->
<div class="table-section">
    <h2>All Movies</h2>
    <table>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Availability</th>
            <th>Poster URL</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="movie" items="${movies}">
            <tr>
                <form method="post" action="/admin/update-movie">
                    <input type="hidden" name="movieId" value="${movie.id}" />
                    <td><input type="text" name="title" value="${movie.title}" required /></td>
                    <td><input type="text" name="description" value="${movie.description}" required /></td>
                    <td>
                        <select name="available">
                            <option value="true" ${movie.available ? 'selected' : ''}>Available</option>
                            <option value="false" ${!movie.available ? 'selected' : ''}>Unavailable</option>
                        </select>
                    </td>
                    <td><input type="text" name="posterUrl" value="${movie.posterUrl}" placeholder="Poster URL" /></td>
                    <td style="display: flex; gap: 8px;">
                        <button type="submit">Save</button>
                    </form>
                    <form method="post" action="/admin/delete-movie" style="display:inline;">
                        <input type="hidden" name="movieId" value="${movie.id}" />
                        <button type="submit" class="danger">Delete</button>
                    </form>
                    </td>
            </tr>
            
        </c:forEach>
    </table>
</div>


</body>
</html>
