package io.nology.postcode_backend.postcode;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("postcode")
public class PostcodeController {
    @GetMapping()
    public String getMethodName() {
        return new String("Test");
    }

}
