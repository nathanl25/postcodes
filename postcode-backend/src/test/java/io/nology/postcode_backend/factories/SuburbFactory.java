package io.nology.postcode_backend.factories;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import io.nology.postcode_backend.suburb.Suburb;
import io.nology.postcode_backend.suburb.SuburbRepository;

@Component
public class SuburbFactory extends BaseFactory<Suburb> {

    private SuburbRepository suburbRepository;

    public SuburbFactory(Faker faker, SuburbRepository suburbRepository) {
        super(faker);
        this.suburbRepository = suburbRepository;
    }

    @Override
    public Suburb create() {
        Suburb newSuburb = new Suburb();
        newSuburb.setName(faker.address().cityName());
        return newSuburb;
    }

    public Suburb save(Suburb suburb) {
        this.suburbRepository.save(suburb);
        return suburb;
    }
}
