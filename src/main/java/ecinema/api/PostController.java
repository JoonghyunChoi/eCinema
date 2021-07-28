package ecinema.api;

import ecinema.data.PostRepository;
import ecinema.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/posts")
@RepositoryRestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    @ResponseBody
    public HashMap<String, Object> getPosts(@PageableDefault Pageable pageable) {

        Page<Post> postList = postRepository.findAll(pageable);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                pageable.getPageSize(), postList.getNumber(), postList.getTotalElements());

        PagedModel<Post> posts = PagedModel.of(postList.getContent(), pageMetadata);
        posts.add(linkTo(methodOn(PostController.class).getPosts(pageable)).withSelfRel());

        int totalPages = postList.getTotalPages();
        List<Integer> pageIndexes = new ArrayList<>();
        for (int i=0; i<totalPages; i++ ) {
            pageIndexes.add(i);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("_embedded", posts);
        result.put("pageIndexes", pageIndexes);

        return result;
    }
}
