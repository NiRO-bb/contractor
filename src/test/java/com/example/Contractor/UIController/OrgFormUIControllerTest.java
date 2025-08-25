package com.example.Contractor.UIController;

import com.example.Contractor.Controller.OrgFormController;
import com.example.Contractor.AbstractContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrgFormUIControllerTest extends AbstractContainer {

    @MockitoBean
    private OrgFormController orgFormController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testGetAllPermission() throws Exception {
        mockMvc.perform(get("/ui/contractor/org_form/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testGetAllDenial() throws Exception {
        mockMvc.perform(get("/ui/contractor/org_form/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testGetPermission() throws Exception {
        mockMvc.perform(get("/ui/contractor/org_form/99998"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testGetDenial() throws Exception {
        mockMvc.perform(get("/ui/contractor/org_form/99999"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", authorities = "CONTRACTOR_SUPERUSER")
    public void testSavePermission() throws Exception {
        mockMvc.perform(put("/ui/contractor/org_form/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testSaveDenial() throws Exception {
        mockMvc.perform(put("/ui/contractor/org_form/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", authorities = "CONTRACTOR_SUPERUSER")
    public void testDeletePermission() throws Exception {
        mockMvc.perform(delete("/ui/contractor/org_form/delete/99998"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    public void testDeleteDenial() throws Exception {
        mockMvc.perform(delete("/ui/contractor/org_form/delete/99999"))
                .andExpect(status().isForbidden());
    }

}
