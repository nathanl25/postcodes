package io.nology.postcode_backend.postcode;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.postcode_backend.common.exceptions.NotFoundException;
import io.nology.postcode_backend.common.exceptions.PreExistingException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("postcode")
public class PostcodeController {

    private PostcodeService postcodeService;

    PostcodeController(PostcodeService postcodeService) {
        this.postcodeService = postcodeService;
    }

    @GetMapping()
    public String getMethodName() {
        return "Public route";
    }

    @PostMapping()
    public ResponseEntity<Postcode> postMethodName(@RequestBody @Valid CreatePostcodeDTO data)
            throws PreExistingException, NotFoundException {
        if (this.postcodeService.getById(data.getPostcode()).isPresent()) {
            throw new PreExistingException("The specified postcode is pre-existing, cannot create new postcode");
        }
        Postcode newPostCode = this.postcodeService.createPostcode(data);
        return new ResponseEntity<Postcode>(newPostCode, HttpStatus.CREATED);
    }

}
