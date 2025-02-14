<%@page import="DAO.accountDAO" %>
<%@page import="Model.favorite" %>
<%@page import="DAO.editNovelDAO" %>
<%@page import="DAO.novelPageDAO" %>
<%@page import="java.sql.ResultSet" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${novel.novel_title}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
        }

        .cover img {
            width: 100%;
            max-width: 300px;
            height: 400px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .rating label {
            font-size: 30px;
            color: #ccc;
            cursor: pointer;
            transition: color 0.3s;
        }

        .rating label:hover,
        .rating label:hover ~ label,
        .rating input[type="radio"]:checked ~ label {
            color: orange;
        }

        .rating input[type="radio"]:checked ~ label,
        .rating input[type="radio"]:checked ~ label ~ label {
            color: orange;
        }

        .like-button.active {
            background-color: #ffcccb;
            color: #ff0000;
        }
    </style>
</head>
<body class="bg-light">

<%
    novelPageDAO dao = new novelPageDAO();
    favorite fav = (favorite) session.getAttribute("favorite");
    ResultSet rsUser = dao.getUser((String) session.getAttribute("user"));
    ResultSet rs = dao.getNovelById(Integer.toString(fav.getNovel_id()));
    ResultSet rsG = dao.getNovelGenre(Integer.toString(fav.getNovel_id()));
    ResultSet rsChap = dao.getChapterById(Integer.toString(fav.getNovel_id()));
    ResultSet rsCmt = dao.getCmtNovel(Integer.toString(fav.getNovel_id()));
    if (rs.next()) {
%>
<div class="container mt-5">
    <a href="/LoginServlet" class="btn btn-primary mb-3"><h3>&larr; Back to main page</h3></a>

    <div class="row">
        <div class="col-md-12">
            <div class="card mb-3">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="<%= rs.getString("cover_img")%>" class="card-img"
                             alt="<%= rs.getString("novel_title")%>">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title"><%= rs.getString("novel_title")%>
                            </h5>
                            <p class="card-text">Author: <%= rs.getString("author")%>
                            </p>
                            <p class="card-text">Language: <%= rs.getString("novel_status")%>
                            </p>
                            <div class="genres">
                                <% while (rsG.next()) { %>
                                <span class="badge badge-secondary"><%= rsG.getString("genre_name")%></span>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">Content:</h5>
                    <p class="card-text"><%= rs.getString("description")%>
                    </p>
                </div>
            </div>
            <div class="d-flex justify-content-center mb-3">
                <form action="/PostServlet/favorite/<%= fav.getNovel_id() %>" class="d-flex gap-2">
                    <% if (session.getAttribute("user") != null) { %>
                    <button value="<%= session.getAttribute("like") %>" class="btn btn-outline-danger like-button">
                        <i class="fa fa-heart"></i>
                        <span class="count"><%= session.getAttribute("favoriteCount") %></span>
                    </button>
                    <% } else { %>
                    <button disabled value="<%= session.getAttribute("like") %>"
                            class="btn btn-outline-danger like-button">
                        <i class="fa fa-heart"></i>
                        <span class="count"><%= session.getAttribute("favoriteCount") %></span>
                    </button>
                    <% } %>
                    <button class="btn btn-outline-secondary comments">
                        <i class="fa fa-comment"></i> <span class="count"><%= session.getAttribute("TotalCmt") %></span>
                    </button>
                </form>
                <button name="rating" class="btn btn-outline-warning rating">
                    <i class="fa fa-star"></i><span class="average"><%= session.getAttribute("AvgRating") %></span>
                </button>
            </div>
        </div>
    </div>

    <div class="card mb-3">
        <div class="card-body">
            <h5 class="card-title">Rating</h5>
            <% if (session.getAttribute("user") != null) { %>
            <form method="get" action="/PostServlet/rating/<%= fav.getNovel_id() %>">
                <div class="rating">
                    <input type="radio" id="star1" name="rating" value="1"/>
                    <label for="star1" title="text"></label>
                    <input type="radio" id="star2" name="rating" value="2"/>
                    <label for="star2" title="text"></label>
                    <input type="radio" id="star3" name="rating" value="3"/>
                    <label for="star3" title="text"></label>
                    <input type="radio" id="star4" name="rating" value="4"/>
                    <label for="star4" title="text"></label>
                    <input type="radio" id="star5" name="rating" value="5"/>
                    <label for="star5" title="text"></label>
                </div>
                <textarea class="form-control mt-2" id="comment-content" name="comment_rating"
                          placeholder="Leave your rating here"></textarea>
                <button id="submit-rating-btn" type="submit" class="btn btn-primary mt-2">Submit rating</button>
            </form>
            <% } %>
        </div>
    </div>

    <div class="card mb-3">
        <div class="card-body">
            <h5 class="card-title">Comment</h5>
            <% if (session.getAttribute("user") != null) { %>
            <div class="d-flex mb-3">
                <div class="user-info d-flex align-items-center mr-3">
                    <% while (rsUser.next()) { %>
                    <img src="<%= rsUser.getString("avatar") %>" class="avatar mr-2">
                    <span class="badge badge-primary"><%= rsUser.getString("username") %></span>
                    <% } %>
                </div>
                <form method="get" action="/PostServlet/comment/<%= fav.getNovel_id() %>" class="flex-grow-1">
                    <textarea id="comment-content" name="cmt" class="form-control mb-2"></textarea>
                    <button type="submit" class="btn btn-primary">Send comment</button>
                </form>
            </div>
            <% } %>
            <div class="d-flex justify-content-center mb-3">
                <button name="btnRating" id="showRatingBtn" class="btn btn-outline-primary mr-2">Rating</button>
                <button name="btnCmt" id="showCommentBtn" class="btn btn-outline-primary">Comment</button>
            </div>
            <div id="rating-section" style="display: none;">
                <jsp:include page="ratingNovel.jsp"/>
            </div>
            <div class="comment-list">
                <% while (rsCmt.next()) { %>
                <div class="comment-item d-flex align-items-center mb-2 p-2 border rounded bg-white">
                    <div class="user-info d-flex align-items-center mr-2">
                        <span class="badge badge-secondary mr-2"><%= rsCmt.getString("username") %></span>
                        <img src="<%= rsCmt.getString("avatar") %>" class="avatar">
                    </div>
                    <span class="comment-content"><%= rsCmt.getString("comment_content") %></span>
                </div>
                <% } %>
            </div>
        </div>
    </div>
</div>
<%
    }
%>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const showRatingBtn = document.getElementById("showRatingBtn");
        const showCommentBtn = document.getElementById("showCommentBtn");
        const ratingSection = document.getElementById("rating-section");

        if (showRatingBtn) {
            showRatingBtn.addEventListener("click", () => {
                ratingSection.style.display = "block";
            });
        }

        if (showCommentBtn) {
            showCommentBtn.addEventListener("click", () => {
                ratingSection.style.display = "none";
            });
        }
    });

    document.addEventListener("DOMContentLoaded", function () {
        const ratingStars = document.querySelectorAll('.rating input[type="radio"]');
        const submitRatingBtn = document.getElementById('submit-rating-btn');

        ratingStars.forEach(star => {
            star.addEventListener('click', () => {
                ratingStars.forEach(s => s.parentNode.classList.remove('checked'));
                Array.from(ratingStars).slice(0, Array.from(ratingStars).indexOf(star) + 1)
                    .forEach(s => s.parentNode.classList.add('checked'));
            });
        });

        submitRatingBtn.addEventListener('click', () => {
            const selectedRating = document.querySelector('.rating input[type="radio"]:checked');
            const commentContent = document.getElementById('comment-content').value;

            if (selectedRating) {
                const ratingValue = selectedRating.value;
            } else {
                alert('Please select a rating!');
            }
        });
    });

    document.addEventListener('DOMContentLoaded', function () {
        const likeButton = document.querySelector('.like-button');

        likeButton.addEventListener('click', function () {
            this.classList.toggle('active');
        });
    });
</script>
</body>
</html>