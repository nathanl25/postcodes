package io.nology.postcode_backend.postcode;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.nology.postcode_backend.common.exceptions.NotFoundException;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburbService;
import io.nology.postcode_backend.suburb.Suburb;
import io.nology.postcode_backend.suburb.SuburbService;

@Service
public class PostcodeService {
    private PostcodeRepository repo;
    private PostcodeSuburbService postcodeSuburbService;
    private SuburbService suburbService;

    public PostcodeService(PostcodeRepository repo, PostcodeSuburbService postcodeSuburbService,
            SuburbService suburbService) {
        this.repo = repo;
        this.postcodeSuburbService = postcodeSuburbService;
        this.suburbService = suburbService;
    }

    public Postcode createPostcode(CreatePostcodeDTO data) throws NotFoundException {
        Postcode newPostcode = new Postcode(data.getPostcode());
        if (data.hasSuburbIds()) {
            List<Suburb> suburbs = this.suburbService.getByIds(data.getSuburbIds());
            return newPostcode;
        }
        this.repo.saveAndFlush(newPostcode);
        return newPostcode;
    }

    public Optional<Postcode> getById(String id) {
        return this.repo.findById(id);
    }
}
