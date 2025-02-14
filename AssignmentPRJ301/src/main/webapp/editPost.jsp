<%@ page import="DAO.editNovelDAO" %>
<%@ page import="Model.novelInfor" %><%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang Dat
  Date: 11/5/2024
  Time: 2:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Post</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        .main-content {
            max-width: 1000px; /* Adjust the width as needed */
            margin: auto; /* Center the table */
        }
    </style>
</head>
<body>

<%
    editNovelDAO dao = new editNovelDAO();
    String id = request.getParameter("id");
    novelInfor novel = dao.getNovelInfor(id);

%>

<%--Main content of edit--%>
<div class="main-content">

    <form class="form" method="post">

        <div class="form-group row">
            <label for="title" class="form-label col-sm-2 col-form-label">Title</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="title" Readonly id="title" value="<%=novel.getNovel_title()%>"
                       disabled>
            </div>
        </div>
        <div class="form-group row">
            <label for="author" class="form-label col-sm-2 col-form-label">Author</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="author" id="author" required
                       value="<%=novel.getAuthor()%>">
            </div>
        </div>
        <div class="form-group row">
            <label for="status" class="form-label col-sm-2 col-form-label">Language</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="status" id="status" required
                       value="<%=novel.getStatus()%>">
            </div>
        </div>
        <div class="form-group row">
            <label for="description" class="form-label col-sm-2 col-form-label">Content</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="description" id="description" required
                       value="<%=novel.getDescription()%>">
            </div>
        </div>


        <div>
            <input class="su btn btn-primary" type="submit" name="btnSave" value="Save">
            <a class="a btn btn-danger" href="ManagePostServlet">Back</a>
        </div>

    </form>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
