package ee.gaile.tuum.model.response;

import ee.gaile.tuum.model.enums.CurrencyEnum;
import ee.gaile.tuum.model.enums.TransactionDirectionEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Aleksei Gaile 20-Apr-24
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TransactionResponse {

    private Long accountId;

    private Long transactionId;

    private BigDecimal amount;

    private CurrencyEnum currency;

    private TransactionDirectionEnum direction;

    private String description;

    private BigDecimal balance;

}
