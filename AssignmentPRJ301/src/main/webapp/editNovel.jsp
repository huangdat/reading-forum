<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sửa Truyện</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            body {
                background-color: #f8f9fa;
            }
            .pageTitle {
                font-size: 2.5rem;
                text-align: center;
                margin-top: 20px;
                color: #dc3545;
            }
            .noUnderline {
                text-decoration: none;
                color: inherit;
            }
            .list-group-item {
                border: none;
                padding: 0.5rem 1rem;
                transition: background-color 0.3s;
            }
            .list-group-item:hover {
                background-color: #f0f0f0;
            }
            .chapters {
                margin-left: 20px;
            }
            .create {
                background-color: forestgreen;
                color: white;
                border: none;
            }
            .container {
                margin-top: 40px;
            }
            .btn-back {
                margin-bottom: 20px;
            }
            hr {
                margin-bottom: 40px;
            }
        </style>
    </head>
    <body>
        <a class="btn btn-danger" value="Back" href="/ManageNovelsController"></a>
        <div class="container">
            <a class="btn btn-danger btn-back" href="/ManageNovelsController">Quay lại</a>

            <c:set var="novelID" value="${sessionScope.novelId}" />
            <sql:setDataSource var="conn"
                               driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                               url="jdbc:sqlserver://LAPTOP-HB63NNNO\\ADMIN:1433;databaseName=NovelOne;encrypt=true;trustServerCertificate=true"
                               user="sa"
                               password="123" />
            <sql:query var="chapters" dataSource="${conn}">
                SELECT * FROM Chapter WHERE novel_id = ?
                <sql:param value="${novelID}" />
            </sql:query>
            <sql:query var="novels" dataSource="${conn}">
                SELECT * FROM Novel WHERE novel_id = ?
                <sql:param value="${novelID}" />
            </sql:query>
            <sql:query var="genres" dataSource="${conn}">
                SELECT * FROM Genre ORDER BY genre_name ASC;
            </sql:query>
            <sql:query var="NovelGenres" dataSource="${conn}">
                SELECT * FROM NovelGenre WHERE novel_id = ?;
                <sql:param value="${novelID}" />
            </sql:query>

            <h2 class="pageTitle">SỬA TRUYỆN</h2>
            <hr>
            <div class="row">
                <div class="col-md-4">
                    <div class="list-group">
                        <c:forEach var="novelVar" items="${novels.rows}">
                            <h2>
                                <a href="/ManageNovelsController/${novelID}" class="list-group-item list-group-item-action noUnderline">
                                    ${novelVar.novel_title}
                                </a>
                            </h2>
                        </c:forEach>
                    </div>
                    <div class="mt-3 chapters">
                        <ul class="list-unstyled">
                            <c:forEach var="chapterVar" items="${chapters.rows}">
                                <li>
                                    <a href="<c:url value='/editChapterController/${novelID}/EditChapter/${chapterVar.chapter_id}' />" class="noUnderline">
                                        • ${chapterVar.chapter_title}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <a class="btn create mt-3" href="/editChapterController/${novelID}/CreateChapter">Thêm chương</a>
                </div>
                <div class="col-md-8">
                    <!-- Additional content for editing the novel can go here -->
                </div>
            </div>
        </div>
    </body>
</html>
