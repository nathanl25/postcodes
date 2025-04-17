package io.nology.postcode_backend.factories;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcode.PostcodeRepository;

@Component
public class PostcodeFactory extends BaseFactory<Postcode> {

    private PostcodeRepository postcodeRepository;

    public PostcodeFactory(Faker faker, PostcodeRepository postcodeRepository) {
        super(faker);
        this.postcodeRepository = postcodeRepository;
    }

    @Override
    public Postcode create() {
        Postcode newPostcode = new Postcode();
        newPostcode.setPostcode(faker.number().digits(4));
        return newPostcode;
    }

    public Postcode save(Postcode postcode) {
        this.postcodeRepository.save(postcode);
        return postcode;
    }
}
