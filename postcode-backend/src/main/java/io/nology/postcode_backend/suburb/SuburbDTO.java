package io.nology.postcode_backend.suburb;

import java.util.ArrayList;
import java.util.List;

import io.nology.postcode_backend.postcode.Postcode;

public class SuburbDTO {
    public class PostcodeData {
        private Long id;
        private String postcode;

        public PostcodeData(Long id, String postcode) {
            this.id = id;
            this.postcode = postcode;
        }

        public Long getPostcodeId() {
            return id;
        }

        public String getPostcode() {
            return postcode;
        }
    }

    public SuburbDTO() {

    }

    public SuburbDTO(Suburb suburb, List<Postcode> postcodes) {
        suburbId = suburb.getId();
        this.suburbName = suburb.getName();
        if (postcodes == null || postcodes.isEmpty() || postcodes.get(0) == null) {
            this.postcodes = new ArrayList<>();
        } else {
            this.postcodes = postcodes.stream().map(p -> new PostcodeData(p.getId(), p.getPostcode())).toList();
        }
    }

    private Long suburbId;

    private String suburbName;

    private List<PostcodeData> postcodes;

    public Long getSuburbId() {
        return suburbId;
    }

    public String getSuburbName() {
        return suburbName;
    }

    public List<PostcodeData> getPostcodes() {
        return postcodes;
    }
}
