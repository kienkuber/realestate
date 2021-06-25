package com.example.realestate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.yml")
class RealEstateApplicationTests {

    @Test
    void contextLoads() {
    }

}
