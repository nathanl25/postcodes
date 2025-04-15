package io.nology.postcode_backend.suburb;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.postcode_backend.common.exceptions.NotFoundException;
import io.nology.postcode_backend.common.exceptions.PreExistingException;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("suburb")
public class SuburbController {
    private SuburbService suburbService;

    SuburbController(SuburbService suburbService) {
        this.suburbService = suburbService;
    }

    @GetMapping()
    public ResponseEntity<List<SuburbDTO>> getAll() {
        List<SuburbDTO> rawData = this.suburbService.getAll();
        return new ResponseEntity<>(rawData, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<SuburbDTO> createSuburb(@RequestBody @Valid CreateSuburbDTO data)
            throws PreExistingException, NotFoundException {
        if (this.suburbService.getByName(data.getName()).isPresent()) {
            throw new PreExistingException("The specified suburb is pre-existing, cannot create new suburb");
        }
        SuburbDTO newSuburb = this.suburbService.createSuburb(data);
        return new ResponseEntity<>(newSuburb, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSuburb(@PathVariable Long id) throws NotFoundException {
        Suburb toDelete = this.suburbService.getById(id)
                .orElseThrow((() -> new NotFoundException("The specified suburb does not exist")));
        this.suburbService.deleteSuburb(toDelete);
        return new ResponseEntity<>("Suburb has been deleted", HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<SuburbDTO> updateSuburb(@PathVariable Long id, @RequestBody @Valid UpdateSuburbDTO data)
            throws NotFoundException, PreExistingException {
        Suburb toUpdate = this.suburbService.getById(id)
                .orElseThrow((() -> new NotFoundException("The specified suburb does not exist")));
        if (data.hasName() && this.suburbService.getByName(data.getName()).isPresent()) {
            if (!data.getName().equals(toUpdate.getName())) {
                throw new PreExistingException(
                        "A suburb with this name already exists, cannot update suburb name");
            }
        }
        SuburbDTO updatedSuburb = this.suburbService.updateSuburb(toUpdate, data);
        return new ResponseEntity<>(updatedSuburb, HttpStatus.OK);
    }
}
