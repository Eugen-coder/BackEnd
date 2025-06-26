package ait.cohort5860.post.controller.HW20250625;

import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto addNewPost(@RequestParam String author, @RequestBody NewPostDto newPostDto) {
        return postService.addNewPost(author, newPostDto);
    }

    @GetMapping("/posts/{id}")
    public PostDto findPostById(@PathVariable Long id) {
        return postService.findPostById(id);
    }

    @PostMapping("/posts/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable Long id) {
        postService.addLike(id);
    }

    @PutMapping("/posts/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody NewPostDto newPostDto) {
        return postService.updatePost(id, newPostDto);
    }

    @DeleteMapping("/posts/{id}")
    public PostDto deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

    @PostMapping("/posts/{id}/comments")
    public PostDto addComment(@PathVariable Long id,
                              @RequestParam String author,
                              @RequestBody NewCommentDto newCommentDto) {
        return postService.addComment(id, author, newCommentDto);
    }

    @GetMapping("/posts/byAuthor")
    public Iterable<PostDto> findPostsByAuthor(@RequestParam String author) {
        return postService.findPostsByAuthor(author);
    }

    @GetMapping("/posts/byTags")
    public Iterable<PostDto> findPostsByTags(@RequestParam List<String> tags) {
        return postService.findPostsByTags(tags);
    }

    @GetMapping("/posts/byPeriod")
    public Iterable<PostDto> findPostsByPeriod(@RequestParam LocalDate dateFrom,
                                               @RequestParam LocalDate dateTo) {
        return postService.findPostsByPeriod(dateFrom, dateTo);
    }
}