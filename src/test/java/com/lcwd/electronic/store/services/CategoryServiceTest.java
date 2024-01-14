package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private  CategoryService categoryService;
    @Autowired
    private ModelMapper  mapper;

    private  Category category;
    @BeforeEach
    public void init()
    {
        category= Category.builder()
                .categoryId("udhfjkh")
                .coverImage("test.jpg")
                .description("Test Category")
                .title("Test Category For Testing")
                .build();
    }

    @Test
    public void createCategoryTest()
    {
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
       CategoryDto savedCategory= categoryService.create(mapper.map(category, CategoryDto.class));
        Assertions.assertNotNull(savedCategory);
        Assertions.assertEquals(category.getTitle(),savedCategory.getTitle());
    }
    @Test
    public void updateCategoryTest()
    {
        String categoryId="udhfjkh";
       CategoryDto categoryDto= CategoryDto.builder()
                .categoryId("udhfjkh")
                .coverImage("category.jpg")
                .description("New Test Category")
                .title("New Test Category For Testing")
                .build();
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(mapper.map(categoryDto, Category.class));
        CategoryDto updatedCategory=categoryService.update(categoryDto,categoryId);
        Assertions.assertEquals(categoryDto.getTitle(),updatedCategory.getTitle());
    }

    @Test
    public void deleteCategoryTest()
    {
        String categoryId="djfdjvbfjkv";
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        categoryService.delete(categoryId);
        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);
    }
    @Test
    public void getAllTest()
    {
      Category  category1= Category.builder()
                .categoryId("udhfjkh")
                .coverImage("test.jpg")
                .description("Test1 Category")
                .title("Test1 Category For Testing")
                .build();
        Category  category2= Category.builder()
                .categoryId("udhfjkh")
                .coverImage("test.jpg")
                .description("Test2 Category")
                .title("Test2 Category For Testing")
                .build();
        Category  category3= Category.builder()
                .categoryId("udhfjkh")
                .coverImage("test.jpg")
                .description("Test3 Category")
                .title("Test3 Category For Testing")
                .build();

        List<Category> categories= Arrays.asList(category1,category2,category3);
        Page<Category>  page=new PageImpl<>(categories);

        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
       PageableResponse<CategoryDto> response = categoryService.getAll(0,10,"title","desc");
       Assertions.assertEquals(3,response.getContent().size());
    }

    @Test
    public void getCategoryTest()
    {
        String categoryId="dbjkdkjfn";
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        CategoryDto categoryDto=categoryService.get(categoryId);
        Assertions.assertNotNull(categoryDto);

        Assertions.assertEquals(category.getTitle(),categoryDto.getTitle());
    }

}
