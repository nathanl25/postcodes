package io.nology.postcode_backend.suburb;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.postcode_backend.common.exceptions.NotFoundException;
import io.nology.postcode_backend.common.exceptions.PreExistingException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("suburb")
public class SuburbController {
    private SuburbService suburbService;

    SuburbController(SuburbService suburbService) {
        this.suburbService = suburbService;
    }

    @PostMapping()
    public ResponseEntity<Suburb> createSuburb(@RequestBody @Valid CreateSuburbDTO data)
            throws PreExistingException, NotFoundException {
        if (this.suburbService.getByName(data.getName()).isPresent()) {
            throw new PreExistingException("The specified suburb is pre-existing, cannot create new suburb");
        }
        Suburb newSuburb = this.suburbService.createSuburb(data);
        return new ResponseEntity<>(newSuburb, HttpStatus.CREATED);
    }

}
