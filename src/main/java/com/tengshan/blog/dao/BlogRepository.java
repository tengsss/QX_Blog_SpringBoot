package com.tengshan.blog.dao;

import com.tengshan.blog.pojo.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> {

    //找到推荐状态下最新更新的
    @Query("select  b from  Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    //找到评论时间排序的博客
    @Query("select b from Blog b")
    List<Blog> findTopByCommentUpdateTime(Pageable pageable);
}
