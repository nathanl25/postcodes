package io.nology.postcode_backend.suburb;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateSuburbDTO {
    @NotBlank
    @Pattern(regexp = "[a-zA-Z- ]{4,25}", message = "Suburb can only contain letters, spaces and hypens and must be between 4 and 25 characters")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private Set<@Valid @Pattern(regexp = "^\\d{4}$", message = "Postcode must be a 4 digit number") String> postcodes;

    public void setPostcodes(Set<String> postcodes) {
        this.postcodes = postcodes;
    }

    public Set<String> getPostcodes() {
        if (postcodes == null) {
            return new HashSet<>();
        }
        return postcodes;
    }

    public boolean hasPostcodes() {
        return postcodes != null;
    }
}
