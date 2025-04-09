package io.nology.postcode_backend.postcodeSuburb;

import org.springframework.stereotype.Service;

@Service
public class PostcodeSuburbService {

    private PostcodeSuburbRepository repo;

    public PostcodeSuburbService(PostcodeSuburbRepository repo) {
        this.repo = repo;
    }
}
