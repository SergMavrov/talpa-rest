package com.talpasolutions.maverick.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talpasolutions.maverick.protocol.SimpleLocation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
public class DronControllerTest extends GeneralConfigurationTest {

    protected static final String SLASH = "/";
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    public MockMvc mockMvc;

    @Test
    public void testEmptyRoutes() throws Exception{
        ResultActions postResult = calculateRoutes(new ArrayList<>());
        postResult
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRoutesWithoutCustomers() throws Exception{
        ResultActions postResult = calculateRoutes(Arrays.asList(givenTestDepot1(), givenTestStore1()));
        postResult
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRoutesWithoutStores() throws Exception{
        ResultActions postResult = calculateRoutes(Arrays.asList(givenTestDepot1(), givenTestCustomer1()));
        postResult
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRoutesWithoutDepots() throws Exception{
        ResultActions postResult = calculateRoutes(Arrays.asList(givenTestStore1(), givenTestCustomer1()));
        postResult
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRoutesTestData() throws Exception{
        ResultActions postResult = calculateRoutes(Arrays.asList(givenTestDepot1(), givenTestDepot2(),
                givenTestStore1(), givenTestStore2(), givenTestCustomer1()));
        postResult
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].customer.name", is(equalTo(givenTestCustomer1().getName()))))
                .andExpect(jsonPath("$.[0].store.name", is(equalTo(givenTestStore1().getName()))))
                .andExpect(jsonPath("$.[0].depot.name", is(equalTo(givenTestDepot1().getName()))));
    }

    protected ResultActions calculateRoutes(List<SimpleLocation> locationRoutes) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        String jsonParam = asJson(locationRoutes);
        log.error("Routes -> " + jsonParam);
        return mockMvc.perform(post(SLASH +"routes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam));
    }

    public String asJson(Object object) throws JsonProcessingException {
        return this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(object);
    }

}
