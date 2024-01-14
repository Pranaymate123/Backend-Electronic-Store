package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.CartItemRepository;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class CartServiceTest {

    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    private Product product;


    private User user;
    private Cart cart;
    private AddItemToCartRequest addItemToCartRequest;

    private CartItem cartItem;

    @BeforeEach
    public void init() {
        product = Product.builder()
                .productId("hjhsfjhfd")
                .title("Test Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .build();
        user = User.builder()
                .name("Pranay")
                .email("pranaymate0706@gmail.com")
                .about("This is testing create method")
                .gender("Male")
                .imageName("pranay.png")
                .password("pranay4122m")
                .build();

        cart = Cart.builder()
                .cartId("jhgduhd")
                .createdAt(new Date())
                .items(new ArrayList<CartItem>())
                .build();


    }

    @Test
    public void addItemToCartTest() {
        addItemToCartRequest = AddItemToCartRequest.builder()
                .quantity(10)
                .productId("fhjkgf")
                .build();

        String userId = "sjbfsjb";
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(cartRepository.findByUser(Mockito.any())).thenReturn(Optional.of(cart));
        Mockito.when(cartRepository.save(Mockito.any())).thenReturn(cart);


        CartDto savedCart = cartService.addItemToCart(userId, addItemToCartRequest);
        Assertions.assertNotNull(cart);
        Assertions.assertEquals(user.getUserId(), cart.getUser().getUserId());
    }

    @Test
    public void RemoveItemFromCartTest() {
        cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(10)
                .totalPrice(10000)
                .cartItemId(12)
                .build();

        int cartItemId = 12;
        String userId = "sjdjksnndj";
        Mockito.when(cartItemRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(cartItem));
        cartService.removeItemFromCart(userId, cartItemId);
        Mockito.verify(cartItemRepository, Mockito.times(1)).delete(cartItem);
    }

    @Test
    public void clearCartTest()
    {
        String userId="sfhvbhdfkbv";
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(cartRepository.findByUser(Mockito.any())).thenReturn(Optional.of(cart));
        Mockito.when(cartRepository.save(cart)).thenReturn(cart);
        cartService.clearCart(userId);
        Mockito.verify(cartRepository,Mockito.times(1)).save(cart);
    }
    @Test
    public void getCartByUserTest()
    {
        cart = Cart.builder()
                .cartId("jhgduhd")
                .createdAt(new Date())
                .items(new ArrayList<CartItem>())
                .user(user)
                .build();
        String userId="xhhfjh";
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(cartRepository.findByUser(Mockito.any())).thenReturn(Optional.of(cart));
        CartDto cartDto=cartService.getCartByUser(userId);
        Assertions.assertEquals(user.getUserId(),cartDto.getUser().getUserId());
    }

}
