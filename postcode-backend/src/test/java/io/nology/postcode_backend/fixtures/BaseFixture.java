package io.nology.postcode_backend.fixtures;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.nology.postcode_backend.factories.PostcodeFactory;
import io.nology.postcode_backend.factories.PostcodeSuburbFactory;
import io.nology.postcode_backend.factories.SuburbFactory;
import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcode.PostcodeRepository;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburb;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburbRepository;
import io.nology.postcode_backend.suburb.Suburb;
import io.nology.postcode_backend.suburb.SuburbRepository;

@Component
public abstract class BaseFixture {
    @Autowired
    protected PostcodeRepository postcodeRepository;

    @Autowired
    protected SuburbRepository suburbRepository;

    @Autowired
    protected PostcodeSuburbRepository postcodeSuburbRepository;

    @Autowired
    protected PostcodeFactory postcodeFactory;

    @Autowired
    protected SuburbFactory suburbFactory;

    @Autowired
    protected PostcodeSuburbFactory postcodeSuburbFactory;

    public Postcode createPostcode() {
        Postcode newPostCode = postcodeFactory.create();
        postcodeFactory.save(newPostCode);
        return newPostCode;
    }

    public Suburb createSuburb() {
        Suburb newSuburb = suburbFactory.create();
        suburbFactory.save(newSuburb);
        return newSuburb;
    }

    public PostcodeSuburb createPostcodeSuburb(Postcode p, Suburb s) {
        PostcodeSuburb newPs = new PostcodeSuburb(p, s);
        postcodeSuburbFactory.save(newPs);
        return newPs;
    }

    public List<Postcode> fetchAllPostcodes() {
        return this.postcodeRepository.findAll();
    }

    public List<Suburb> fetchAllSuburbs() {
        return this.suburbRepository.findAll();
    }

    public List<PostcodeSuburb> fetchAllPostcodeSuburbs() {
        return this.postcodeSuburbRepository.findAll();
    }

    @Transactional
    public abstract void setup();

    @Transactional
    public void tearDown() {
        postcodeSuburbRepository.deleteAll();
        postcodeRepository.deleteAll();
        suburbRepository.deleteAll();
    }
}
