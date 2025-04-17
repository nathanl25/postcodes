package io.nology.postcode_backend.postcode;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostcodeRepository extends JpaRepository<Postcode, Long> {
    Optional<Postcode> findOneByPostcode(String postcode);

    List<Postcode> findByPostcodeIn(Set<String> postcodes);
    // boolean existsById(String id);
}