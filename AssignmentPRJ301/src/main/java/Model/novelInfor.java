/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hp
 */
public class novelInfor {

    String novel_title;
    String author;
    String illustrator;
    String status;
    String cover_img;
    String description;
    String favourite;
    String view;

    public novelInfor(String novel_title, String author, String status, String description, String view) {
        this.novel_title = novel_title;
        this.author = author;
        this.status = status;
        this.description = description;
        this.view = view;
    }

    public novelInfor(String novel_title, String author, String illustrator, String status, String cover_img, String description) {

        this.novel_title = novel_title;
        this.author = author;
        this.illustrator = illustrator;
        this.status = status;
        this.cover_img = cover_img;
        this.description = description;
    }

    public novelInfor() {
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    
    
    public String getNovel_title() {
        return novel_title;
    }

    public void setNovel_title(String novel_title) {
        this.novel_title = novel_title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
