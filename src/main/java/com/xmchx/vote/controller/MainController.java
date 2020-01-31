package com.xmchx.vote.controller;

import com.xmchx.vote.model.Category;
import com.xmchx.vote.payload.PagedResponse;
import com.xmchx.vote.payload.PollResponse;
import com.xmchx.vote.repository.*;
import com.xmchx.vote.service.PollService;
import com.xmchx.vote.util.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/7 8:33
 */
@Controller
@Slf4j
public class MainController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PollService pollService;


    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(@RequestParam(name = "page",
            defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                        Model model) {
        PagedResponse<PollResponse> polls = pollService.getAllPolls(page, size, "createTime",
                "desc");
        model.addAttribute("page", polls);
        model.addAttribute("aHref", "/index?page=");
        return "index";
    }

    @GetMapping("/tags")
    public String tagsIndex(@RequestParam(name = "name", defaultValue = "") String name,
                            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER)
                                    int page,
                            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE)
                                    int size,
                            Model model) {
        PagedResponse<PollResponse> polls = pollService.getAllPollsByTagName(page, size, name);
        model.addAttribute("page", polls);
        String href = "/tags?name=" + name + "&page=";
        model.addAttribute("aHref", href);
        return "index";
    }

    @GetMapping("/categories")
    public String categoriesIndex(@RequestParam(name = "name", defaultValue = "") String name,
                                  @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER)
                                          int page,
                                  @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE)
                                          int size,
                                  Model model) {
        PagedResponse<PollResponse> polls = pollService.getAllPollsByCategoryName(page, size, name);
        model.addAttribute("page", polls);
        String href = "/categories?name=" + name + "&page=";
        model.addAttribute("aHref", href);
        return "index";
    }


    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登录失败!!");
        return "login";
    }

    @GetMapping("/newpoll")
    public String newpoll(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "newpoll";
    }


}
