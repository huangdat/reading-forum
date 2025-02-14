<%-- 
    Document   : index
    Created on : Jun 30, 2024, 3:39:59 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
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
            .col-9 form {
                display: flex;
                flex-direction: column;
            }
            .inputTitle{
                width: 850px;
                border-width: 2px;
            }
            .inputContent{
                height: 386px;
                width: 850px;
                overflow-y: auto;
                border-width: 2px;
                white-space: pre-wrap;
            }
            .addNew{
                right: 100px;
                width: 200px;
                border: 3px solid;
                border-radius: 5px;
                margin-top: 10px;
                padding: 10px;
                background-color: dodgerblue;
                font-size: 18px
            }
            .chapters{
                margin-left: 30px;
                font-size: 18px;
                height: 450px;
            }
            .navBar{
                background-color: black;
                height: 60px;
            }
            .menu{
                border-width: 1px;
                overflow: auto;
                height: 500px;
                max-height: 500px;
            }
            .pageTitle{
                font-size: 50px;
                text-align: center;
                margin-top: 10px;
                color: red;
            }
            .noUnderline{
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <a class="btn btn-danger" value="Back" href="/ManageNovelsController"></a>
        <c:set var="novelID" value="${sessionScope.novelId}" />
        <sql:setDataSource var="conn"
                           driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                           url="jdbc:sqlserver://LAPTOP-HB63NNNO\\ADMIN:1433;databaseName=NovelOne;user=sa;password=123;encrypt=true;trustServerCertificate=true"
                           user="sa"
                           password="123"/>
        <sql:query var="chapters" dataSource="${conn}">
            SELECT * FROM Chapter where novel_id = ?
            <sql:param value="${novelID}"/>
        </sql:query>
        <sql:query var="novels" dataSource="${conn}">
            SELECT * FROM Novel where novel_id = ?
            <sql:param value="${novelID}"/>
        </sql:query>
        <div class="navBar">
            aaa
        </div>
        <h2 class="pageTitle">THÊM CHƯƠNG</h2>
        <hr>
        <div class="container" style="margin-top: 10px">
            <div class="row gx-5">
                <div class="col-4">
                    <div class="menu">
                        <c:forEach var="novelVar" items="${novels.rows}">
                            <a href="/ManageNovelsController/${novelID}" class="noUnderline"><h3>${novelVar.novel_title}</h3></a>
                                </c:forEach>
                        <div class="chapters">
                            <c:forEach var="chapterVar" items="${chapters.rows}">
                                <a href="<c:url value='/editChapterController/${novelID}/EditChapter/${chapterVar.chapter_id}' />" class="noUnderline">• ${chapterVar.chapter_title}</a>
                                <br>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="col-8">
                    <form method="post" action="ChapterController">
                        <div>
                            <h3>Tên chương</h3>
                        </div>
                        <input type="text" class="inputTitle" name="txtChapterTitle" required/>
                        <h3>Nội dung</h3>
                        <textarea class="inputContent" name="txtChapterContent" required></textarea>
                        <input type="hidden" name="txtNovelID" value="${novelID}"/>
                        <input type="submit" name="btnCreateChapter" value="Đăng" class="addNew"/>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
