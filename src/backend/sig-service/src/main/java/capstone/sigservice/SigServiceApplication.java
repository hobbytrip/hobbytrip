package capstone.sigservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class SigServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigServiceApplication.class, args);
    }

}
