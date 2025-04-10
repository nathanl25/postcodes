package io.nology.postcode_backend.suburb;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SuburbRepository extends JpaRepository<Suburb, Long> {

    Optional<List<Suburb>> findByIdIn(Set<Long> suburbIds);

    Optional<Suburb> findOneByName(String name);
}
