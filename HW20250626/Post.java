package ait.cohort5860.post.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private String author;
    private LocalDateTime dateCreated = LocalDateTime.now();
    @ManyToMany
    private Set<Tag> tags = new HashSet<>();
    private int likes;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Post(String title, String content, String author, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags.addAll(tags.stream().map(Tag::new).toList());
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addLikes() {
        likes++;
    }

    public boolean addTag(Tag tag) {
        this.tags.add(tag);
        return false;
    }

    public void addComment(String author, Object content) {
        this.comments.add(new Comment(author, (String) content));

    }

    public void setLikes(int i) {
    }

    public void clearTags() {
        this.tags.clear();

    }
}

