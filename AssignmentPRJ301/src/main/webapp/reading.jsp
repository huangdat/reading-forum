<%-- 
    Document   : readingChapter
    Created on : Jul 13, 2024, 6:07:24 PM
    Author     : LENOVO
--%>

<%@page import="Model.Chapter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
        <style>
            .middle {
                text-align: center;
                margin-top: 50px;
            }

            .content {
                font-size: 24px;
                white-space: pre-line;
            }

            .content p {
                margin-bottom: 20px;
            }

            /* Thêm CSS cho thanh chức năng */
            .fixed-sidebar {
                position: fixed;
                top: 50%;
                right: 10px;
                transform: translateY(-50%);
                background-color: #f8f9fa;
                border: 1px solid #dee2e6;
                padding: 10px;
                width: 100px; /* Độ rộng của thanh chức năng */
                text-align: center;
                border-radius: 5px;
            }

            .fixed-sidebar .btn {
                display: block;
                width: 100%;
                margin-bottom: 5px;
            }
        </style>
    </head>
    <body>
        <c:set var="chapterID" value="${sessionScope.chapterId}" />
        <c:set var="novelID" value="${sessionScope.novelId}" />
        <sql:setDataSource var="conn"
                           driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                           url="jdbc:sqlserver://LAPTOP-HB63NNNO\\ADMIN:1433;databaseName=NovelOne;user=sa;password=123;encrypt=true;trustServerCertificate=true"
                           user="sa"
                           password="123"/>
        <sql:query var="chapters" dataSource="${conn}">
            SELECT * FROM Chapter where chapter_id = ?
            <sql:param value="${chapterID}"/>
        </sql:query>
        <c:forEach var="chapterVar" items="${chapters.rows}">
            <div class="container">
                <div class="row">
                    <div class="col-1">
                    </div>
                    <div class="col-10">
                        <h1 class="middle">${chapterVar.chapter_title}</h1>
                        <div class="content">
                            <p>${chapterVar.chapter_content}</p>
                        </div>
                    </div>
                    <div class="col-1">
                    </div>
                </div>
            </div>
        </c:forEach>
    </body>
</html>


