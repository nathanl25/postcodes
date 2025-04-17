package io.nology.postcode_backend.factories;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburb;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburbRepository;
import io.nology.postcode_backend.suburb.Suburb;

@Component
public class PostcodeSuburbFactory extends BaseFactory<PostcodeSuburb> {

    private PostcodeSuburbRepository postcodeSuburbRepository;

    public PostcodeSuburbFactory(Faker faker, PostcodeSuburbRepository postcodeSuburbRepository) {
        super(faker);
        this.postcodeSuburbRepository = postcodeSuburbRepository;
    }

    @Override
    public PostcodeSuburb create() {
        PostcodeSuburb newPostcodeSuburb = new PostcodeSuburb();
        return newPostcodeSuburb;
    }

    public PostcodeSuburb create(Postcode p, Suburb s) {
        PostcodeSuburb newPostcodeSuburb = new PostcodeSuburb(p, s);
        return newPostcodeSuburb;
    }

    public PostcodeSuburb save(PostcodeSuburb ps) {
        this.postcodeSuburbRepository.save(ps);
        return ps;
    }

}
