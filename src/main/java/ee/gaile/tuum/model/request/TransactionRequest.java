package ee.gaile.tuum.model.request;

import ee.gaile.tuum.model.enums.CurrencyEnum;
import ee.gaile.tuum.model.enums.TransactionDirectionEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Aleksei Gaile 20-Apr-24
 */
@Getter
@Setter
@NoArgsConstructor
public class TransactionRequest {

    @NotNull(message = "customerId cannot be null")
    private Long accountId;

    @NotNull(message = "amount cannot be null")
    private BigDecimal amount;

    @NotNull(message = "currency cannot be null")
    private CurrencyEnum currency;

    @NotNull(message = "direction cannot be null")
    private TransactionDirectionEnum transactionDirection;

    @NotNull(message = "description cannot be null")
    private String description;

}
