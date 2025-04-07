package io.nology.postcode_backend.postcode;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("admin")
public class AdminController {
    @GetMapping()
    public String getMethodName() {
        return "Admin only route";
    }

}
