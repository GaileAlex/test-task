package ee.gaile.tuum.repository;

import ee.gaile.tuum.model.enums.CurrencyEnum;
import ee.gaile.tuum.model.response.BalanceResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Aleksei Gaile 18-Apr-24
 */
@Mapper
public interface BalanceRepository {

    @Insert("INSERT INTO balance (account_id, currency_type)" +
            " VALUES (#{accountId}, #{currencyType}::currency_enum)")
    void insertCurrencyType(Long accountId, CurrencyEnum currencyType);


    @Select("SELECT * FROM balance WHERE account_id = #{accountId}")
    @Results({
            @Result(property = "balance", column = "balance"),
            @Result(property = "currencyType", column = "currency_type")})
    List<BalanceResponse> getBalanceByAccountId(Long accountId);

    @Update("UPDATE balance SET balance = #{balance} WHERE account_id = #{accountId} AND currency_type = #{currencyType}::currency_enum")
    void updateAccountBalance(Long accountId, CurrencyEnum currencyType, BigDecimal balance);

}
