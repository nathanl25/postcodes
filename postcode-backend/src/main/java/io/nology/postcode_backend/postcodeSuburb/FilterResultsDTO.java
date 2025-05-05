package io.nology.postcode_backend.postcodeSuburb;

import org.springframework.data.domain.Sort.Direction;

import jakarta.validation.constraints.Pattern;

public class FilterResultsDTO {

    @Pattern(regexp = "^\\d{1,4}$", message = "Postcode must be a 1-4 digit number")
    private String postcode;

    @Pattern(regexp = "[a-zA-Z- ]{1,25}", message = "Suburb can only contain letters, spaces and hypens and must be at least 1 character")
    private String name;

    private Direction postcodeSortDirection = Direction.ASC;

    private Direction suburbSortDirection = Direction.ASC;

    public Direction getPostcodeSortDirection() {
        return postcodeSortDirection;
    }

    public void setPostcodeSortDirection(Direction postcodeSortDirection) {
        this.postcodeSortDirection = postcodeSortDirection;
    }

    public Direction getSuburbSortDirection() {
        return suburbSortDirection;
    }

    public void setSuburbSortDirection(Direction suburbSortDirection) {
        this.suburbSortDirection = suburbSortDirection;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
