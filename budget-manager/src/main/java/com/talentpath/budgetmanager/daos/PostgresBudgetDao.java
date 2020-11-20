package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Profile({"daotesting","production"})
public class PostgresBudgetDao implements BudgetDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> allTransactions = template.query("SELECT * FROM \"Transactions\";",new TransactionMapper());
        return allTransactions;
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
            toReturn.setAmount(resultSet.getDouble("amount"));
            toReturn.setDescription((resultSet.getString("description")));
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
