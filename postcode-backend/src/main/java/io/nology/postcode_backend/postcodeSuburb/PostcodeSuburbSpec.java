package io.nology.postcode_backend.postcodeSuburb;

import org.springframework.data.jpa.domain.Specification;

import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.suburb.Suburb;
import jakarta.persistence.criteria.Join;

public class PostcodeSuburbSpec {

    public static Specification<PostcodeSuburb> hasPostcode(String postcode) {
        System.out.println(postcode);
        return (root, query, criteriaBuilder) -> {
            Join<PostcodeSuburb, Postcode> ps = root.join("postcode");
            return criteriaBuilder.like(ps.get("postcode"), "%" + postcode + "%");
        };
    }

    public static Specification<PostcodeSuburb> hasSuburb(String suburb) {
        return (root, query, criteriaBuilder) -> {
            Join<PostcodeSuburb, Suburb> ps = root.join("suburb");
            return criteriaBuilder.like(criteriaBuilder.lower(ps.get("name")), "%" + suburb.toLowerCase() + "%");
        };
    }

}
