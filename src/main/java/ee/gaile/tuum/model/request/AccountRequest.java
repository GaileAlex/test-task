package ee.gaile.tuum.model.request;

import ee.gaile.tuum.model.enums.CurrencyEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Aleksei Gaile 17-Apr-24
 */
@Getter
@Setter
@NoArgsConstructor
public class AccountRequest {

    @NotNull(message = "customerId cannot be null")
    private Long customerId;

    @NotNull(message = "country cannot be null")
    private String country;

    @NotEmpty(message = "currency types cannot be empty")
    private List<CurrencyEnum> currencyTypes;

}
