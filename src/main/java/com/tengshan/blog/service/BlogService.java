package com.tengshan.blog.service;

import com.tengshan.blog.pojo.Blog;
import com.tengshan.blog.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    //保存博客（更新更新时间）
    Blog saveBlog(Blog blog);
    //保存博客（更新评论时间）
    Blog saveBlogByCommentTime(Blog blog);
    //列出所有博客
    List<Blog> listBlog();
    //获取博客跳转
    Blog getAndConvert(Long id);
    //根据id删除博客
    void deleteById(Long id);
    //根据id查询博客
    Blog findById(Long id);
    //更新用户个人信息
    Blog updateBlog(Long id,Blog blog);
    //分页列出所有博客
    Page<Blog> listBlog (Pageable pageable);
    //推荐博客
    List<Blog> recommendBlogTop();
    //评论时间排序博客
    List<Blog> commentBlogTop();
}
