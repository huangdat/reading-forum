/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hp
 */
public class Rating {
    String content; 
    String username; 
    String avatar; 
    String novel_id; 
    String rating_id; 
    String rating_number; 
    String userId; 
    
  public Rating() {
    }

    public Rating(String content, String username, String avatar, String novel_id, String rating_id, String rating_number, String userId) {
        this.content = content;
        this.username = username;
        this.avatar = avatar;
        this.novel_id = novel_id;
        this.rating_id = rating_id;
        this.rating_number = rating_number;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNovel_id() {
        return novel_id;
    }

    public void setNovel_id(String novel_id) {
        this.novel_id = novel_id;
    }

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public String getRating_number() {
        return rating_number;
    }

    public void setRating_number(String rating_number) {
        this.rating_number = rating_number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
  
  
    
    
}
