package com.tengshan.blog.service;

import com.tengshan.blog.pojo.Comment;

import java.util.List;

public interface CommentService {
    //发表评论
    Comment saveComment(Comment comment);
    //展示所有评论
    List<Comment> listCommentByBlogId(Long blogId);

    //计算所有评论数
    Integer countComment(Long id);
    //计算所有评论
    Long countSumComment();
}
