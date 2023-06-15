package com.example.firebaze1920;

public class Notes {

    private String title;
    private String content;
    private String email;

    public Notes(String title, String content, String email) {
        this.title = title;
        this.content = content;
        this.email = email;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
