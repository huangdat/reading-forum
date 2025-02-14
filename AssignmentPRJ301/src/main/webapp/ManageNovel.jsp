<%@page import="Model.Novel"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Novel" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Manage Novels</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .novel-row {
                display: flex;
                justify-content: space-between; /* Space between containers */
                margin-bottom: 40px;
            }

            .novel-container {
                width: 48%; /* Width for containers to fit side by side */
                border: 1px solid #ddd;
                border-radius: 5px;
                padding : 20px;
                background-color: #f9f9f9;
                display: flex;
                align-items: center;
            }

            .cover {
                width: 150px;
                height: auto;
                border-radius: 5px;
                margin-right: 20px;
            }

            .novel-content {
                flex: 1; /* Let content take available space */
            }

            .novel-content p {
                margin: 5px 0;
            }

            .button-group {
                margin-left: 20px; /* Space between content and buttons */
            }

            .search-bar {
                margin-bottom: 20px;
                display: flex;
                justify-content: center;
            }

            .pagination-container {
                display: flex;
                justify-content: flex-end;
                margin-top: 20px;
            }

            .title {
                text-align: center;
                margin-top: 20px;
                margin-bottom: 20px;
            }

            .alert {
                margin-top: 40px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="title">Manage Created Post List</h2>
            
            <a  href="/accountController/account" class="btn btn-danger" >back</a>

            <div class="search-bar">
                <input type="text" id="search" class="form-control" placeholder="Search post..."
                       value="<%= request.getAttribute("query") != null ? request.getAttribute("query") : "" %>">
                <button class="btn btn-primary ml-2" onclick="searchNovels()">Search</button>
            </div>

            <div>
                <%
                    List<Novel> novels = (List<Novel>) request.getAttribute("listNovel");
                    if (novels != null && !novels.isEmpty()) {
                        for (int i = 0; i < novels.size(); i += 2) {
                %>
                <div class="novel-row">
                    <%
                        for (int j = 0; j < 2; j++) {
                            if ((i + j) < novels.size()) {
                                Novel novel = novels.get(i + j);
                    %>
                    <div class="novel-container">
                        <img src="<%= novel.getCoverImg() %>" alt="Cover Image" class="cover">
                        <div class="novel-content">
                            <p><strong><%= novel.getNovelTitle() %></strong></p>
                            <p><%= novel.getDescription() %></p>
                        </div>
                        <div class="button-group">
                            <a href="/EditPostServlet?id=<%= novel.getNovelId() %>" class="btn btn-primary">Edit</a>
                            <button class="btn btn-danger" onclick="confirmDelete('<%= novel.getNovelId() %>')">Delete</button>
                        </div>
                    </div>
                    <%
                            } else {
                    %>
                    <div class="novel-container" style="visibility: hidden;"></div>
                    <%
                            }
                        }
                    %>
                </div>
                <%
                        }
                    } else {
                %>
                <div class="alert alert-info">No novels found.</div>
                <%
                    }
                %>
            </div>

            <div class="pagination-container">
                <nav>
                    <ul class="pagination">
                        <%
                            Integer currentPage = (Integer) request.getAttribute("currentPage");
                            Integer totalPages = (Integer) request.getAttribute("totalPages");

                            if (currentPage == null) {
                                currentPage = 1;
                            }
                            if (totalPages == null) {
                                totalPages = 1;
                            }
                        %>
                        <li class="page-item <%= currentPage == 1 ? "disabled" : "" %>">
                            <a class="page-link" href="?page=<%= currentPage - 1 %>">Previous</a>
                        </li>
                        <%
                            for (int i = 1; i <= totalPages; i++) {
                        %>
                        <li class="page-item <%= currentPage == i ? "active" : "" %>">
                            <a class="page-link" href="?page=<%= i %>"><%= i %></a>
                        </li>
                        <%
                            }
                        %>
                        <li class="page-item <%= currentPage == totalPages ? "disabled" : "" %>">
                            <a class="page-link" href="?page=<%= currentPage + 1 %>">Next</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

        <script>
            function confirmDelete(novelId) {
                if (confirm("Are you sure you want to delete this novel?")) {
                    window.location.href = "/ManagePostServlet?action=delete&id=" + novelId;
                }
            }

            function searchNovels() {
                const query = document.getElementById("search").value;
                window.location.href = "?action=search&query=" + query;
            }
        </script>
    </body>
</html>
