package io.nology.postcode_backend.postcode;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.postcode_backend.common.exceptions.NotFoundException;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("postcode")
public class PostcodeController {

    private PostcodeService postcodeService;

    PostcodeController(PostcodeService postcodeService) {
        this.postcodeService = postcodeService;
    }

    @GetMapping()
    public ResponseEntity<List<PostcodeDTO>> getAll() {
        List<PostcodeDTO> rawData = this.postcodeService.getAll();
        return new ResponseEntity<>(rawData, HttpStatus.OK);
    }

    @GetMapping("{postcodeNum}")
    public ResponseEntity<PostcodeDTO> getPostcode(@PathVariable String postcodeNum) throws NotFoundException {

        Postcode postcode = this.postcodeService.getByPostcode(postcodeNum)
                .orElseThrow(() -> new NotFoundException("This postcode does not exist"));
        PostcodeDTO postcodeData = this.postcodeService.getPostcodeData(postcode);
        return new ResponseEntity<>(postcodeData, HttpStatus.OK);

    }

}
