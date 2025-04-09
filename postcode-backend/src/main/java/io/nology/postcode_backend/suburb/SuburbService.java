package io.nology.postcode_backend.suburb;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.nology.postcode_backend.common.exceptions.NotFoundException;

// import io.nology.postcode_backend.Suburb;

@Service
public class SuburbService {

    private SuburbRepository repo;

    SuburbService(SuburbRepository repo) {
        this.repo = repo;
    }

    public List<Suburb> getByIds(Set<Long> suburbIds) throws NotFoundException {
        List<Suburb> suburbs = this.repo.findAllById(suburbIds);
        if (suburbs.size() != suburbIds.size()) {
            throw new NotFoundException("Matching suburbs not found");
        }
        return suburbs;
    }

}
