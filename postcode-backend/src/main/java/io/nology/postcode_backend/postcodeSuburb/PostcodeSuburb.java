package io.nology.postcode_backend.postcodeSuburb;

import io.nology.postcode_backend.common.BaseEntity;
import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.suburb.Suburb;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "postcode_suburbs")
public class PostcodeSuburb extends BaseEntity {

    public PostcodeSuburb() {

    }

    public PostcodeSuburb(Postcode postcode, Suburb suburb) {
        this.postcode = postcode;
        this.suburb = suburb;
    }

    @ManyToOne
    @JoinColumn(name = "postcode_id")
    @OrderColumn(name = "p_name")
    private Postcode postcode;

    @ManyToOne
    @JoinColumn(name = "suburb_id")
    @OrderColumn(name = "s_name")
    private Suburb suburb;

    public Postcode getPostcode() {
        return postcode;
    }

    public Suburb getSuburb() {
        return suburb;
    }

    public void setPostcode(Postcode postcode) {
        this.postcode = postcode;
    }

    public void setSuburb(Suburb suburb) {
        this.suburb = suburb;
    }
}
