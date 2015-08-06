package org.insane.jblog.controllers;

import org.insane.jblog.domain.Post;
import org.insane.jblog.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

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

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        repository.delete(id);

        return "redirect:/posts";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newPost() {
        return "new_post";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String savePost(@ModelAttribute Post post) {
        // TODO: Figure out whether it has to placed here
        post.setCreationDate(new Date());

        repository.save(post);

        return "redirect:/posts";
    }

}
