<%@page import="Model.CommentDelete"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Manage Comments</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .comment-row {
                display: flex;
                justify-content: space-between;
                margin-bottom: 40px;
            }
            .comment-container {
                flex: 0 1 calc(33.333% - 20px);
                margin: 0 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                padding: 20px;
                padding-left: 10px;
                box-sizing: border-box;
                background-color: #f9f9f9;
                display: flex;
                align-items: flex-start;
            }
            .avatar {
                width: 70px;
                height: 70px;
                border-radius: 50%;
                margin-right: 15px;
            }
            .comment-content {
                flex: 1;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .comment-content p {
                margin: 0;
            }
            .comment-content strong {
                display: inline-block;
                margin-bottom: 10px;
            }
            .delete-button {
                margin-left: 15px;
                align-self: flex-start;
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
            <h2 class="title">Manage Comments</h2>
                        <a  href="/accountController/account" class="btn btn-danger" >back</a>


            <div class="search-bar">
                <input type="text" id="search" class="form-control" placeholder="Search comments..." value="<%= request.getAttribute("query") != null ? request.getAttribute("query") : "" %>">
                <button class="btn btn-primary ml-2" onclick="searchComments()">Search</button>
            </div>

            <div>
                <%
                    List<CommentDelete> comments = (List<CommentDelete>) request.getAttribute("listComment");
                    if (comments != null && !comments.isEmpty()) {
                        for (int i = 0; i < Math.min(comments.size(), 9); i += 3) {
                %>
                <div class="comment-row">
                    <%
                        for (int j = 0; j < 3; j++) {
                            if ((i + j) < comments.size()) {
                                CommentDelete comment = comments.get(i + j);
                    %>
                    <div class="comment-container">
                        <img src="<%= comment.getAvatar() %>" alt="Avatar" class="avatar">
                        <div class="comment-content">
                            <p><strong><%= comment.getUsername() %></strong></p>
                            <p><%= comment.getCommentContent() %></p>
                        </div>
                        <button class="btn btn-danger delete-button" onclick="confirmDelete('<%= comment.getCommentId() %>')">Delete</button>
                    </div>
                    <%
                            } else {
                    %>
                    <div class="comment-container" style="visibility: hidden;"></div>
                    <%
                            }
                        }
                    %>
                </div>
                <%
                        }
                    } else {
                %>
                <div class="alert alert-info">No comments found.</div>
                <%
                    }
                %>
            </div>

            <div class="pagination-container">
                <nav>
                    <ul class="pagination">
                        <li class="page-item <%= (int) request.getAttribute("page") == 1 ? "disabled" : "" %>">
                            <a class="page-link" href="?page=<%= (int) request.getAttribute("page") - 1 %>">Previous</a>
                        </li>
                        <%
                            int totalPages = (int) request.getAttribute("totalPages");
                            for (int i = 1; i <= totalPages; i++) {
                        %>
                        <li class="page-item <%= (int) request.getAttribute("page") == i ? "active" : "" %>">
                            <a class="page-link" href="?page=<%= i %>"><%= i %></a>
                        </li>
                        <%
                            }
                        %>
                        <li class="page-item <%= (int) request.getAttribute("page") == totalPages ? "disabled" : "" %>">
                            <a class="page-link" href="?page=<%= (int) request.getAttribute("page") + 1 %>">Next</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

        <script>
            function confirmDelete(commentId) {
                if (confirm("Are you sure you want to delete this comment?")) {
                    window.location.href = "/CommentController?action=delete&id=" + commentId;
                }
            }

            function searchComments() {
                const query = document.getElementById("search").value;
                window.location.href = "/CommentController?action=search&query=" + query;
            }

            <% if (request.getAttribute("noMoreComments") != null && (boolean) request.getAttribute("noMoreComments")) { %>
            alert("No more comments available.");
            <% } %>
        </script>
    </body>
</html>
