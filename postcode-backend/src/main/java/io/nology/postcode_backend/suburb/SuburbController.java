package io.nology.postcode_backend.suburb;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

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

}
