<%@page import="DAO.accountDAO" %>
<%@page import="java.sql.ResultSet" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            background-image: url('/imgs/lofi-girl.png'); /* Ensure this path is correct */
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            background-attachment: fixed;
            color: black; /* Set all text to black */
        }

        .blurred-element {
            background-color: rgba(255, 255, 255, 0.29); /* Make background blur */
            border-radius: 10px; /* Optional: Add rounded corners */
            padding: 20px; /* Optional: Add padding */
        }

        .fixed-size-img {
            height: 200px; /* Set a fixed height */
            object-fit: cover; /* Ensure the image covers the area without distortion */
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="row">
        <div class="col-md-3">
            <%--            <div class="blurred-element">--%>
            <%
                String userId = (String) session.getAttribute("userId");

                String user = (String) session.getAttribute("user");
                accountDAO daoAcc = new accountDAO();
                ResultSet rsUser = daoAcc.getUser(user);
                if (rsUser.next()) {
            %>
            <div class="card blurred-element">
                <div class="card-body text-center">
                    <img src="<%= rsUser.getString("avatar")%>" alt="Avatar" class="rounded-circle img-fluid mb-3"
                         style="width: 120px; height: 120px;">
                    <h5 class="card-title"><strong><%= rsUser.getString("username")%>
                    </strong></h5></div>
            </div>
            <ul class="list-group mt-3 blurred-element">
                <% if (session.getAttribute("Admin") != null) { %>
                <li class="list-group-item blurred-element" style="background-color: rgba(255,255,255,0.59)"><a href="/CommentController" class="text-decoration-none"><i
                        class="fas fa-comment-dots"></i> <strong style="font-size: 1.2em;">Comment Management</strong></a>
                </li>
                <li class="list-group-item blurred-element"style="background-color: rgba(255,255,255,0.59)"><a href="/ManagePostServlet"
                                                               class="text-decoration-none"><i
                        class="fa-solid fa-list-check"></i> <strong style="font-size: 1.2em;">Post Management</strong></a>
                </li>
                <li class="list-group-item blurred-element" style="background-color: rgba(255,255,255,0.59)"><a href="/AddNovelController/edit"
                                                               class="text-decoration-none"><i
                        class="fa-solid fa-plus "></i> <strong style="font-size: 1.2em;">Create New Post</strong></a></li>
                <% } %>
                <li class="list-group-item blurred-element" style="background-color: rgba(255,255,255,0.59)"><a href="#" class="text-decoration-none"><i
                        class="fas fa-user-edit"></i> <strong style="font-size: 1.2em;">Update Infomation</strong></a></li>
                <li class="list-group-item blurred-element" style="background-color: rgba(255,255,255,0.59)"><a href="/LoginServlet/guest"
                                                               class="text-decoration-none"><i
                        class="fas fa-sign-out-alt"></i> <strong style="font-size: 1.2em;">Log Out</strong></a></li>
                <li class="list-group-item blurred-element"style="background-color: rgba(255,255,255,0.59)"><a href="#" id="delete-account"
                                                               class="text-decoration-none text-danger"><i
                        class="fas fa-trash-alt"></i> <strong style="font-size: 1.2em;">Delete Account</strong></a></li>
                <li class="list-group-item" style="border-inline-color: transparent; border-bottom-color: transparent; background-color: rgba(255,255,255,0)"><a href="/LoginServlet" class="btn btn-danger w-100"><i
                        class="fas fa-arrow-left"></i> <strong style="font-size: 1.2em;">Back</strong></a></li>
            </ul>
            <% } else {
                response.sendRedirect("đường_dẫn_trang_đăng_nhập");
            } %>
        </div>
        <%--        </div>--%>
        <div class="col-md-9">
            <div class="blurred-element">
                <h2 style="color: white; text-align: center">Your Favorite Posts</h2>
                <div class="input-group mb-3" style="backdrop-filter: blur(10px); width: 300px;">
                    <input type="text" id="search-input" class="form-control blurred-element"
                           placeholder="Filter Searching...">
                    <%-- <span class="input-group-text"><i class="fas fa-search"></i></span> --%>
                </div>
                <div class="row row-cols-1 row-cols-md-4 g-4">
                    <%
                        ResultSet rsFav = daoAcc.listFav(userId);
                        while (rsFav.next()) {
                    %>
                    <div class="col novel-item" data-search-term="<%= rsFav.getString("novel_title")%>">
                        <div class="card h-100" style="background-color: rgba(255,255,255,0.59)">
                            <a href="/PostServlet/novel_id/<%= rsFav.getString("novel_id")%>"
                               class="text-decoration-none">
                                <img src="<%= rsFav.getString("cover_img")%>" class="card-img-top fixed-size-img"
                                     alt="<%= rsFav.getString("novel_title")%>" style="color: black;">
                                <div class="card-body" style="color: black;">
                                    <h5 class="card-title"><%= rsFav.getString("novel_title")%>
                                    </h5>
                                </div>
                            </a>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN6jIeHz"
        crossorigin="anonymous"></script>
<script>
    const searchInput = document.getElementById("search-input");
    const novelItems = document.querySelectorAll(".novel-item");

    searchInput.addEventListener("input", () => {
        const searchTerm = searchInput.value.toLowerCase();
        novelItems.forEach(item => {
            const searchTermInItem = item.dataset.searchTerm.toLowerCase();
            if (searchTermInItem.includes(searchTerm)) {
                item.style.display = "block";
            } else {
                item.style.display = "none";
            }
        });
    });

    document.getElementById("delete-account").addEventListener("click", function () {
        if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
            fetch('/deleteaccount.jsp', {method: 'POST'})
                .then(response => {
                    if (response.ok) {
                        window.location.href = '/LoginController/guest';
                        setTimeout(() => {
                            alert("Account deleted successfully");
                        }, 500);
                    } else {
                        response.text().then(text => alert('Error deleting account: ' + text));
                    }
                })
                .catch(error => alert('Fetch error: ' + error.message));
        }
    });

    const favoritesContainer = document.querySelector('.favorites');

    favoritesContainer.addEventListener('wheel', (event) => {
        if (event.deltaY !== 0) {
            event.preventDefault();
            favoritesContainer.scrollBy(0, event.deltaY);
        }
    });
</script>
</body>
</html>