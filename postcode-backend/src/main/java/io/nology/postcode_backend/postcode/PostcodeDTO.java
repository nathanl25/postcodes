package io.nology.postcode_backend.postcode;

import java.util.ArrayList;
import java.util.List;

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

    }

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
