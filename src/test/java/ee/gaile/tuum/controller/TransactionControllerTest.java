package ee.gaile.tuum.controller;

import ee.gaile.tuum.UtilsTests;
import ee.gaile.tuum.model.request.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static ee.gaile.tuum.model.enums.CurrencyEnum.EUR;
import static ee.gaile.tuum.model.enums.TransactionDirectionEnum.IN;
import static ee.gaile.tuum.model.enums.TransactionDirectionEnum.OUT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/sql/insert-to-account.sql"})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class TransactionControllerTest extends DbContainer {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        doNothing().when(rabbitTemplate).convertAndSend(any(String.class), any(String.class), any(Object.class));
    }

    @Test
    void makeAndGetTransaction_whenValidData() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(50L);
        transactionRequest.setAmount(BigDecimal.valueOf(10));
        transactionRequest.setCurrency(EUR);
        transactionRequest.setTransactionDirection(IN);
        transactionRequest.setDescription("transfer");

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UtilsTests.asJsonString(transactionRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(50L))
                .andExpect(jsonPath("$.balance").value(20.00))
                .andReturn();

        mockMvc.perform(get("/transaction/50")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId").value(50L))
                .andReturn();
    }

    @Test
    void makeAndGetTransaction_IN_direction() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(60L);
        transactionRequest.setAmount(BigDecimal.valueOf(10));
        transactionRequest.setCurrency(EUR);
        transactionRequest.setTransactionDirection(IN);
        transactionRequest.setDescription("transfer");

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UtilsTests.asJsonString(transactionRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(60L))
                .andExpect(jsonPath("$.balance").value(40.00))
                .andReturn();

    }

    @Test
    void makeAndGetTransaction_OUT_direction() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(70L);
        transactionRequest.setAmount(BigDecimal.valueOf(10));
        transactionRequest.setCurrency(EUR);
        transactionRequest.setTransactionDirection(OUT);
        transactionRequest.setDescription("transfer");

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UtilsTests.asJsonString(transactionRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(70L))
                .andExpect(jsonPath("$.balance").value(10.00))
                .andReturn();

    }

    @Test
    void makeAndGetTransaction_OUT_direction_thenBalanceNotEnough() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(70L);
        transactionRequest.setAmount(BigDecimal.valueOf(100));
        transactionRequest.setCurrency(EUR);
        transactionRequest.setTransactionDirection(OUT);
        transactionRequest.setDescription("transfer");

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UtilsTests.asJsonString(transactionRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not enough money"))
                .andReturn();
    }

}
