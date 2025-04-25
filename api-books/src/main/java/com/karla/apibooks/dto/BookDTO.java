package com.karla.apibooks.dto;

public class BookDTO {

    private Long    id;
    private String  title;
    private String  author;
    private boolean isAvailable;
    private Long    userId;
    private String  userName;
    private String  userEmail;

    public BookDTO() {
    }

    public BookDTO(Long id, String title, String author, boolean isAvailable, Long userId) {
        this.id          = id;
        this.title       = title;
        this.author      = author;
        this.isAvailable = isAvailable;
        this.userId      = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
