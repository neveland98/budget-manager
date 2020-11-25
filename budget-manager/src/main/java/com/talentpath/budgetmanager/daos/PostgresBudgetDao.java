package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Component
@Profile({"daotesting","production"})
public class PostgresBudgetDao implements BudgetDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Transaction> getAllTransactions(Integer userId) {
        return template.query("SELECT * FROM \"Transactions\" WHERE \"userId\" = "+ userId +";",new TransactionMapper());
    }

    @Override
    public Integer addTransaction(Transaction userTransaction) {
        return template.queryForObject("insert into \"Transactions\" " +
                "(\"amount\",\"userId\",\"charge\",\"description\") " +
                "values ('"+ userTransaction.getAmount() +"','"+ userTransaction.getUserId() +"','"+ userTransaction.isCharge() +"','"+ userTransaction.getDescription() +"') " +
                "returning \"transactionId\";",new IdMapper());
    }

    @Override
    public Transaction getTransactionById(Integer transactionId) {
        throw new UnsupportedOperationException();
    }

    private class TransactionMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
            Transaction toReturn = new Transaction();
            toReturn.setTransactionId(resultSet.getInt("transactionId"));
            toReturn.setUserId(resultSet.getInt("userId"));
            toReturn.setCharge(resultSet.getBoolean("charge"));
            toReturn.setAmount(BigInteger.valueOf(resultSet.getLong("amount")));
            toReturn.setDescription(resultSet.getString("description"));
            Date date = resultSet.getDate("date");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            toReturn.setDate(calendar);
            return toReturn;
        }
    }

    private class IdMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("transactionId");
        }
    }
}
