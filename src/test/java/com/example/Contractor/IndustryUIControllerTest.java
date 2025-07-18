package com.example.Contractor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(DatabaseSetup.class)
@AutoConfigureMockMvc
public class IndustryUIControllerTest {

    private final static String CONTENT = """
            {
                "id": 99999,
                "name": "testSave"
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testGetAllPermission() throws Exception {
        mockMvc.perform(get("/ui/contractor/industry/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testGetAllDenial() throws Exception {
        mockMvc.perform(get("/ui/contractor/industry/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testGetPermission() throws Exception {
        mockMvc.perform(get("/ui/contractor/industry/99998"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithAnonymousUser
    public void testGetDenial() throws Exception {
        mockMvc.perform(get("/ui/contractor/industry/99999"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", authorities = "CONTRACTOR_SUPERUSER")
    public void testSavePermission() throws Exception {
        mockMvc.perform(put("/ui/contractor/industry/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CONTENT))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testSaveDenial() throws Exception {
        mockMvc.perform(put("/ui/contractor/industry/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CONTENT))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", authorities = "CONTRACTOR_SUPERUSER")
    public void testDeletePermission() throws Exception {
        mockMvc.perform(delete("/ui/contractor/industry/delete/99998"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testDeleteDenial() throws Exception {
        mockMvc.perform(delete("/ui/contractor/industry/delete/99999"))
                .andExpect(status().isForbidden());
    }

}
