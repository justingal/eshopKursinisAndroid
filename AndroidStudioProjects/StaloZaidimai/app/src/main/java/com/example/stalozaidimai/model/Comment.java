package com.example.stalozaidimai.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Comment {


    private int id;
    private String commentTitle;
    private String commentBody;
    private LocalDate dateCreated;

    private List<Comment> replies = new ArrayList<>();

    private Comment parentComment;

    public Comment() {
    }

    public Comment(int id, String commentTitle, String commentBody, LocalDate dateCreated, List<Comment> replies, Comment parentComment) {
        this.id = id;
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = dateCreated;
        this.replies = replies;
        this.parentComment = parentComment;

    }
    public Comment(String commentTitle, String commentBody ) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = LocalDate.now();
        this.replies = new ArrayList<>();
    }

    public Comment(String commentTitle, String commentBody, Comment parentComment) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = LocalDate.now();
        this.parentComment = parentComment;
        this.replies = new ArrayList<>();
    }

    public Comment(int id, String commentTitle, String commentBody) {
        this.id = id;
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;

    }


}