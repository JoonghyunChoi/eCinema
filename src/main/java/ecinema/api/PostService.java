package ecinema.api;


import ecinema.data.PostRepository;
import ecinema.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public List<Integer> getPageIndexes(int totalPages) {

        List<Integer> pageIndexes = new ArrayList<>();
        for (int i=0; i<totalPages; i++) {
            pageIndexes.add(i);
        }

        return pageIndexes;
    }

    public Post getPostById(Long id) {
        return postRepository.getById(id);
    }

    public Page<Post> findAllPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }


}
