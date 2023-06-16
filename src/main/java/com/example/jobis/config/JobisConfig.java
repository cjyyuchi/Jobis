package com.example.jobis.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobisConfig {


    // ModelMapper 설정
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    // Swagger 설정
    @Bean
    public OpenAPI openApi(){
        Info info = new Info()
                .title("자비스앤빌런즈 API 구축")
                .version("1.0")
                .description("삼쩜삼 백엔드 엔지니어 채용과제");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
