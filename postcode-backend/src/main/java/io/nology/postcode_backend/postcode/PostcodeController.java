package io.nology.postcode_backend.postcode;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.postcode_backend.common.exceptions.NotFoundException;
import io.nology.postcode_backend.common.exceptions.PreExistingException;
import io.nology.postcode_backend.postcodeSuburb.PostcodeSuburb;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("postcode")
public class PostcodeController {

    private PostcodeService postcodeService;

    PostcodeController(PostcodeService postcodeService) {
        this.postcodeService = postcodeService;
    }

    @GetMapping()
    public ResponseEntity<List<Postcode>> getAllPostcodes() {
        List<Postcode> postcodes = this.postcodeService.getAll();
        return new ResponseEntity<>(postcodes, HttpStatus.OK);
        // return this.postcodeService.getAll();
    }

    @GetMapping("raw")
    public ResponseEntity<List<PostcodeDTO>> getAllRaw() {
        List<PostcodeDTO> rawData = this.postcodeService.getAllRaw();
        return new ResponseEntity<>(rawData, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PostcodeDTO> createPostcode(@RequestBody @Valid CreatePostcodeDTO data)
            throws PreExistingException, NotFoundException {
        if (this.postcodeService.getByPostcode(data.getPostcode()).isPresent()) {
            throw new PreExistingException("The specified postcode is pre-existing, cannot create new postcode");
        }
        PostcodeDTO newPostCode = this.postcodeService.createPostcode(data);
        return new ResponseEntity<PostcodeDTO>(newPostCode, HttpStatus.CREATED);
    }

    @DeleteMapping("{postcode}")
    public ResponseEntity<String> deletePostcode(@PathVariable String postcode) throws NotFoundException {
        // if (this.postcodeService.getByPostcode(postcode).)
        Postcode toDelete = this.postcodeService.getByPostcode(postcode)
                .orElseThrow((() -> new NotFoundException("The specified postcode does not exist")));
        // this.postcodeService.deletePostcode(postcode);
        System.out.println("Postcode exists");
        this.postcodeService.deletePostcode(toDelete);
        return new ResponseEntity<>("Postcode has been deleted", HttpStatus.OK);
    }

    @PostMapping("{postcode}")
    public ResponseEntity<PostcodeDTO> updatePostcode(@PathVariable String postcode,
            @RequestBody @Valid UpdatePostcodeDTO data) throws NotFoundException, PreExistingException {
        Postcode toUpdate = this.postcodeService.getByPostcode(postcode)
                .orElseThrow((() -> new NotFoundException("The specified postcode does not exist")));
        if (data.hasPostcode() && this.postcodeService.getByPostcode(data.getPostcode()).isPresent()) {
            throw new PreExistingException("The specified postcode currently exists, cannot update postcode number");
        }
        PostcodeDTO updatedPostcode = this.postcodeService.updatePostcode(toUpdate, data);
        return new ResponseEntity<>(updatedPostcode, HttpStatus.OK);
    }

}
