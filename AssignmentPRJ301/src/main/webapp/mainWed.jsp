<%@page import="DAO.accountDAO" %>
<%@page import="DAO.LoginDAO" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="DAO.mainWedDao" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>
    <link href="https://fonts.googleapis.com/css2?family=Prata&family=Raleway:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <style>
        /* Reset và biến CSS chung */
        :root {
            --booksaw-color: #A68A6A; /* Định nghĩa màu Booksaw */
            --accent-color: #C5A992;
            --dark-color: #2f2f2f;
            --light-color: #F3F2EC;
            --body-text-color: #757575;
            --body-font: "Raleway", sans-serif;
            --heading-font: "Prata", Georgia, serif;
        }

        body, h1, h2, h3, h4, h5, h6, p, a, span, div {
            font-family: 'Roboto', sans-serif !important;
        }

        body {
            font-family: var(--body-font);
            font-size: 16px;
            line-height: 1.7;
            color: var(--body-text-color);
            background-color: var(--light-color);
            margin: 0;
            padding: 0;
        }

        h1, h2, h3, h4, h5, h6 {
            font-family: var(--heading-font);
            line-height: 1.2;
            color: var(--dark-color);
        }

        /* Header styles */
        .box {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            height: 80px;
            background: #f3f2ec;
            border-bottom: 1px solid #E0E0E0;
            z-index: 1000;
            display: flex;
            align-items: center;
            padding: 0 2.5rem;
            border-radius: 0 0 15px 15px;
        }

        #animated-gif {
            position: absolute;
            height: 90px;
            width: auto;
            left: 35px;
            top: 10px;
        }

        .search-form {
            position: relative;
            margin-left: auto;
            margin-right: 20px;
        }

        .search-form input {
            background: #EFEEE8;
            border: none;
            border-radius: 40px;
            padding: 12px 20px;
            width: 300px;
            font-weight: 300;
        }

        .Topluocxem {
            text-align: center;
        }

        .search-button {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            background: transparent;
            border: none;
            color: var(--body-text-color);
            border-radius: 50%;
        }

        .login-button {
            list-style: none;
            margin: 0 20px;
        }

        .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            border: 2px solid var(--accent-color);
        }

        .noti-button {
            background: none;
            border: none;
            cursor: pointer;
            padding: 0;
        }

        .noti-button:hover {
            color: orange;
        }

        .fa-bell {
            color: gold;
            font-size: 33px;
        }

        .fa-bell:hover {
            animation: shake 0.5s infinite;
        }

        /* Main content styles */
        .parallax {
            padding: 120px 0 60px;
            background-color: var(--light-color);
        }

        h1.mt-5 {
            margin-top: 0px;
            text-align: center;
            color: #A68A6A; /* Màu giống như Booksaw */
        }

        .parallax h1 {
            font-family: var(--heading-font);
            font-size: 2.5em;
            color: var(--booksaw-color); /* Sử dụng biến */
            margin: 0 0 40px 50px;
            position: relative;
            font-weight: 400;
        }

        .parallax h1:after {
            content: "";
            position: absolute;
            bottom: -15px;
            left: 0;
            width: 100%; /* Đặt chiều rộng thành 100% */
            height: 3px;
            background: var(--accent-color);
        }

        /* Top favorites section */
        .top-favorites {
            display: flex;
            flex-wrap: nowrap;
            overflow-x: auto;
            justify-content: flex-start;
            margin-left: 50px;
            margin-right: 50px;
            padding: 20px 0;
        }

        .top-favorites .novel-item {
            width: 24%;
            margin-right: 1%;
            flex-shrink: 0;
            background: #fff;
            border: none;
            overflow: hidden;
            transition: transform 0.3s ease;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .top-favorites .novel-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }

        .top-favorites img {
            width: 100%;
            height: 300px;
            object-fit: cover;
            background: #EFEEE8;
            border: 1px solid #EAE8DF;
            padding: 15px;
            border-radius: 15px 15px 0 0;
        }

        /* Tùy chỉnh thanh cuộn ngang */
        .top-favorites::-webkit-scrollbar {
            height: 8px;
        }

        .top-favorites::-webkit-scrollbar-track {
            background: #f1f1f1;
            border-radius: 10px;
        }

        .top-favorites::-webkit-scrollbar-thumb {
            background: var(--accent-color);
            border-radius: 10px;
        }

        .top-favorites::-webkit-scrollbar-thumb:hover {
            background: var(--dark-color);
        }

        /* Novel items general */
        .novel-item {
            background: #fff;
            border: none;
            border-radius: 15px;
            overflow: hidden;
            transition: transform 0.3s ease;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .novel-item:hover {
            transform: translateY(-5px);
        }

        .novel-item img {
            width: 100%;
            height: 350px;
            object-fit: cover;
            background: #EFEEE8;
            border: 1px solid #EAE8DF;
            padding: 15px;
            transition: transform 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94);
            border-radius: 15px 15px 0 0;
        }

        .novel-item:hover img {
            transform: scale(1.1);
        }

        .novel-item .info {
            padding: 20px;
            text-align: center;
        }

        .novel-item h3 {
            font-family: var(--heading-font);
            font-size: 1.4em;
            color: var(--accent-color);
            margin: 10px 0;
            font-weight: 400;
        }

        /* New releases section */
        .new-releases {
            padding: 80px 0;
        }

        .new-releases h1 {
            font-family: var(--heading-font);
            font-size: 2.5em;
            color: var(--dark-text-color);
            margin-bottom: 50px;
            position: relative;
            font-weight: 400;
        }

        .new-releases h1:after {
            content: "";
            position: absolute;
            bottom: -15px;
            left: 0;
            width: 50px;
            height: 3px;
            background: var(--accent-color);
        }

        .new-release-item {
            display: flex;
            gap: 30px;
            margin-bottom: 30px;
            padding: 20px;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .new-release-item img {
            width: 200px;
            height: 280px;
            object-fit: cover;
            border-radius: 10px;
        }

        /* Sidebar sections */
        .section-container h1 {
            font-family: var(--heading-font);
            font-size: 2em;
            color: var(--dark-text-color);
            margin-bottom: 30px;
            font-weight: 400;
        }

        p {
            margin-bottom: 20px;
        }

        .text-lead {
            font-size: 1.1em;
            line-height: 1.6;
        }

        .text-small {
            font-size: 0.9em;
        }

        /* Buttons */
        .btn {
            font-family: var(--body-font);
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        .section-container h1 {
            font-family: var(--heading-font);
            font-size: 2em;
            color: var(--dark-text-color);
            margin-bottom: 30px;
        }

        /* Pagination */
        #pagination {
            text-align: center;
            margin-top: 30px;
        }

        #pagination button {
            background: transparent;
            border: 1px solid var(--accent-color);
            color: var(--accent-color);
            padding: 8px 15px;
            margin: 0 5px;
            border-radius: 20px;
            transition: all 0.3s ease;
        }

        #pagination button.active,
        #pagination button:hover {
            background: var(--accent-color);
            color: #fff;
        }

        /* Genre tags */
        .genre-button {
            background: #EFEEE8;
            color: var(--body-text-color);
            border: none;
            padding: 5px 15px;
            margin: 3px;
            border-radius: 20px;
            font-size: 0.9em;
        }

        .novel-list {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }
    </style>
</head>
<body>
<div class="box">
    <a href="/LoginServlet/guest">
        <img id="animated-gif" src="/imgs/weblogo.png" alt="weblogo" style="height: 50px; width: auto;">
    </a>
    <h3 style="margin: auto">All knowledge in your hands</h3>
    <form class="search-form" action="/SearchingController">
        <input type="text" placeholder="Searching...">
        <button type="submit" class="search-button"><i class="fa-solid fa-magnifying-glass"></i></button>
    </form>
    <ul>
        <li class="login-button">
            <%-- Kiểm tra session để đảm bảo người dùng đã đăng nhập --%>
            <% if (session.getAttribute("user") != null) { %>
            <%-- Lấy thông tin người dùng từ session và DAO --%>
            <%
                String user = (String) session.getAttribute("user");
                accountDAO daoAcc = new accountDAO();
                ResultSet rsUser = daoAcc.getUser(user);
                if (rsUser.next()) {
                    String avatarUrl = rsUser.getString("avatar");

            %>
            <a href="/accountController/account">
                <img src="<%= avatarUrl%>" alt="Avatar" class="avatar">
            </a>
            <% } %>
            <% } %>
        </li>

    </ul>

</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-12 parallax">
            <h1 class="Topluocxem">Top popular</h1>
            <div class="top-favorites">

                <%
                    mainWedDao daoMain = new mainWedDao();
                    ResultSet rsM = daoMain.getAll();
                    while (rsM.next()) {%>

                <div class="novel-item">
                    <a href="/PostServlet/novel_id/<%= rsM.getString("novel_id")%>">
                        <img src="<%= rsM.getString("cover_img")%>" alt="<%= rsM.getString("novel_title")%>">
                    </a>

                    <div class="info">
                        <h3><%= rsM.getString("novel_title")%>
                        </h3>
                    </div>
                </div>
                <% }%>

            </div>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <!--<div class="col-lg-9 new-releases">-->
            <h1 class="mt-5">Up to date</h1>

            <div id="novel-list">
                <%
                    mainWedDao daoGen = new mainWedDao();
                    mainWedDao dao = new mainWedDao();
                    ResultSet rs = dao.getNewUpdate();
                    while (rs.next()) {
                        ResultSet rsG = daoGen.NovelGenre(rs.getString("novel_id"));
                %>
                <div class="novel-item new-release-item">
                    <a href="/PostServlet/novel_id/<%= rs.getString("novel_id")%>" style="display: inline-block;">
                        <img src="<%= rs.getString("cover_img")%>" alt="<%= rs.getString("novel_title")%>">
                    </a>
                    <div class="info" style="display: inline-block; padding-left: 20px; text-align: left;">
    <h3 style="font-size: 1.8em;"><%= rs.getString("novel_title")%></h3>
    <p style="font-size: 1.2em;">Author: <%= rs.getString("author")%></p>
    <p style="font-size: 1.2em;">Language: <%= rs.getString("novel_status")%></p>
    <%-- <p style="font-size: 1.2em;"><%=rs.getString("description")%></p> --%>
    <p style="font-size: 1.2em;">
        <% while (rsG.next()) { %>
        <span class="genre-button"><%= rsG.getString("genre_name")%></span>
        <% } %>
    </p>
</div>
                </div>
                <% }%>
            </div>
            <div id="pagination">
            </div>
            <!--</div>-->
            <!--<div class="col-lg-3">-->
            <div class="section-container mt-5 favorite">
                <h1>Favorite</h1>
                <%
                    mainWedDao daoFav = new mainWedDao();
                    ResultSet rsFav = daoFav.favourite();
                    while (rsFav.next()) {
                %>
                <div class="novel-item" style="display: flex; align-items: center;">
                    <a href="/PostServlet/novel_id/<%= rsFav.getString("novel_id")%>">
                        <img src="<%= rsFav.getString("cover_img")%>"
                             style="max-width: 80px; max-height: 80px; margin-right: 10px;"
                             alt="<%= rsFav.getString("novel_title")%>">
                    </a><

                    <div class="info">
                        <h3><%= rsFav.getString("novel_title")%>
                        </h3>
                    </div>
                </div>

                <% }%>
            </div>

<%--            <div class="section-container mt-5 history">--%>
<%--                <h1>Reading History</h1>--%>
<%--                <%--%>
<%--                    mainWedDao daoHis = new mainWedDao();--%>
<%--                    ResultSet rsHis = daoHis.readingHistory();--%>
<%--                    while (rsHis.next()) {--%>
<%--                %>--%>
<%--                <div class="novel-item" style="display: flex; align-items: center;">--%>
<%--                    <a href="/PostServlet/novel_id/<%= rsHis.getString("novel_id")%>">--%>
<%--                        <img src="<%= rsHis.getString("cover_img")%>"--%>
<%--                             style="max-width: 80px; max-height: 80px; margin-right: 10px;"--%>
<%--                             alt="<%= rsHis.getString("novel_title")%>">--%>
<%--                    </a>--%>
<%--                    <div class="info">--%>
<%--                        <h3><%= rsHis.getString("novel_title")%>--%>
<%--                        </h3>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <% }%>--%>
<%--            </div>--%>
            <!--</div>-->
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Số lượng truyện hiển thị trên mỗi trang
            const itemsPerPage = 5;

            // Lấy danh sách truyện (chỉ trong phần "Mới cập nhật")
            const novels = document.querySelectorAll('.new-release-item');

            // Hàm để tạo nút chuyển trang
            function createPaginationButton(page) {
                const button = document.createElement('button');
                button.textContent = page;
                button.addEventListener('click', function () {
                    displayNovels(page);
                });
                return button;
            }

            // Hàm để hiển thị danh sách truyện (chỉ trong phần "Mới cập nhật")
            function displayNovels(page) {
                const startIndex = (page - 1) * itemsPerPage;
                const endIndex = startIndex + itemsPerPage;

                // Ẩn tất cả các truyện trong phần "Mới cập nhật"
                novels.forEach(novel => novel.style.display = 'none');

                // Hiển thị các truyện trong trang hiện tại
                for (let i = startIndex; i < endIndex && i < novels.length; i++) {
                    novels[i].style.display = 'block';
                }

                // Cập nhật các nút chuyển trang
                const paginationContainer = document.getElementById('pagination');
                paginationContainer.innerHTML = '';
                const totalPages = Math.ceil(novels.length / itemsPerPage);
                for (let i = 1; i <= totalPages; i++) {
                    const button = createPaginationButton(i);
                    if (i === page) {
                        button.classList.add('active');
                    }
                    paginationContainer.appendChild(button);
                }
            }

            // Hiển thị trang đầu tiên khi trang tải xong
            displayNovels(1);
        });
    </script>
</div>
</body>
</html>