package io.nology.postcode_backend.fixtures;

import org.springframework.stereotype.Component;

import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburb;
import io.nology.postcode_backend.suburb.Suburb;

@Component
public class PostcodeFixture extends BaseFixture {

    private Postcode postcodeWithSuburb;
    private Postcode postcodeWithoutSuburb;
    private Suburb suburb;
    private PostcodeSuburb postcodeWithSuburbEntry;
    private PostcodeSuburb postcodeWithoutSuburbEntry;

    @Override
    public void setup() {
        postcodeWithSuburb = createPostcode();
        postcodeWithoutSuburb = createPostcode();
        suburb = createSuburb();
        postcodeWithSuburbEntry = createPostcodeSuburb(postcodeWithSuburb, suburb);
        postcodeWithoutSuburbEntry = createPostcodeSuburb(postcodeWithoutSuburb, null);
    }

    public Postcode getPostcodeWithSuburb() {
        return postcodeWithSuburb;
    }

    public Postcode getPostcodeWithoutSuburb() {
        return postcodeWithoutSuburb;
    }

    public Suburb getSuburb() {
        return suburb;
    }

    public PostcodeSuburb getPostcodeWithSuburbEntry() {
        return postcodeWithSuburbEntry;
    }

    public PostcodeSuburb getPostcodeWithoutSuburbEntry() {
        return postcodeWithoutSuburbEntry;
    }

    public void setPostcodeWithSuburb(Postcode postcodeWithSuburb) {
        this.postcodeWithSuburb = postcodeWithSuburb;
    }

    public void setPostcodeWithoutSuburb(Postcode postcodeWithoutSuburb) {
        this.postcodeWithoutSuburb = postcodeWithoutSuburb;
    }

    public void setSuburb(Suburb suburb) {
        this.suburb = suburb;
    }

    public void setPostcodeWithSuburbEntry(PostcodeSuburb postcodeWithSuburbEntry) {
        this.postcodeWithSuburbEntry = postcodeWithSuburbEntry;
    }

    public void setPostcodeWithoutSuburbEntry(PostcodeSuburb postcodeWithoutSuburbEntry) {
        this.postcodeWithoutSuburbEntry = postcodeWithoutSuburbEntry;
    }
}
