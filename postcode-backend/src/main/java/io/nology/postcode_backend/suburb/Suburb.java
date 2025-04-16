package io.nology.postcode_backend.suburb;

import io.nology.postcode_backend.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "suburbs")
public class Suburb extends BaseEntity {

    @Column(nullable = false, name = "s_name")
    private String name;

    public Suburb() {

    }

    public Suburb(String name) {
        this.name = name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }
}
