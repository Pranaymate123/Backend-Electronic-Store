package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.RoleRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    User user;
    Role role;

    @BeforeEach
    public void init(){


        role=Role.builder()
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
    //Create User
    @Test
    public void createUserTest()
    {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(role));

        UserDto user1=userService.createUser(mapper.map(user, UserDto.class));

        Assertions.assertNotNull(user1);
    }
    //update User Test
    @Test
    public void updateUserTest()
    {

        String userId="";
        UserDto userDto=UserDto.builder()
                .name("Karana")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
//
        UserDto updatetdUser=userService.updateUser(userDto,"abcdfjkv");
//        UserDto updatetdUser=mapper.map(user, UserDto.class);
        System.out.println(updatetdUser.getName());
        Assertions.assertNotNull(updatetdUser);
        Assertions.assertEquals(updatetdUser.getUserId(),user.getUserId());
        Assertions.assertEquals(updatetdUser.getName(),userDto.getName());

    }

    @Test
    public void deleteUserTest()
    {
        String userId="jhsbjdds";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
    }
    @Test
    public void getAllUserTest()
    {
        User user1=User.builder()
                .name("Raju")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();

        User user2=User.builder()
                .name("Meena")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();

        List<User> userList= Arrays.asList(user,user1,user2);
        Page<User> page= new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser =userService.getAllUser(1,3,"name","desc");
        Assertions.assertNotNull(allUser.getContent());
        Assertions.assertEquals(3,allUser.getContent().size());
    }
    @Test
    public void getUserByIdTest()
    {
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));

        UserDto userDto=userService.getUserById("jhfbdf");
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(),userDto.getName(),"Name Not Matched ");


    }
    @Test
    public void getUserByEmailTest()
    {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
         UserDto userDto=userService.getUserByEmail("abcd@gmail.com");
         Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(),userDto.getName(),"Name Not Matched ");
        Assertions.assertEquals(user.getEmail(),userDto.getEmail(),"Email Not Matched ");

    }
    @Test
    public void searchUserTest()
    {
        User user1=User.builder()
                .name("Raju Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();

        User user2=User.builder()
                .name("Vansh Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();
        User user3=User.builder()
                .name("Yash Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();

        User user4=User.builder()
                .name("Akshay Kumar")
                .about("This is testing updated User About Details")
                .gender("Male")
                .imageName("xyz.png")
                .password("abcd")
                .build();
        String keywords="Kumar";
        Mockito.when(userRepository.findByNameContaining(keywords)).thenReturn(Arrays.asList(user1,user2,user3,user4));

        List<UserDto> userDtos=userService.searchUser(keywords);
        Assertions.assertEquals(4,userDtos.size());

    }

    @Test
    public void getUserByEmailOptionalTest()
    {
        String email="pranaymate0706@gmail.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
       Optional<User> optionalUser=userService.findUserByEmailOptional(email);
       User user= optionalUser.get();
       Assertions.assertEquals(email,user.getEmail());
    }

}
