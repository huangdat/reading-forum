<%-- 
    Document   : index
    Created on : Jul 10, 2024, 10:07:28 PM
    Author     : LENOVO
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Search Novels</title>
        <style>
            .novel-container {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                justify-content: center;
            }
            .novel {
                border: 1px solid #ccc;
                padding: 10px;
                margin: 10px;
                width: calc(100% / 6 - 20px);
                box-sizing: border-box;
                text-align: center;
                position: relative;
                overflow: hidden;
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .novel img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
            .novel-title {
                display: block;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                width: 100%;
                font-size: 20px;
                text-align: center;
            }
            .pagination {
                text-align: center;
                margin: 20px 0;
            }
            .pagination a {
                margin: 0 5px;
                text-decoration: none;
                border: 1px solid #ccc;
                padding: 5px 10px;
                color: #333;
            }
            .pagination a.active {
                background-color: #ccc;
            }
            .pageTitle {
                font-size: 50px;
                text-align: center;
                margin: 10px 0;
                color: red;
            }
            .search-input {
                font-size: 18px;
                padding: 10px;
                width: 300px;
                margin-right: 10px;
            }
            .search-button {
                font-size: 18px;
                padding: 10px 20px;
            }
            .navBar {
                background-color: black;
                height: 60px;
            }
            .noneDecor {
                text-decoration: none;
            }
            
        </style>
    </head>
    <body>
       
        <h2 class="pageTitle">Tìm truyện</h2>
        <form action="search.jsp" method="get">
            <input type="text" name="keyword" placeholder="Enter keyword" value="${param.keyword}" class="search-input" style="margin-left: 110px;"/>
            <input type="submit" value="Tìm" class="search-button" />
        </form>
        <hr>
        <c:if test="${not empty param.keyword}">
            <sql:setDataSource var="conn"
                               driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                               url="jdbc:sqlserver://LAPTOP-HB63NNNO\\ADMIN:1433;databaseName=NovelOne;user=sa;password=123;encrypt=true;trustServerCertificate=true"
                               user="sa"
                               password="123"/>
            <sql:query var="results" dataSource="${conn}">
                SELECT novel_id, cover_img, novel_title 
                FROM (
                SELECT novel_id, cover_img, novel_title
                FROM Novel 
                WHERE novel_title LIKE ?
                ) AS temp
                <sql:param value="%${param.keyword}%" />
            </sql:query>
            <div class="novel-container">
                <c:forEach var="novel" items="${results.rows}">
                    <div class="novel">
                        <div style="width: 100%; height: 250px;">
                            <a href="/novelPageController/novel_id/${novel.novel_id}"><img src="${novel.cover_img}" alt="${novel.novel_title}" /></a>
                        </div>
                        <a class="noneDecor" href="/novelPageController/novel_id/${novel.novel_id}"><span class="novel-title" title="${novel.novel_title}">${novel.novel_title}</span></a>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </body>
</html>