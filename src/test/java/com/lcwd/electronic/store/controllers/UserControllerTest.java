package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private Role role;

    private User user;

    @Autowired
    private ModelMapper  mapper;

    @Autowired
    private FileService fileService;

    @BeforeEach
    public void init(){


        role= Role.builder()
                .roleId("abc")
                .roleName("Normal")
                .build();

        user= User.builder()
                .name("Pranay")
                .email("pranaymate0706@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("pranay.png")
                .password("pranay4122m")
                .roles(Set.of(role))
                .build();
    }
    @Test
    public void createUserTest() throws Exception {
        //  /users  -->Post Request  + usedata as Jspon
        // data as json created
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(mapper.map(user, UserDto.class));
        //request for url
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());


    }

    private String convertObjectToJsonString(User user) {
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;

        }

    }

    @Test
    public void updateUserTest() throws Exception {
        String userId="1234";

        UserDto  userDto=this.mapper.map(user,UserDto.class);

        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(userDto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/"+userId)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcmFuYXltYXRlMDcwNkBnbWFpbC5jb20iLCJpYXQiOjE3MDUxNTY4MzMsImV4cCI6MTcwNTE3NDgzM30.f1Uz5by530GfofDlpUXVcoVVK7FOgx5Fhulm_-U4ZxduoenzoO7VH6c8I3VU8dK5tuLAgNQc0TM3qiVmobxdEA")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }
    @Test
    public  void getAllUsersTest() throws Exception {
        UserDto user1=UserDto.builder()
                .name("Raju Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();

        UserDto user2=UserDto.builder()
                .name("Vansh Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();
        UserDto user3=UserDto.builder()
                .name("Yash Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();

        UserDto user4=UserDto.builder()
                .name("Akshay Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();

        PageableResponse<UserDto> pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(user1,user2,user3,user4));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setTotalPages(100);
        pageableResponse.setTotalElements(100);
        Mockito.when(userService.getAllUser(Mockito.anyInt(),Mockito.anyInt(), Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);


        this.mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getUserByUserIdTest() throws Exception {
        String userId="jhhsabjhsdb";
        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(mapper.map(user,UserDto.class));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByEmailTest() throws Exception {
        String emailId="pranaymate0706@gmail.com";
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(mapper.map(user,UserDto.class));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/email/"+emailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void searchUserTest() throws Exception {
        UserDto user1=UserDto.builder()
                .name("Raju Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();

        UserDto user2=UserDto.builder()
                .name("Vansh Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();
        UserDto user3=UserDto.builder()
                .name("Yash Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();

        UserDto user4=UserDto.builder()
                .name("Akshay Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();
        String keywords="Kumar";
        Mockito.when(userService.searchUser(Mockito.anyString())).thenReturn(Arrays.asList(user1,user2,user3,user4));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/search/"+keywords)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
//    public void uploadImageTest() throws IOException {
//        String userId="jbjkvjkfn";
//        String fileNameWithExtension="/images/users/pranay.jpg";
//        String imageUploadPath="/images/users";
//        Mockito.when(fileService.uploadFile((MultipartFile) Mockito.any(),Mockito.anyString())).thenReturn(fileNameWithExtension);
//        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(mapper.map(user,UserDto.class));
//        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(mapper.map(user, UserDto.class));
//
//        fileService.uploadFile()
//
//        )
//    }

}
