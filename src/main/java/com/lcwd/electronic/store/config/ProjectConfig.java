package com.lcwd.electronic.store.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    //Bean for converting entity --->Dto and vice versa
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

//    @Bean
//    public GoogleOAuth2Client  getGoogleOAuth2Client() {
//        return new GoogleOAuth2Client();
//    }

}
