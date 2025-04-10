package io.nology.postcode_backend.postcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import io.nology.postcode_backend.suburb.Suburb;

public class PostcodeDTO {
    public PostcodeDTO() {

    }

    public class SuburbData {
        private Long id;
        private String name;

        public SuburbData(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getSuburbId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public PostcodeDTO(Postcode postcode, List<Suburb> suburbs) {
        postcodeId = postcode.getId();
        this.postcode = postcode.getPostcode();
        if (suburbs == null || suburbs.isEmpty() || suburbs.get(0) == null) {
            this.suburbs = new ArrayList<>();
        } else {
            this.suburbs = suburbs.stream().map(s -> new SuburbData(s.getId(), s.getName())).toList();
        }

        // this.suburbs = suburbs == null ? null : suburbs.stream().map(s ->
        // s.getName()).toList();
    }

    // public PostcodeDTO(Postcode postcode) {
    // id = postcode.getId();
    // this.postcode = postcode.getPostcode();
    // }

    private Long postcodeId;

    private String postcode;

    private List<SuburbData> suburbs;

    public Long getPostcodeId() {
        return postcodeId;
    }

    public String getPostcode() {
        return postcode;
    }

    public List<SuburbData> getSuburbs() {
        return suburbs;
    }
}
