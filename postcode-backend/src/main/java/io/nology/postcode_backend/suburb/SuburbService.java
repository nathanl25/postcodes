package io.nology.postcode_backend.suburb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import io.nology.postcode_backend.common.exceptions.NotFoundException;
import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcode.PostcodeService;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburbService;
import jakarta.validation.Valid;

// import io.nology.postcode_backend.Suburb;

@Service
public class SuburbService {

    private SuburbRepository repo;
    private PostcodeService postcodeService;
    private PostcodeSuburbService postcodeSuburbService;

    SuburbService(SuburbRepository repo, @Lazy PostcodeService postcodeService,
            @Lazy PostcodeSuburbService postcodeSuburbService) {
        this.repo = repo;
        this.postcodeService = postcodeService;
        this.postcodeSuburbService = postcodeSuburbService;
    }

    public List<Suburb> getByIds(Set<Long> suburbIds) throws NotFoundException {
        if (suburbIds == null) {
            // System.out.println("suburb ids null");
            // List<Suburb> list = new ArrayList<>();
            // list.add(null);
            // return list;
            return null;
        }
        List<Suburb> suburbs = this.repo.findAllById(suburbIds);
        if (suburbs.size() != suburbIds.size()) {
            throw new NotFoundException("Matching suburbs not found");
        }
        return suburbs;
    }

    Optional<Suburb> getByName(String name) {
        return this.repo.findOneByName(name);
    }

    public Suburb createSuburb(CreateSuburbDTO data) throws NotFoundException {
        Suburb newSuburb = new Suburb(data.getName());
        if (data.hasPostcodes()) {
            List<Postcode> postcodes = this.postcodeService.getByPostcodes(data.getPostcodes());
            return newSuburb;
        }
        this.repo.saveAndFlush(newSuburb);
        return newSuburb;
    }

}
