package io.nology.postcode_backend.postcodeSuburb;

import io.nology.postcode_backend.common.BaseEntity;
import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.suburb.Suburb;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @JoinColumn(name = "postcode")
    Postcode postcode;

    @ManyToOne
    @JoinColumn(name = "suburb_id")
    Suburb suburb;
}
