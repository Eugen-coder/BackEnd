package ait.cohort5860.post.dao;

import ait.cohort5860.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorIgnoreCase(String author);
    List<Post> findByTags_NameIn(List<String> tagNames);
    List<Post> findByDateBetween(LocalDate from, LocalDate to);

}
