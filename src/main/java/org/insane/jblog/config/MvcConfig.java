package org.insane.jblog.config;

import org.insane.jblog.controllers.PostController;
import org.insane.jblog.repository.PostRepository;
import org.insane.jblog.view.jinja2.Jinja2ViewResolver;
import org.insane.jblog.view.jinja2.SpringResourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.loader.ResourceLocator;

@Configuration
@EnableWebMvc
@Import(RepositoryConfig.class)
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public PostController postController(PostRepository postRepository) {
        return new PostController(postRepository);
    }

    @Bean
    public ViewResolver viewResolver() {
        Jinja2ViewResolver resolver = new Jinja2ViewResolver();

        resolver.setCache(true);
        resolver.setPrefix(prefix());
        resolver.setSuffix(".html");

        return resolver;
    }

    public String prefix() {
        return "/WEB-INF/jinja2/";
    }

    @Bean
    public ResourceLocator resourceLocator() {
        return new SpringResourceLocator(prefix());
    }

    @Bean
    public Jinjava jinja2Engine() {
        Jinjava engine = new Jinjava();

        engine.setResourceLocator(resourceLocator());

        return engine;
    }

}
