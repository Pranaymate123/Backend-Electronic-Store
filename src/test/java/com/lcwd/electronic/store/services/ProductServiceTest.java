package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ModelMapper mapper;

    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    private Product product;

    private Category category;
    @MockBean
    private CategoryRepository categoryRepository;

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

         category=Category.builder()
                .categoryId("udhfjkh")
                .coverImage("test.jpg")
                .description("Test Category")
                .title("Test Category For Testing")
                .build();

    }

    @Test
    public void createProductTest() {
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = productService.create(mapper.map(product, ProductDto.class));
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(productDto.getTitle(), product.getTitle());
    }

    @Test
    public void updateProductTest() {
        ProductDto productDto1 = ProductDto.builder()
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
        String productId = "jhbjhsdb";
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = productService.update(mapper.map(productDto1, ProductDto.class), productId);
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(product.getProductId(), productDto.getProductId());
        Assertions.assertEquals(productDto.getTitle(), productDto1.getTitle());
    }

    @Test
    public void deleteProductTest() {
        String userId = "jhabsdbh";
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        productService.delete(userId);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }

    @Test
    public void getProductTest()
    {
        String userId = "jhabsdbh";
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
       ProductDto productDto=productService.get(userId);
       Assertions.assertEquals(product.getTitle(),productDto.getTitle());
    }

    @Test
    public void getAllProductTest()
    {
      Product  product = Product.builder()
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

     Product   product1 = Product.builder()
                .productId("jkakjfdvjknihsd")
                .title("Test 1 Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .build();

      Product  product2 = Product.builder()
                .productId("hjhjjfdjfgsfjhfd")
                .title("Test2 Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .build();


        List<Product> prodLst= Arrays.asList(product,product1,product2);
        Page<Product> page=new PageImpl<>(prodLst);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> pageableResponse= productService.getAll(0,10,"title","desc");
        Assertions.assertEquals(3,pageableResponse.getContent().size());
    }

    @Test
    public void getAllLProductsLiveTest()
    {
        Product  product = Product.builder()
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

        Product   product1 = Product.builder()
                .productId("iijgkrtrkjgrnt")
                .title("Test1 Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .build();

        Product  product2 = Product.builder()
                .productId("akjhvkjdhjkt")
                .title("Test2 Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .build();


        List<Product> prodLst= Arrays.asList(product,product1,product2);
        Page<Product> page=new PageImpl<>(prodLst);
        Mockito.when(productRepository.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> pageableResponse= productService.getAllLive(0,10,"title","desc");
        Assertions.assertEquals(3,pageableResponse.getContent().size());

    }
    @Test
    public void searchByTitleTest()
    {
        Product  product = Product.builder()
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

        Product   product1 = Product.builder()
                .productId("udhfjkfhdjkv")
                .title("Test 11 Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .build();

        Product  product2 = Product.builder()
                .productId("ihfkrjehvkjvre")
                .title("Test 2 Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .build();

        String subtitle="ijdgte";

        List<Product> prodLst= Arrays.asList(product,product1,product2);
        Page<Product> page=new PageImpl<>(prodLst);
        Mockito.when(productRepository.findByTitleContaining(Mockito.anyString(),(Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> pageableResponse= productService.searchByTitle(subtitle,0,10,"title","desc");
        Assertions.assertEquals(3,pageableResponse.getContent().size());
    }
    @Test
    public void createWithCategoryTest()
    {

        String categoryId="udhfjkh";

        ProductDto productDto1=ProductDto.builder()
                .productId("udhfjkfhdjkv")
                .title("Test 11 Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .category(mapper.map(category, CategoryDto.class))
                .build();

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
//        product.setCategory(category);
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(mapper.map(productDto1,Product.class));
        ProductDto productDto=productService.createWithCategory(productDto1,categoryId);
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(categoryId,productDto1.getCategory().getCategoryId());
    }
    @Test
    public void updateCategoryTest()
    {
        String productId="jbdjkfjk";
        ProductDto  productDto = ProductDto.builder()
                .productId("jbdjkfjk")
                .title("Test 2 Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .build();


        String categoryId="udhfjkh";

        Product testProduct=mapper.map(productDto,Product.class);;

        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(testProduct));
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(testProduct);

        ProductDto updatedCategoryProduct=productService.updateCategory(productId,categoryId);
        Assertions.assertEquals(productDto.getProductId(),updatedCategoryProduct.getProductId());
        Assertions.assertEquals(categoryId,updatedCategoryProduct.getCategory().getCategoryId());
    }
    @Test
    public void getAllOfCategoryTest()
    {
        Product  product = Product.builder()
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

        Product   product1 = Product.builder()
                .productId("udhfjkfhdjkv")
                .title("Test 11 Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .build();

        Product  product2 = Product.builder()
                .productId("ihfkrjehvkjvre")
                .title("Test 2 Product")
                .productImageName("test.jpg")
                .addedDate(new Date())
                .description("Testing the product Service")
                .price(1200)
                .discountedPrice(800)
                .live(true)
                .stock(true)
                .quantity(10)
                .build();

        List<Product> prodLst= Arrays.asList(product,product1,product2);
        String categoryId="udhfjkh";
        Page<Product> products=new PageImpl<>(prodLst);

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));

        Mockito.when(productRepository.findByCategory(Mockito.any(),(Pageable) Mockito.any())).thenReturn(products);

        PageableResponse<ProductDto> response=productService.getAllOfCategory(categoryId,0,10,"title","desc");
       Assertions.assertNotNull(response.getContent());
        Assertions.assertEquals(3,response.getContent().size());
    }

}
