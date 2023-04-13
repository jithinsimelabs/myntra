package org.simelabs.catelog.application.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simelabs.catelog.application.service.dto.CreateBrandCommand;
import org.simelabs.catelog.application.service.handler.BrandControllerAdvice;
import org.simelabs.catelog.application.service.models.Brand;
import org.simelabs.catelog.application.service.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

@WebMvcTest(value = BrandController.class)
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BrandRepository brandRepository;

    private final ObjectMapper om = new ObjectMapper();
    private String UNIQUE_BRAND_NAME_PUMA="Puma";

    @BeforeEach
    void setUp() {
    }

    @Test
    public void shouldReturnNotFoundWhileSendRequestOnEndPointForGetBrands() throws Exception {
        this.mockMvc.perform(get("/api/brand"))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnAllBrands() throws Exception {
        when(brandRepository.findAll()).thenReturn(fakeBrands());
        this.mockMvc.perform(get("/api/brand"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(status().isOk());
    }


    @Test
    void shouldReturn400ifBodyIsNull() throws Exception {

        CreateBrandCommand nullRequestBody = new CreateBrandCommand();

        this.mockMvc.perform(
                        post("/api/brand/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(nullRequestBody))
                )
                .andExpect(status().is(400));

    }


    @Test
    void shouldReturn400ifBodyIsBlank() throws Exception {

        CreateBrandCommand blankRequestBody = new CreateBrandCommand(" ");
        this.mockMvc.perform(
                        post("/api/brand/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(blankRequestBody))
                )
                .andExpect(status().is(400));

    }

    @Test
    void shouldReturn400ifBodyIsMinLength() throws Exception {
        CreateBrandCommand minLengthRequestBody = new CreateBrandCommand("mi");

        this.mockMvc.perform(
                        post("/api/brand/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(minLengthRequestBody))
                )
                .andExpect(status().is(400));

    }

    @Test
    void shouldReturn400ifNameIsUnique() throws Exception {

        when(brandRepository.findByName(anyString())).thenReturn(Optional.of(fakeBrands().get(0)));
        CreateBrandCommand minLengthRequestBody = new CreateBrandCommand(UNIQUE_BRAND_NAME_PUMA);

        this.mockMvc.perform(
                        post("/api/brand/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(minLengthRequestBody))
                )
                .andExpect(status().is(400));

    }

    @Test
    void shouldCreateBrandAndReturn201() throws Exception{
        when(brandRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(brandRepository.save(any(Brand.class))).thenReturn(fakeBrands().get(0));
        CreateBrandCommand body = new CreateBrandCommand(UNIQUE_BRAND_NAME_PUMA);
        this.mockMvc.perform(
                        post("/api/brand/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(body))
                )
                .andExpect(jsonPath("$.name",is(UNIQUE_BRAND_NAME_PUMA)))
                .andExpect(status().is(201));

    }

    @Test
    void shouldHaveThrowNotFoundWhenIdIsInvalid() throws Exception {

        when(brandRepository.findById(anyString())).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/api/brand/2"))
                .andExpect(status().is(404));
    }


    @Test
    void shouldReturnValidBrandIfIdIsValid() throws Exception {

        when(brandRepository.findById(anyString())).thenReturn(Optional.of(fakeBrands().get(0)));
        this.mockMvc.perform(get("/api/brand/2"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name", is(UNIQUE_BRAND_NAME_PUMA)));
    }



    private List<Brand> fakeBrands() {
        return List.of(
                Brand.builder()
                        .id("asdjflk")
                        .name("Puma")
                        .build(),
                Brand.builder()
                        .id("asdjklfasdfj")
                        .name("Addidas")
                        .build(),
                Brand.builder()
                        .id("qwerqwer")
                        .name("Nike")
                        .build()
        );
    }


}