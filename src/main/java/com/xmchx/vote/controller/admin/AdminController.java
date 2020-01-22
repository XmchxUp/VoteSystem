package com.xmchx.vote.controller.admin;

import com.xmchx.vote.model.*;
import com.xmchx.vote.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/8 18:51
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private CommentRepository commentRepository;


    @RequestMapping
    public String root() {
        return "redirect:/admin/index";
    }


    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String signin(@RequestParam("usernameOrEmail") String usernameOrEmail,
                         @RequestParam("password") String password,
                         @RequestParam("verifyCode") String verifyCode,
                         HttpSession session) {
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }

        if (StringUtils.isEmpty(usernameOrEmail) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "admin/login";
        }

        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (!optionalUser.isPresent()) {
            session.setAttribute("errorMsg", "登陆失败");
            return "admin/login";
        }

        User user = optionalUser.get();
        if (!user.matchesPassword(password)) {
            session.setAttribute("errorMsg", "登陆失败");
            return "admin/login";
        }

        Role role = roleRepository.findByName(RoleName.ROLE_ADMIN).get();
        if (!user.getRoles().contains(role)) {
            session.setAttribute("errorMsg", "没有权限");
            return "admin/login";
        }

        if ("locked".equals(user.getStatus())) {
            session.setAttribute("errorMsg", "用户被封杀");
            return "admin/login";
        }
        session.setAttribute("loginUser", user);
        //session过期时间设置为7200秒 即两小时
        session.setMaxInactiveInterval(60 * 60 * 2);
        return "redirect:/admin/index";
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        long pollCount = pollRepository.count();
        long commentCount = commentRepository.count();
        long categoryCount = categoryRepository.count();
        long tagCount = tagRepository.count();
        request.setAttribute("path", "index");
        request.setAttribute("pollCount", pollCount);
        request.setAttribute("commentCount", commentCount);
        request.setAttribute("categoryCount", categoryCount);
        request.setAttribute("tagCount", tagCount);
        return "admin/index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("path", "users");
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "admin/user";
    }

    @GetMapping("/user/edit")
    public String userEdit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        return "admin/add";
    }

    @GetMapping("/polls")
    public String polls(Model model) {
        model.addAttribute("path", "polls");
        return "admin/poll";
    }

    @GetMapping("/polls/add")
    public String pollAdd(Model model) {
        model.addAttribute("path", "add");
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/add";
    }

    @GetMapping("/polls/edit/{pollId}")
    public String pollEdit(Model model, @PathVariable("pollId") Long pollId) {
        model.addAttribute("path", "edit");
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        Optional<Poll> pollOptional = pollRepository.findById(pollId);
        if (!pollOptional.isPresent()) {
            return "redirect:/admin/polls/add";
        }
        model.addAttribute("poll", pollOptional.get());
        return "admin/edit";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("path", "categories");
        return "admin/category";
    }

    @GetMapping("/tags")
    public String tags(Model model) {
        model.addAttribute("path", "tags");
        return "admin/tag";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }
}
