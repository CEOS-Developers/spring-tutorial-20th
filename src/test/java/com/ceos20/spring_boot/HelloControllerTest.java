package com.ceos20.spring_boot;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;  //실제 HTTP 요청을 보내지 않고, 애플리케이션의 Spring MVC 레이어를 테스트할 수 있는 도구

    @DisplayName("DisplayName : 테스트 이름을 설정할 수 있습니다")
    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))  //MockMvc를 통해 / 엔드포인트에 GET 요청을 보냄
                .andExpect(status().isOk())  // 200 OK 상태 코드를 반환하는지 확인
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));  //응답 본문이 "Greetings from Spring Boot!"와 일치하는지 확인
    }
}
