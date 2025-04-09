package io.nology.postcode_backend.postcode;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreatePostcodeDTO {

    @NotBlank
    @Pattern(regexp = "^\\d{4}$", message = "Postcode must be a 4 digit number")
    private String postcode;

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPostcode() {
        return postcode;
    }

    private Set<@Valid @Min(value = 1, message = "Invalid suburb inputted") Long> suburbIds;

    public Set<Long> getSuburbIds() {
        return suburbIds;
    }

    public void setSuburbIds(Set<Long> suburbIds) {
        this.suburbIds = suburbIds;
    }

    public boolean hasSuburbIds() {
        return suburbIds != null;
    }
}
