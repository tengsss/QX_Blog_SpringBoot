package com.tengshan.blog.service.impl;

import com.tengshan.blog.dao.CommentRepository;
import com.tengshan.blog.pojo.Comment;
import com.tengshan.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort =Sort.by("createTime");
        List<Comment> commentList =commentRepository.findByBlogId(blogId,sort);
        System.out.println("运行到listCommentByNewId"+commentList);
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment:commentList){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        return commentsView;
    }

    @Override
    public Integer countComment(Long id) {
        System.out.println(id);
        System.out.println(commentRepository.countCommentsByBlogId(id));
        return commentRepository.countCommentsByBlogId(id);
    }

    @Override
    public Long countSumComment() {
        return commentRepository.count();
    }

}
