package io.nology.postcode_backend.postcode;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostcodeRepository extends JpaRepository<Postcode, String> {

    // boolean existsById(String id);
}