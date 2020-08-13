package com.tengshan.blog.web.admin;

import com.tengshan.blog.pojo.User;
import com.tengshan.blog.service.BlogService;
import com.tengshan.blog.service.CommentService;
import com.tengshan.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class ManageController {
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;

    //后台主页
    @RequestMapping("/manage")
    public String toManage(Model model,Pageable pageable,HttpSession session){
        model.addAttribute("totalUser",userService.listUser(pageable).getTotalElements());
        model.addAttribute("totalBlog",blogService.listBlog(pageable).getTotalElements());
        model.addAttribute("totalComment",commentService.countSumComment());
        model.addAttribute("user",session.getAttribute("user")); //添加当前用户信息
        System.out.println(model.getAttribute("user"));
        return "admin/manageIndex";
    }

    //后台用户展示界面
    @RequestMapping("/userList")
    public String showUsers(@PageableDefault(size = 50,sort = {"updateTime"},direction = Sort.Direction.DESC)
                            Pageable pageable, Model model){
        model.addAttribute("page",userService.listUser(pageable));

        return "admin/userList";
    }

    //后台编辑用户信息
    @GetMapping("/userChange/{id}/toUpdate")
    public String toUpdate(@PathVariable Long id,Model model){
        User user = userService.getUserInfo(id);
        model.addAttribute("updateUser", user);
        return "admin/userChange";
    }

    //后台添加用户信息
    @GetMapping("/userAdd")
    public String Add(Model model){
        System.out.println("此时为新增");
        model.addAttribute("addUser",new User());
        return "admin/userAdd";
    }

    //保存修改后的用户信息
    @PostMapping("/user/save")
    public String save(User user, RedirectAttributes attributes, HttpSession session){
        System.out.println("接收到的用户信息"+user);
        User user1;
        if(user.getId()==null){ //没有找到该用户  为新增
            userService.register(user);
            System.out.println("此时执行新增用户");
            attributes.addFlashAttribute("message","新增操作成功！");
        }else{
            user1 = userService.updateUser(user.getId(),user);
            session.setAttribute("updateUser",user1);
            System.out.println("此时执行修改用户");
            attributes.addFlashAttribute("message","编辑操作成功！");
        }
        return "redirect:/admin/userList";
    }

    //后台删除用户信息
    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id,RedirectAttributes attributes){
        System.out.println("待删除的id"+id);
        userService.deleteByUserId(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/userList";
    }

    //后台用户搜索功能
    @RequestMapping("/search")
    public String Search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                         @RequestParam String query,Model model){
        System.out.println(query);
        model.addAttribute("page",userService.listUser("%"+query+"%",pageable));
        System.out.println(model.getAttribute("page"));
        model.addAttribute("query",query);
        return "admin/userList";
    }


}
