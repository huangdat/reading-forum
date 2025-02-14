/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hp
 */
public class novelGenre {
    int novel_id;
     String [] genre_id; 

    public novelGenre() {
    }

    public novelGenre(int novel_id, String[] genre_id) {
        this.novel_id = novel_id;
        this.genre_id = genre_id;
    }

    public int getNovel_id() {
        return novel_id;
    }

    public void setNovel_id(int novel_id) {
        this.novel_id = novel_id;
    }

    public String[] getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(String[] genre_id) {
        this.genre_id = genre_id;
    }

    

   
    
}
