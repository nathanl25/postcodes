package io.nology.postcode_backend.suburb;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.postcode_backend.postcode.PostcodeDTO;
import io.nology.postcode_backend.postcodeSuburb.FilterResultsDTO;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    @GetMapping("/filter")
    public ResponseEntity<List<SuburbDTO>> queryPostcodes(@ModelAttribute @Valid FilterResultsDTO data) {
        List<SuburbDTO> response = this.suburbService.findByCriteria(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
