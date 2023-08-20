package com.company.oop.taskManagementSystemTests.models;

import com.company.oop.taskManagementSystem.core.contracts.TMSRepository;
import com.company.oop.taskManagementSystem.models.CommentImpl;
import com.company.oop.taskManagementSystemTests.utils.TestHelpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class CommentImplTests {
    public static final int CONTENT_LEN_MIN = 5;
    public static final int CONTENT_LEN_MAX = 200;
    private static final String CONTENT_LEN_ERR = format(
            "Content must be between %d and %d characters long!",
            CONTENT_LEN_MIN,
            CONTENT_LEN_MAX);

    private String author;
    private String content;


    @Test
    public void should_ThrowException_When_ContentLengthBelowMinimum(){
        content = TestHelpers.getString(CONTENT_LEN_MIN - 1);
        author = "Nikolay";
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CommentImpl(content, author));
    }

    @Test
    public void should_ThrowException_When_ContentLengthAboveMinimum(){
        content = TestHelpers.getString(CONTENT_LEN_MAX + 1);
        author = "Nikolay";
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CommentImpl(content, author));
    }

    @Test
    public void should_CreateComment_When_ValidArguments(){
        content = TestHelpers.getString(CONTENT_LEN_MIN + 1);
        author = "Nikolay";
        CommentImpl comment = new CommentImpl(content, author);
        Assertions.assertEquals(content, comment.getContent());
        Assertions.assertEquals(author, comment.getAuthor());
    }

}
