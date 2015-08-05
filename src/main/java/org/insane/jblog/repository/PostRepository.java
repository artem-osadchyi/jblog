package org.insane.jblog.repository;

import java.util.List;

import org.insane.jblog.domain.Post;

public interface PostRepository {

    Post get(long id);

    List<Post> getAll();

    void delete(long id);

}
