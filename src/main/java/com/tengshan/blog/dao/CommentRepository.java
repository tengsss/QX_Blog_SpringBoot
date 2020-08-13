package com.tengshan.blog.dao;

import com.tengshan.blog.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogId(Long id, Sort sort);

    //查当前博客下有多少评论
    @Query("select count(c) from Comment c where blog_id = ?1")
    Integer countCommentsByBlogId(Long id);
}
