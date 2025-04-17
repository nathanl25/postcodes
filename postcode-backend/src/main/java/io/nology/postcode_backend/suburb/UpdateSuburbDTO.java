package io.nology.postcode_backend.suburb;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

public class UpdateSuburbDTO {
    @Pattern(regexp = "[a-zA-Z- ]{4,25}", message = "Suburb can only contain letters, spaces and hypens and must be between 4 and 25 characters")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private Set<@Valid @Pattern(regexp = "^\\d{4}$", message = "Postcode must be a 4 digit number") String> postcodes;

    public Set<String> getPostcodes() {
        return postcodes;
    }

    public void setPostcodes(Set<String> postcodes) {
        this.postcodes = postcodes;
    }

    public boolean hasPostcodes() {
        return postcodes != null;
    }

    public boolean hasName() {
        return name != null;
    }
}
