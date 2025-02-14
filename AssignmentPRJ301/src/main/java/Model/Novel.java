package Model;

import java.sql.Timestamp;

public class Novel {

    private int novelId;
    private String novelTitle;
    private String author;
    private String novelStatus;
    private String coverImg;
    private String description;
    private int view;
    private Timestamp createdAt;

    public Novel() {
    }

    public Novel(int novelId, String novelTitle, String author, String novelStatus, String coverImg, String description, int view, Timestamp createdAt) {
        this.novelId = novelId;
        this.novelTitle = novelTitle;
        this.author = author;
        this.novelStatus = novelStatus;
        this.coverImg = coverImg;
        this.description = description;
        this.view = view;
        this.createdAt = createdAt;
    }

    // Getters and setters for all fields
    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }

    public String getNovelTitle() {
        return novelTitle;
    }

    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNovelStatus() {
        return novelStatus;
    }

    public void setNovelStatus(String novelStatus) {
        this.novelStatus = novelStatus;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
