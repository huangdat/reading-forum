<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Model.novelInfor"%>
<%@page import="DAO.novelPageDAO"%>
<%@page import="DAO.mainWedDao"%>
<%@page import="DAO.editNovelDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Novel</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <style>
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                background-color: #f5f5f5; /* Màu nền nhạt */
            }

            .form-container {
                width: 60%;
                max-width: 600px;
                padding: 30px;
                border-radius: 10px;
                background-color: #fff;
                box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
            }

            .form-container h1 {
                color: red;
                font-family: cursive;

                text-shadow:
                    -1px -1px 0 #000,
                    1px -1px 0 #000,
                    -1px  1px 0 #000,
                    1px  1px 0 #000;
            }


            .form-group label {
                font-weight: 500;
            }

            .form-control, .select2-selection {
                border-radius: 5px;
            }

            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
                border-radius: 5px;
            }

            .btn-primary:hover {
                background-color: #0069d9;
                border-color: #0062cc;
            }
        </style>
    </head>

    <body>
        <c:set var="novelID" value="${sessionScope.novelId}" />
        <%
            String id = (String) session.getAttribute("novelId");
            System.out.println("update" + id);
            editNovelDAO daoedit = new editNovelDAO();
            novelPageDAO daoPage = new novelPageDAO();
            novelInfor obj = (novelInfor) session.getAttribute("obj");
//            ResultSet rsUpdateGen = daoedit.NovelGenreByid(id);
            ResultSet rsGen = daoedit.NovelGenre();
            //  Lưu danh sách thể loại của novel vào một List
            List<String> selectedGenreIds = new ArrayList<>();
            ResultSet rsUpdateGen = daoedit.NovelGenreByid(id);
            while (rsUpdateGen.next()) {
                selectedGenreIds.add(rsUpdateGen.getString("genre_id"));
            }
            System.out.println(selectedGenreIds.size());
            rsUpdateGen.close(); //  Đóng ResultSet

        %>

        <div class="form-container">
            <h1>sửa TRUYỆN</h1>
            <form method="post" action="ManageNovelsController"  >
                <div class="form-group">
                    <label for="novel_title">Novel Title:</label>
                    <input type="text" class="form-control" id="novel_title" name="novel_title" value="<%= obj.getNovel_title()%>" required /> 
                </div>
                <div class="form-group">
                    <label for="author">Author:</label>
                    <input type="text" class="form-control" id="author"   name="author" value="<%= obj.getAuthor()%>" required /> 
                </div>               
                <div class="form-group">
                    <label for="status">Status:</label>
                    <input type="text" class="form-control" id="status"  required=""  value="<%= obj.getStatus()%>" name="status" /> 
                </div>
                <div class="form-group">
                    <label for="cover_img">Cover Image:</label>
                    <input type="file" class="form-control-file" id="cover_img"  required="" name="cover_img"  /> 
                </div>
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" id="description" name="description" rows="3" required=""  ></textarea>
                </div>

                <script>
                    document.getElementById("description").value = "<%= obj.getDescription()%>";
                </script>


                <div class="form-group">
                    <label for="genre_ids">Genres:</label>
                    <select class="form-control" id="genre_ids" name="genre_ids"  required="" multiple>
                        <%
                            while (rsGen.next()) {
                                String genre_id = rsGen.getString("genre_id");
                                String genre_name = rsGen.getString("genre_name");

                                // Kiểm tra xem thể loại này có trong danh sách selectedGenreIds hay không
                                boolean isSelected = selectedGenreIds.contains(genre_id);
                        %>
                        <option value="<%= genre_id%>" <%= isSelected ? "selected" : ""%>><%= genre_name%></option> 
                        <%
                            }
                        %>
                    </select>
                </div>
                <a class="btn btn-danger mt-3" href="/editNovelController/EditNovel/${novelID}">quay lại</a>

                <button type="submit"  name="btnUpdate" class="btn btn-primary mt-3">Save</button>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script> 
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script>
                    $(document).ready(function () {
                        $('#genre_ids').select2();

                        //  Chọn trước các option đã được chọn
                        $('#genre_ids').val(selectedGenreIds).trigger('change');
                    });
        </script>


    </body>
</html>