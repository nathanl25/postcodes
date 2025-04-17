package io.nology.postcode_backend.postcode;

import io.nology.postcode_backend.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "postcodes")
public class Postcode extends BaseEntity {
    @Column(nullable = false, name = "p_name")
    private String postcode;

    public Postcode() {
    }

    public Postcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
