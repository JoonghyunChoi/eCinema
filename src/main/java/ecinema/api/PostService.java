package ecinema.api;


import ecinema.data.PostRepository;
import ecinema.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


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
