package org.insane.jblog.controllers;

import org.insane.jblog.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @RequestMapping
    public String getAll(Model model) {
        model.addAttribute("posts", repository.getAll());

        return "feed";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getPost(@PathVariable long id, Model model) {
        model.addAttribute("post", repository.get(id));

        return "post";
    }

}
