package org.insane.jblog.config;

import org.hibernate.SessionFactory;
import org.insane.jblog.domain.Post;
import org.insane.jblog.repository.PostRepository;
import org.insane.jblog.repository.impl.HibernatePostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public SessionFactory postSessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(Post.class)
                .buildSessionFactory();
    }

    @Bean
    public PostRepository postRepository() {
        return new HibernatePostRepository(postSessionFactory());
    }

}
