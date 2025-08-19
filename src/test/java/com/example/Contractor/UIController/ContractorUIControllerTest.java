package com.example.Contractor.UIController;

import com.example.Contractor.Controller.ContractorController;
import com.example.Contractor.ContextSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@ExtendWith(ContextSetup.class)
@AutoConfigureMockMvc
public class ContractorUIControllerTest {

    @MockitoBean
    private ContractorController controllerController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "USER")
    public void testGetPermission() throws Exception {
        mockMvc.perform(get("/ui/contractor/testGet")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testGetDenial() throws Exception {
        mockMvc.perform(get("/ui/contractor/testGet")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CONTRACTOR_SUPERUSER")
    public void testSavePermission() throws Exception {
        mockMvc.perform(put("/ui/contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testSaveDenial() throws Exception {
        mockMvc.perform(put("/ui/contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CONTRACTOR_SUPERUSER")
    public void testDeletePermission() throws Exception {
        mockMvc.perform(delete("/ui/contractor/delete/testDelete"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testDeleteDenial() throws Exception {
        mockMvc.perform(delete("/ui/contractor/delete/testDelete"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CONTRACTOR_SUPERUSER")
    public void testSearchPermission1() throws Exception {
        mockMvc.perform(post("/ui/contractor/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"testSearch\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "CONTRACTOR_RUS")
    public void testSearchPermission2() throws Exception {
        mockMvc.perform(post("/ui/contractor/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"country\":\"RUS\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "CONTRACTOR_RUS")
    public void testSearchDenial1() throws Exception {
        mockMvc.perform(post("/ui/contractor/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"country\":\"ABC\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testSearchDenial2() throws Exception {
        mockMvc.perform(post("/ui/contractor/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"testSearch\"}"))
                .andExpect(status().isForbidden());
    }

}