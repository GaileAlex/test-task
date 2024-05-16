package ee.gaile.tuum.controller;

import ee.gaile.tuum.UtilsTests;
import ee.gaile.tuum.model.enums.CurrencyEnum;
import ee.gaile.tuum.model.request.AccountRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
class AccountControllerTest extends DbContainer {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAccount_whenValidData() throws Exception {
        AccountRequest accountRequest = new AccountRequest();
        List<CurrencyEnum> currencyTypes = List.of(CurrencyEnum.USD, CurrencyEnum.EUR);
        accountRequest.setCustomerId(12L);
        accountRequest.setCountry("Estonia");
        accountRequest.setCurrencyTypes(currencyTypes);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UtilsTests.asJsonString(accountRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").isNotEmpty())
                .andExpect(jsonPath("$.customerId").value(12L))
                .andReturn();
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = {"/sql/insert-to-account-id-11.sql"})
    void getAccount_whenValidData() throws Exception {
        mockMvc.perform(get("/account/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").isNotEmpty())
                .andReturn();
    }

    @Test
    void getAccount_whenAccountIdIsNotValid() throws Exception {
        mockMvc.perform(get("/account/132")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andReturn();
    }

}
