<%@page import="java.sql.ResultSet"%>
<%@page import="DAO.novelPageDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"> 
        <style>
            /* ... Các style CSS hiện tại ... */

            .comment-list {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }

            .comment-item {
                padding: 15px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                background-color: #f8f9fa;
                display: flex;
                align-items: center;
            }

            .user-info {
                display: flex;
                align-items: center;
                margin-right: 10px;
            }

            .user-info img {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 5px;
            }

            .comment-content {
                margin-bottom: 10px;
            }

            .comment-container {
                width: 90%;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border: 1px solid #ced4da;
                border-radius: 8px;
            }

            /* Rating Stars Styles */
            .rating-stars {
                color: orange;
                font-size: 16px;
                margin-bottom: 5px;
            }

            .rating-stars i {
                margin-right: 3px;
            }
        </style>
    </head>
    <body>
        <%
            novelPageDAO daoPage = new novelPageDAO();
            String id = (String) session.getAttribute("id");
            ResultSet rsRate = daoPage.getRatingNovel(id);
        %>
        <div class="comments-section">
            <h2>Đánh giá</h2>
            <form method="get" action="/novelPageController/rating/<%=id%>">
            </form> <br>

            <div class="comment-list"> 
                <% while (rsRate.next()) {%>
                <div class="comment-item"> 
                    <div class="user-info">
                        <span class="genre"><%= rsRate.getString("username")%></span>
                        <img src="<%= rsRate.getString("avatar")%>" style="max-width: 30px; max-height: 30px;">
                    </div>
                    <div class="comment-content"> 
                        <div class="rating-stars">
                            <%
                                int rating = rsRate.getInt("rating_number");
                                for (int i = 1; i <= 5; i++) {
                                    if (i <= rating) {
                            %>
                            <i class="fas fa-star filled"></i> 
                            <% } else { %>
                            <i class="far fa-star"></i> 
                            <% }
                                }
                            %>
                        </div>
                        <span><%= rsRate.getString("rating_content")%></span> 
                    </div>
                </div>
                <% }%>
            </div>
        </div>
    </body>
</html>