package io.nology.postcode_backend.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

@Component
public abstract class BaseFactory<T> {
    protected final Faker faker;

    @Autowired
    public BaseFactory(Faker faker) {
        this.faker = faker;
    }

    public abstract T create();
}
