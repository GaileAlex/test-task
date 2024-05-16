package ee.gaile.tuum.model.response;

import ee.gaile.tuum.model.enums.CurrencyEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Aleksei Gaile 17-Apr-24
 */
@Getter
@Setter
@NoArgsConstructor
public class BalanceResponse {

    private BigDecimal balance;

    private CurrencyEnum currencyType;

}
