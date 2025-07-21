package com.example.Contractor.UIController;

import com.example.Contractor.Controller.CountryController;
import com.example.Contractor.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@ExtendWith(DatabaseSetup.class)
@AutoConfigureMockMvc
public class CountryUIControllerTest {

    @MockitoBean
    private CountryController countryController;

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testGetAllPermission() throws Exception {
        mockMvc.perform(get("/ui/contractor/country/all"))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithAnonymousUser
    public void testGetAllDenial() throws Exception {
        mockMvc.perform(get("/ui/contractor/country/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testGetPermission() throws Exception {
        mockMvc.perform(get("/ui/contractor/country/testGet"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testGetDenial() throws Exception {
        mockMvc.perform(get("/ui/contractor/country/testGet"))
                .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(username = "user", authorities = "CONTRACTOR_SUPERUSER")
    public void testSavePermission() throws Exception {
        mockMvc.perform(put("/ui/contractor/country/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testSaveDenial() throws Exception {
        mockMvc.perform(put("/ui/contractor/country/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(username = "user", authorities = "CONTRACTOR_SUPERUSER")
    public void testDeletePermission() throws Exception {
        mockMvc.perform(delete("/ui/contractor/country/delete/testDelete"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testDeleteDenial() throws Exception {
        mockMvc.perform(delete("/ui/contractor/country/delete/testDelete"))
                .andExpect(status().isForbidden());
    }
    
}
