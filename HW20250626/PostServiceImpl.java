package ait.cohort5860.post.service;

import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dao.TagRepositiry;
import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exception.PostNotFoundException;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final TagRepositiry tagRepositiry;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(),  author);

        // Handle tags
        Set<String> tags = newPostDto.getTags();
        if(tags != null){
            for(String tagName : tags){
                Tag tag = tagRepositiry.findById(tagName).orElseGet(()-> tagRepositiry.save(new Tag(tagName)));
                post.addTag(tag);
            }
        }
        post =  postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    public void addLike(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }

    @Override
    @Transactional
    public PostDto updatePost(Long id, NewPostDto newPostDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        post.setTitle(newPostDto.getTitle());
        post.setContent(newPostDto.getContent());

        // Обновим теги
        post.clearTags();

        // предполагается метод удаления всех тегов
        Set<String> tags = newPostDto.getTags();
        if (tags != null) {
            for (String tagName : tags) {
                Tag tag = tagRepositiry.findById(tagName).orElseGet(() -> tagRepositiry.save(new Tag(tagName)));
                post.addTag(tag);
            }
        }

        return modelMapper.map(postRepository.save(post), PostDto.class);
    }


    @Override
    @Transactional
    public PostDto deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    public PostDto addComment(Long id, String author, NewCommentDto newCommentDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        post.addComment(author, newCommentDto.getContent()); // предполагается метод addComment
        return modelMapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    public Iterable<PostDto> findPostsByAuthor(String author) {
        return postRepository.findByAuthorIgnoreCase(author)
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    public Iterable<PostDto> findPostsByTags(List<String> tags) {
        return postRepository.findByTags_NameIn(tags) // предположим, что имя поля — name
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    public Iterable<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return postRepository.findByDateBetween(dateFrom, dateTo)
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

}
