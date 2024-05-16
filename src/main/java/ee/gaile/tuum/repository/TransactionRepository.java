package ee.gaile.tuum.repository;

import ee.gaile.tuum.model.response.TransactionHistoryResponse;
import ee.gaile.tuum.model.response.TransactionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Aleksei Gaile 20-Apr-24
 */
@Mapper
public interface TransactionRepository {

    @Select("INSERT INTO transaction (account_id, amount, currency, direction,description)" +
            " VALUES (#{accountId}, #{amount}, #{currency}::currency_enum, #{direction}::direction_enum," +
            "#{description}) RETURNING id")
    Long insertTransaction(TransactionResponse transaction);

    @Select("SELECT * FROM transaction WHERE account_id = #{accountId}")
    @Results({
            @Result(property = "transactionId", column = "id"),
            @Result(property = "accountId", column = "account_id")})
    List<TransactionHistoryResponse> getAccountTransactions(Long accountId);

}
