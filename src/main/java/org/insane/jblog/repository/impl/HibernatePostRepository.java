package org.insane.jblog.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.insane.jblog.domain.Post;
import org.insane.jblog.repository.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernatePostRepository implements PostRepository {
    private final SessionFactory postSessionFactory;

    public HibernatePostRepository(SessionFactory postSessionFactory) {
        this.postSessionFactory = postSessionFactory;
    }

    @Override
    public Post get(long id) {
        try (Session session = postSessionFactory.openSession()) {
            return session.get(Post.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Post> getAll() {
        try (Session session = postSessionFactory.openSession()) {
            return session.createCriteria(Post.class).list();
        }
    }

}
