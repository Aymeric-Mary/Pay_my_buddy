package com.openclassrooms.pay_my_buddy.IT.user;

import com.openclassrooms.pay_my_buddy.DataTools;
import com.openclassrooms.pay_my_buddy.model.User;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddConnectionIT extends DataTools {

    @Test
    @WithMockUser(username = "test@gmail.com")
    void shouldAddConnection() throws Exception {
        // Given
        User connectedUser = createUser("test@gmail.com");
        User connection = createUser("connection@gmail.com");
        // When
        mockMvc.perform(post("/users/mine/connections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "connectionId":%d
                                     }
                                """.formatted(connection.getId())))
                // Then
                .andExpect(status().isNoContent())
                .andReturn();
        entityManager.clear();
        assertThat(connectedUser.getConnections()).contains(connection);
    }

    @Test
    @WithMockUser(username = "test@gmail.com")
    void shouldAddConnection2() throws Exception {
        // Given
        User connectedUser = createUser("test@gmail.com");
        // When
        MvcResult result = mockMvc.perform(post("/users/mine/connections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "connectionId":0
                                     }
                                """))
                // Then
                .andExpect(status().isNotFound())
                        .andReturn();
        JSONAssert.assertEquals("""
                    {
                        "error":"NO_SUCH_RESOURCE",
                        "resource":"User",
                        "id":0
                    }
                """, result.getResponse().getContentAsString(), true);
    }
}
