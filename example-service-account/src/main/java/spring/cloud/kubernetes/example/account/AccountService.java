package spring.cloud.kubernetes.example.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import spring.cloud.kubernetes.example.account.facade.UaaFeignClient;

/**
 * @author wxl
 */
@EnableFeignClients(clients = UaaFeignClient.class)
@SpringBootApplication
public class AccountService {


    public static void main(String[] args) {

        SpringApplication.run(AccountService.class, args);
    }
}
