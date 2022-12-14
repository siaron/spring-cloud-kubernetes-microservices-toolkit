package spring.cloud.kubernetes.example.uaa.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.cloud.kubernetes.example.uaa.facode.OrganizationFeignClient;

/**
 * @author wxl
 */
@RestController
@RequestMapping("/uaa")
@RequiredArgsConstructor
public class PassportController {

    private final OrganizationFeignClient organizationFeignClient;

    @GetMapping("/passport/{identifier}")
    public String passport(@PathVariable("identifier") String identifier) {
        organizationFeignClient.organization(identifier);
        return "ok";
    }
}
