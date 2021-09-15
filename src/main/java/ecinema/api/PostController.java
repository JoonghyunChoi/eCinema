package ecinema.api;

import ecinema.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/posts")
@RepositoryRestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping
    @ResponseBody
    public CollectionModel<Object> getPosts(@RequestParam(value = "page") int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Page<Post> posts = postService.findAllPost(pageable);

        List<Integer> pageIndexes = postService.getPageIndexes(posts.getTotalPages());

        List<Object> resultSet = new ArrayList<>();
        resultSet.add(posts);
        resultSet.add(pageIndexes);

        CollectionModel<Object> collectionModel = CollectionModel.of(resultSet);
        collectionModel.add(linkTo(methodOn(PostController.class).getPosts(pageNumber)).withSelfRel());

        return collectionModel;
    }
}
