package io.nology.postcode_backend.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.postcode_backend.common.exceptions.NotFoundException;
import io.nology.postcode_backend.common.exceptions.PreExistingException;
import io.nology.postcode_backend.postcode.CreatePostcodeDTO;
import io.nology.postcode_backend.postcode.Postcode;
import io.nology.postcode_backend.postcode.PostcodeDTO;
import io.nology.postcode_backend.postcode.PostcodeService;
import io.nology.postcode_backend.postcode.UpdatePostcodeDTO;
import io.nology.postcode_backend.suburb.CreateSuburbDTO;
import io.nology.postcode_backend.suburb.Suburb;
import io.nology.postcode_backend.suburb.SuburbDTO;
import io.nology.postcode_backend.suburb.SuburbService;
import io.nology.postcode_backend.suburb.UpdateSuburbDTO;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("admin")
public class AdminController {

    private PostcodeService postcodeService;
    private SuburbService suburbService;

    public AdminController(PostcodeService postcodeService, SuburbService suburbService) {
        this.postcodeService = postcodeService;
        this.suburbService = suburbService;
    }

    @GetMapping()
    public String getMethodName() {
        return "Admin only route";
    }

    @PostMapping("/postcode")
    public ResponseEntity<PostcodeDTO> createPostcode(@RequestBody @Valid CreatePostcodeDTO data)
            throws PreExistingException, NotFoundException {
        if (this.postcodeService.getByPostcode(data.getPostcode()).isPresent()) {
            throw new PreExistingException("The specified postcode is pre-existing, cannot create new postcode");
        }
        PostcodeDTO newPostCode = this.postcodeService.createPostcode(data);
        return new ResponseEntity<PostcodeDTO>(newPostCode, HttpStatus.CREATED);
    }

    @PostMapping("/postcode/{postcode}")
    public ResponseEntity<PostcodeDTO> updatePostcode(@PathVariable String postcode,
            @RequestBody @Valid UpdatePostcodeDTO data) throws NotFoundException, PreExistingException {
        Postcode toUpdate = this.postcodeService.getByPostcode(postcode)
                .orElseThrow((() -> new NotFoundException("The specified postcode does not exist")));
        if (!this.postcodeService.validPostcodeUpdate(toUpdate, data)) {
            throw new PreExistingException("The specified postcode currently exists, cannot update postcode number");
        }
        PostcodeDTO updatedPostcode = this.postcodeService.updatePostcode(toUpdate, data);
        return new ResponseEntity<>(updatedPostcode, HttpStatus.OK);
    }

    @PostMapping("/suburb")
    public ResponseEntity<SuburbDTO> createSuburb(@RequestBody @Valid CreateSuburbDTO data)
            throws PreExistingException, NotFoundException {
        if (this.suburbService.getByName(data.getName()).isPresent()) {
            throw new PreExistingException("The specified suburb is pre-existing, cannot create new suburb");
        }
        SuburbDTO newSuburb = this.suburbService.createSuburb(data);
        return new ResponseEntity<>(newSuburb, HttpStatus.CREATED);
    }

    @DeleteMapping("/postcode/{postcode}")
    public ResponseEntity<String> deletePostcode(@PathVariable String postcode) throws NotFoundException {
        Postcode toDelete = this.postcodeService.getByPostcode(postcode)
                .orElseThrow((() -> new NotFoundException("The specified postcode does not exist")));
        this.postcodeService.deletePostcode(toDelete);
        return new ResponseEntity<>("Postcode has been deleted", HttpStatus.OK);
    }

    @DeleteMapping("/suburb/{id}")
    public ResponseEntity<String> deleteSuburb(@PathVariable Long id) throws NotFoundException {
        Suburb toDelete = this.suburbService.getById(id)
                .orElseThrow((() -> new NotFoundException("The specified suburb does not exist")));
        this.suburbService.deleteSuburb(toDelete);
        return new ResponseEntity<>("Suburb has been deleted", HttpStatus.OK);
    }

    @PostMapping("/suburb/{id}")
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
