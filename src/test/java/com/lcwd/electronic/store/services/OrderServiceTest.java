package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.*;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.OrderRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

@SpringBootTest
public class OrderServiceTest {
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private OrderService orderService;

    private Order order;

    private User user;
    private Cart cart;
    private OrderItem orderItem;

    private CartItem cartItem;

    private Product product;
    @BeforeEach
    public void init()
    {
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

        order=Order.builder()
                .orderId("jjfdjhbfd")
                .orderedDate(new Date())
                .billingAddress("Testing order service")
                .billingName("Test User")
                .orderStatus("pending")
                .paymentStatus("NotPaid")
                .build();

        cart = Cart.builder()
                .cartId("jhgduhd")
                .createdAt(new Date())
                .items(new ArrayList<CartItem>())
                .build();

    }

    @Test
    public void createOrderTest()
    {
        cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(10)
                .totalPrice(10000)
                .cartItemId(12)
                .build();
        CartItem cartItem1 = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(10)
                .totalPrice(10000)
                .cartItemId(12)
                .build();

        CreateOrderRequest createOrderRequest=CreateOrderRequest.builder()
                .cartId("hsdfjhvbf")
                .userId("fdjkfdjk")
                .billingName("Test User")
                .billingAddress("Test Order Request")
                .orderStatus("Pending")
                .build();

        cart.getItems().add(cartItem);
        cart.getItems().add(cartItem1);
        Mockito.when(cartRepository.findById(Mockito.anyString())).thenReturn(Optional.of(cart));
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
//        Mockito.when(cartRepository.save(Mockito.any())).thenReturn(null);
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);

        OrderDto savedOrder=orderService.createOrder(createOrderRequest);

        Assertions.assertNotNull(savedOrder);
        Assertions.assertEquals(order.getBillingName(),savedOrder.getBillingName());

    }
    @Test
    public void removeOrderTest()
    {
        String orderId="jhsfb";
        Mockito.when(orderRepository.findById(Mockito.anyString())).thenReturn(Optional.of(order));
        orderService.removeOrder(orderId);
        Mockito.verify(orderRepository,Mockito.times(1)).delete(order);
    }
    @Test
    public void getOrderOfUserTest()
    {
      Order  order1=Order.builder()
                .orderId("jjfdjhbfd")
                .orderedDate(new Date())
                .billingAddress("Testing1 order service")
                .billingName("Test1 User")
                .orderStatus("pending")
                .paymentStatus("NotPaid")
                .build();

        Order  order2=Order.builder()
                .orderId("jhhdgfjhdf")
                .orderedDate(new Date())
                .billingAddress("Testing2 order service")
                .billingName("Test2 User")
                .orderStatus("pending")
                .paymentStatus("NotPaid")
                .build();

        Order  order3=Order.builder()
                .orderId("jhdfghhdj")
                .orderedDate(new Date())
                .billingAddress("Testing3 order service")
                .billingName("Test3 User")
                .orderStatus("pending")
                .paymentStatus("NotPaid")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(orderRepository.findByUser(Mockito.any())).thenReturn(Arrays.asList(order,order1,order2,order3));
        List<OrderDto> orders=orderService.getOrdersOfUser("jdjfjhfjkh");
        Assertions.assertEquals(4,orders.size());
    }

    @Test
    public void getOrders()
    {
        Order  order1=Order.builder()
                .orderId("jjfdjhbfd")
                .orderedDate(new Date())
                .billingAddress("Testing1 order service")
                .billingName("Test1 User")
                .orderStatus("pending")
                .paymentStatus("NotPaid")
                .build();

        Order  order2=Order.builder()
                .orderId("jhhdgfjhdf")
                .orderedDate(new Date())
                .billingAddress("Testing2 order service")
                .billingName("Test2 User")
                .orderStatus("pending")
                .paymentStatus("NotPaid")
                .build();

        Order  order3=Order.builder()
                .orderId("jhdfghhdj")
                .orderedDate(new Date())
                .billingAddress("Testing3 order service")
                .billingName("Test3 User")
                .orderStatus("pending")
                .paymentStatus("NotPaid")
                .build();
        List<Order> orders=Arrays.asList(order1,order2,order3);
        Page<Order> page=new PageImpl<>(orders);
        Mockito.when(orderRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<OrderDto> response =orderService.getOrders(0,10,"title","desc");
        Assertions.assertEquals(3,response.getContent().size());
    }
}
