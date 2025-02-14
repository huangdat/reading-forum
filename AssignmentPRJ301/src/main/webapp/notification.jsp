<%@page import="DAO.NotificationDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAOs.NotificationDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Thông báo của bạn</title>

        <style>
            body {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                height: 100vh;
                margin: 0;
                background-color: #f5f5f5;
            }
            h1 {
                margin-bottom: 20px;
            }
            .List {
                background-color: #f0f8ff;
                overflow-y: scroll;
                width: 80%;
                height: 400px;
                display: flex;
                flex-direction: column;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                padding: 10px;
            }
            .listOfNotifications {
                height: 100px;
                background-color: #e0f7fa;
                display: flex;
                margin-bottom: 10px;
                font-weight: normal;
                border-radius: 5px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                transition: background-color 0.3s;
            }
            .listOfNotifications.unread {
                color: black;
                font-weight: 500;
                background-color: #b2ebf2;
            }
            .listOfNotifications:hover {
                background-color: #b2ebf2;
            }
            .Novel_img {
                background-color: lightgreen;
                height: 100%;
                width: 20%;
                border-radius: 5px 0 0 5px;
                overflow: hidden;
            }
            img {
                background-size: cover;
                width: 100%;
                height: 100%;
                object-fit: fill;
            }
            .Novel_description {
                background-color: #ffe0b2;
                width: 80%;
                height: 100%;
                position: relative;
                border-radius: 0 5px 5px 0;
                padding: 10px;
            }
            .checkbox {
                position: absolute;
                top: 10px;
                right: 10px;
            }
            a:hover {
                text-decoration: none;
                color: black;
            }
        </style>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
    </head>

    <body>
        <h1>Thông báo của bạn</h1>

        <div class="List">
            <%
                NotificationDAO dao = new NotificationDAO();
                ResultSet rs = dao.getAllNovelByID(6);
                while (rs.next()) {
                    boolean is_read = rs.getBoolean("is_read");
                    String divClass = is_read ? "listOfNotifications" : "listOfNotifications unread";
            %>

            <div class="<%= divClass %>" href="#">
                <div class="Novel_img">
                    <img alt="#" />
                </div>
                <div class="Novel_description">
                    <a><%= rs.getString("novel_title") %> vừa cập nhật cốt truyện mới</a>
                    <p>Nội dung: <%= rs.getString("content") %></p>
                    <p>Tác giả: <%= rs.getString("author") %></p>
                    <a class="checkbox" href="/NotificationController/CheckRead/?id=<%= rs.getInt("notification_id") %>">Đánh dấu đã đọc</a>
                </div>
            </div>
            <% 
                }
            %>
        </div>
    </body>
</html>
