/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class Chapter {

    private String chapter_id;
    private String chapter_title;
    private String chapter_content;
    private String novel_id;

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getChapter_title() {
        return chapter_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public String getChapter_content() {
        return chapter_content;
    }

    public void setChapter_content(String chapter_content) {
        this.chapter_content = chapter_content;
    }

    public String getNovel_id() {
        return novel_id;
    }

    public void setNovel_id(String novel_id) {
        this.novel_id = novel_id;
    }

    public Chapter() {
    }

    public Chapter(String chapter_title, String chapter_content, String novel_id) {
        this.chapter_title = chapter_title;
        this.chapter_content = chapter_content;
        this.novel_id = novel_id;
    }

    public Chapter(String chapter_id, String chapter_title, String chapter_content, String novel_id) {
        this.chapter_id = chapter_id;
        this.chapter_title = chapter_title;
        this.chapter_content = chapter_content;
        this.novel_id = novel_id;
    }

}
