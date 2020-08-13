package com.tengshan.blog.web;

import com.tengshan.blog.pojo.Blog;
import com.tengshan.blog.pojo.Comment;
import com.tengshan.blog.pojo.User;
import com.tengshan.blog.service.BlogService;
import com.tengshan.blog.service.CommentService;
import com.tengshan.blog.service.TagService;
import com.tengshan.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    //主页
    @RequestMapping("/")
    public String index(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model, HttpSession session){

        model.addAttribute("page",blogService.listBlog(pageable));
        session.setAttribute("totalBlogNum",blogService.listBlog(pageable).getTotalElements());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("pageByCommentUpdateTime",blogService.commentBlogTop());
        //获取推荐状态文章
        model.addAttribute("recommendBlog",blogService.recommendBlogTop());
        return "index";
    }

    //列表页
    @RequestMapping("/toList")
    public String list(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                       Pageable pageable,Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("tags",tagService.listTagTop(7));
        model.addAttribute("pageByCommentUpdateTime",blogService.commentBlogTop());
        for(int i=0;i<6;i++) {
            System.out.println(tagService.listTagTop(6).get(i).getBlogList().size());
        }
        return "list";
    }

    //详情页
    @RequestMapping("/toShow/{id}")
    public String show(@PathVariable Long id,Model model,HttpSession session){
        Blog blog = blogService.getAndConvert(id);

        model.addAttribute("blog",blog);
        session.setAttribute("blog",blog);
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        model.addAttribute("pageByCommentUpdateTime",blogService.commentBlogTop());

        //新建一个评论对象，传到后端装
        Comment comment = new Comment();
        User user = (User) session.getAttribute("user");
        if(user!=null) {
            comment.setImage(user.getImage());
            comment.setNickname(user.getNickname());
            comment.setPhone(user.getPhone());
        }
        model.addAttribute("commentAdd",comment);

        System.out.println(blog.getTags());
        return "show";
    }

}
