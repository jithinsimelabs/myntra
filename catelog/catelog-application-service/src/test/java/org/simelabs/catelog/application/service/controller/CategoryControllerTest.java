package org.simelabs.catelog.application.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simelabs.catelog.application.service.dto.CreateBrandCommand;
import org.simelabs.catelog.application.service.dto.CreateCategoryCommand;
import org.simelabs.catelog.application.service.models.Category;
import org.simelabs.catelog.application.service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    private final ObjectMapper om = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryRepository categoryRepository;
    private final String UNIQUE_CATEGORY_NAME_MEN = "Men";

    @BeforeEach
    void setUp() {
    }

    @Test
    public void shouldReturnNotFoundWhileSendRequestOnEndPointForGetBrands() throws Exception {
        this.mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnAllBrands() throws Exception {
        when(categoryRepository.findAll()).thenReturn(fakeCategory());
        this.mockMvc.perform(get("/api/category"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(status().isOk());
    }


    @Test
    void shouldReturn400ifBodyIsNull() throws Exception {

        CreateCategoryCommand nullRequestBody = new CreateCategoryCommand();

        this.mockMvc.perform(
                        post("/api/category/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(nullRequestBody))
                )
                .andExpect(status().is(400));

    }


    @Test
    void shouldReturn400ifBodyIsBlank() throws Exception {

        CreateCategoryCommand blankRequestBody = new CreateCategoryCommand(" ");
        this.mockMvc.perform(
                        post("/api/category/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(blankRequestBody))
                )
                .andExpect(status().is(400));

    }

    @Test
    void shouldReturn400ifBodyIsMinLength() throws Exception {
        CreateBrandCommand minLengthRequestBody = new CreateBrandCommand("mi");

        this.mockMvc.perform(
                        post("/api/category/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(minLengthRequestBody))
                )
                .andExpect(status().is(400));

    }

    @Test
    void shouldReturn400ifNameIsUnique() throws Exception {

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(fakeCategory().get(0)));
        CreateCategoryCommand minLengthRequestBody = new CreateCategoryCommand(UNIQUE_CATEGORY_NAME_MEN);

        this.mockMvc.perform(
                        post("/api/category/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(minLengthRequestBody))
                )
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(status().is(400));

    }

    @Test
    void shouldCreateBrandAndReturn201() throws Exception {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(fakeCategory().get(0));
        CreateCategoryCommand body = new CreateCategoryCommand(UNIQUE_CATEGORY_NAME_MEN);
        this.mockMvc.perform(
                        post("/api/category/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(body))
                )
                .andExpect(jsonPath("$.name",is(UNIQUE_CATEGORY_NAME_MEN)))
                .andExpect(status().is(201));

    }

    @Test
    void shouldHaveThrowNotFoundWhenIdIsInvalid() throws Exception {

        when(categoryRepository.findById(anyString())).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/api/category/2"))
                .andExpect(status().is(404));
    }


    @Test
    void shouldReturnValidBrandIfIdIsValid() throws Exception {

        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(fakeCategory().get(0)));
        this.mockMvc.perform(get("/api/category/2"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name", is(UNIQUE_CATEGORY_NAME_MEN)));
    }


    private List<Category> fakeCategory() {
        return List.of(
                Category.builder()
                        .id("asdjflk")
                        .name(UNIQUE_CATEGORY_NAME_MEN)
                        .build(),
                Category.builder()
                        .id("asdjklfasdfj")
                        .name("Addidas")
                        .build(),
                Category.builder()
                        .id("qwerqwer")
                        .name("Nike")
                        .build()
        );
    }


}