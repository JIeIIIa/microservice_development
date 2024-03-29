package it.discovery.monolith.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.discovery.library.LibraryApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

@SpringJUnitWebConfig(LibraryApplication.class)
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void updateBook_validBook_bookSaved() throws Exception {
    }

    @Test
    @Disabled
    void getLibraryName() throws Exception {
        mockMvc.perform(get("/shop/library"))
            .andExpect(status().isOk());
    }

    @Test
    void getFakeLibraryName() throws Exception {
        mockMvc.perform(get("/shop/fake_library"))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void findAllBooks() throws Exception {
        mockMvc.perform(get("/shop/book"))
            .andDo(print())
            .andExpect(status().isOk());
//            .andExpect(jsonPath("*"), )
    }
}
