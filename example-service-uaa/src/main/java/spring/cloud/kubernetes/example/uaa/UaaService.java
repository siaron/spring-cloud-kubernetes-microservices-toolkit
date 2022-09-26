package spring.cloud.kubernetes.example.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import spring.cloud.kubernetes.example.uaa.facode.OrganizationFeignClient;

/**
 * @author wxl
 */
@EnableFeignClients(clients = OrganizationFeignClient.class)
@SpringBootApplication
public class UaaService {

    public static void main(String[] args) {

        SpringApplication.run(UaaService.class, args);
    }
}
