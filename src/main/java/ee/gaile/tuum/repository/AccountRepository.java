package ee.gaile.tuum.repository;

import ee.gaile.tuum.model.request.AccountRequest;
import ee.gaile.tuum.model.response.Account;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * @author Aleksei Gaile 18-Apr-24
 */
@Mapper
public interface AccountRepository {

    @Select("INSERT INTO account (customer_id, country)" +
            " VALUES (#{customerId}, #{country}) RETURNING account_id")
    Long insertAccount(AccountRequest account);

    @Select("SELECT * FROM account WHERE account_id = #{accountId}")
    @Results(id = "accountResultMap", value = {
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "balances", column = "account_id",
                    javaType = List.class,
                    many = @Many(select = "ee.gaile.tuum.repository.BalanceRepository.getBalanceByAccountId"))
    })
    Optional<Account> getAccountById(Long accountId);

}
