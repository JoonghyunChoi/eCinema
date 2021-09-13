package ecinema.api;

import ecinema.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/posts")
@RepositoryRestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping
    @ResponseBody
    public HashMap<String, Object> getPosts(@RequestParam(value = "page") int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Page<Post> postList = postService.findAllPost(pageable);

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                pageable.getPageSize(), postList.getNumber(), postList.getTotalElements());

        PagedModel<Post> posts = PagedModel.of(postList.getContent(), pageMetadata);
        posts.add(linkTo(methodOn(PostController.class).getPosts(pageNumber)).withSelfRel());

        HashMap<String, Object> result = new HashMap<>();
        result.put("_embedded", posts);
        result.put("pageIndexes", postService.getPageIndexes(postList.getTotalPages()));

        return result;
    }
}
