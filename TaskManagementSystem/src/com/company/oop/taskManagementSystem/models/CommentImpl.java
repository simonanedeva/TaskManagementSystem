package com.company.oop.taskManagementSystem.models;

import com.company.oop.taskManagementSystem.models.contracts.Comment;
import com.company.oop.taskManagementSystem.utils.ValidationHelpers;

import static java.lang.String.format;

public class CommentImpl implements Comment {
    public static final int CONTENT_LEN_MIN = 5;
    public static final int CONTENT_LEN_MAX = 200;
    private static final String CONTENT_LEN_ERR = format(
            "Content must be between %d and %d characters long!",
            CONTENT_LEN_MIN,
            CONTENT_LEN_MAX);

    private String author;
    private String content;

    public CommentImpl(String content, String author) {
        setAuthor(author);
        setContent(content);
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    private void setContent(String content) {
        ValidationHelpers.validateStringLength(content, CONTENT_LEN_MIN, CONTENT_LEN_MAX, CONTENT_LEN_ERR);
        this.content = content;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }
}