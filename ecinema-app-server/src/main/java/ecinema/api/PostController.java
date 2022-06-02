package ecinema.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.hateoas.CollectionModel;
import lombok.RequiredArgsConstructor;
import ecinema.domain.Post;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/posts")
@RepositoryRestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    @ResponseBody
    public CollectionModel<Object> getPosts(@RequestParam(value="page") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Page<Post> posts = postService.findAllPost(pageable);
        List<Integer> pageIndexes = postService.getPageIndexes(posts.getTotalPages());

        List<Object> postSet = new ArrayList<>();
        postSet.add(posts);
        postSet.add(pageIndexes);

        CollectionModel<Object> collectionModel = CollectionModel.of(postSet);
        collectionModel.add(linkTo(methodOn(PostController.class).getPosts(pageNumber)).withSelfRel());
        return collectionModel;
    }
}