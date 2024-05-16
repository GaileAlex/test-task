package ee.gaile.tuum.model.response;

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
public class Account {

    private Long accountId;

    private Long customerId;

    private List<BalanceResponse> balances;

}
