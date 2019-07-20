package it.discovery.monolith.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.discovery.monolith.MonolithApplication;

@SpringJUnitWebConfig(MonolithApplication.class)
@AutoConfigureMockMvc
public class ShopControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void updateBook_validBook_bookSaved() throws Exception {
    }

    @Test
    void getLibraryName() throws Exception {
        mockMvc.perform(get("/library"))
            .andExpect(status().isOk());
    }

    @Test
    void getFakeLibraryName() throws Exception {
        mockMvc.perform(get("/fake_library"))
            .andExpect(status().is4xxClientError());
    }
}
