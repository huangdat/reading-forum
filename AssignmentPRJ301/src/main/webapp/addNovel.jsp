<%@page import="java.sql.ResultSet"%>
<%@page import="DAO.editNovelDAO"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Blog</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Prata&display=swap" rel="stylesheet">

    <style>
        body {
            background-image: url('/imgs/backaddnew.jpeg');
            font-family: 'Prata', serif; /* Giữ nguyên font chữ */
            background-color: #f3f2ec;
            color: #333;
            display: flex;
            justify-content: center; /* Căn giữa theo chiều ngang */
            align-items: center; /* Căn giữa theo chiều dọc */
            min-height: 100vh; /* Chiều cao tối thiểu bằng chiều cao của viewport */
            margin: 0; /* Bỏ margin mặc định */
        }
        .form-container {
            font-family: "Arial", sans-serif;
            width: 90%; /* Chiều rộng tối đa 90% */
            max-width: 500px; /* Chiều rộng tối đa là 500px */
            padding: 25px; /* Padding cho form */
            border-radius: 10px; /* Bo góc */
            background-color: #fff; /* Màu nền trắng */
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1); /* Đổ bóng */
        }
        h1 {
            font-family: "Arial", sans-serif;
            color: #0078a5; /* Màu giống như Booksaw */
        }
    </style>
</head>
<body>
<%
    editNovelDAO daoedit = new editNovelDAO();
    ResultSet rsGen = daoedit.NovelGenre();
%>

<div class="form-container">
    <h1 class="text-center">Adding Post</h1>
    <form method="post">
        <div class="mb-3">
            <label for="novel_title" class="form-label">Title:</label>
            <input type="text" class="form-control" id="novel_title" name="novel_title" required="">
        </div>
        <div class="mb-3">
            <label for="author" class="form-label">Author:</label>
            <input type="text" class="form-control" id="author" name="author" required="">
        </div>
        <div class="mb-3">
            <label for="status" class="form-label">Status:</label>
            <input type="text" class="form-control" id="status" name="status">
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Content:</label>
            <textarea class="form-control" id="description" name="description" rows="3"></textarea>
        </div>

        <div class="d-flex justify-content-between mt-4">
            <button type="submit" name="AddNew" class="btn btn-primary">Add New</button>
            <a href="/accountController/account" class="btn btn-secondary">Back</a>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>