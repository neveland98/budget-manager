package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.exceptions.BudgetDaoException;
import com.talentpath.budgetmanager.models.Category;
import com.talentpath.budgetmanager.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

@Component
@Profile({"daotesting","production"})
public class PostgresBudgetDao implements BudgetDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Transaction> getAllTransactions(Integer userId) {
        return template.query("SELECT * FROM \"Transactions\" WHERE \"userId\" = "+ userId +" ORDER BY date ASC;",new TransactionMapper());
    }

    @Override
    public BigInteger getRunningTotal(Integer id) {
        List<BigInteger> totals = template.query("select sum(amount),charge from \"Transactions\" where \"userId\"='"+ id +"'\n" +
                "group by charge\n" +
                "order by charge DESC;",new TotalMapper());//order by charge desc, means that charge is first and not charge is second
        BigInteger toReturn = new BigInteger("0");
        for(BigInteger number:totals) {
            toReturn = toReturn.add(number);
        }
        return toReturn;
    }

    @Override
    public Integer addTransaction(Transaction userTransaction) {
        return template.queryForObject("insert into \"Transactions\" " +
                "(\"amount\",\"userId\",\"charge\",\"description\",\"date\",\"associated_category_id\") " +
                "values ('"+ userTransaction.getAmount() +"','"+
                userTransaction.getUserId() +"','"+
                userTransaction.isCharge() +"','"+
                userTransaction.getDescription() +"','"+
                new Date(userTransaction.getDate().getTimeInMillis())+"','" +
                userTransaction.getCategory().getCategoryId()+"') " +
                "returning \"transactionId\";",new IdMapper());
    }

    @Override
    public Integer editTransaction(Transaction updated) {
        return template.queryForObject("update \"Transactions\"\n" +
                "set charge = '"+ updated.isCharge() +"', " +
                "description = '"+ updated.getDescription() +"', " +
                "amount = '"+ updated.getAmount() +"', " +
                "\"date\" = '"+ new Date(updated.getDate().getTimeInMillis()) +"', " +
                "associated_category_id = '" + updated.getCategory().getCategoryId() + "' " +
                "where \"transactionId\" = '"+ updated.getTransactionId() +"' returning \"transactionId\";",new IdMapper());
    }

    @Override
    public void deleteTransactionById(Integer id) throws BudgetDaoException {
        try {
            template.update("DELETE FROM \"Transactions\" WHERE \"transactionId\" = '"+ id +"';");
        }
        catch(DataAccessException e) {
            throw new BudgetDaoException("No transaction with id: " + id);
        }
    }

    @Override
    public Transaction getTransactionById(Integer transactionId) throws BudgetDaoException {
        try {
            return template.queryForObject("select * from \"Transactions\" where \"transactionId\" = "+ transactionId +";",new TransactionMapper());
        }
        catch (DataAccessException e) {
            throw new BudgetDaoException("No transaction with id: " + transactionId);
        }
    }

    @Override
    public Category addCategory(Category userCategory) throws BudgetDaoException {
        Integer id = template.queryForObject("insert into \"categories\" (\"category_name\",\"user_id\",\"associated_user_user_id\") values ('"+ userCategory.getCategoryName() +"','"+ userCategory.getUser_id() +"','"+ userCategory.getUser_id() +"') returning category_id;",new CategoryIdMapper());
        return getCategoryById(id);
    }

    @Override
    public Category getCategoryById(Integer categoryId) throws BudgetDaoException {
        try {
            return template.queryForObject("select * from \"categories\" where \"category_id\" = "+ categoryId +";",new CategoryMapper());
        }
        catch(DataAccessException e) {
            throw new BudgetDaoException("No category with id: " + categoryId);
        }
    }

    @Override
    public Integer editCategory(Category updated) throws BudgetDaoException {
        try {
            return template.queryForObject("update categories set category_name='"+ updated.getCategoryName() +"' where category_id = '"+ updated.getCategoryId() +"' returning category_id;",new CategoryIdMapper());
        }
        catch (DataAccessException e) {
            throw new BudgetDaoException("No category with id: " + updated.getCategoryId());
        }
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        template.update("DELETE FROM categories WHERE category_id = "+ categoryId +";");
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
            toReturn.setCategory(template.queryForObject("select * from categories where category_id = '"+ resultSet.getInt("associated_category_id") +"';",new CategoryMapper()));
            return toReturn;
        }
    }

    private class IdMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("transactionId");
        }
    }

    private class CategoryIdMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("category_id");
        }
    }

    private class CategoryMapper implements RowMapper <Category>{
        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException {
            Category toReturn = new Category();
            toReturn.setCategoryId(resultSet.getInt("category_id"));
            toReturn.setCategoryName(resultSet.getString("category_name"));
            toReturn.setUser_id(resultSet.getInt("user_id"));
            return toReturn;
        }
    }
    private class TotalMapper implements RowMapper<BigInteger> {
        @Override
        public BigInteger mapRow(ResultSet resultSet, int i) throws SQLException {
            boolean charge = resultSet.getBoolean("charge");
            BigInteger sum = BigInteger.valueOf(resultSet.getLong("sum"));
            if(charge) sum = sum.multiply(BigInteger.valueOf(-1L));
            return sum;
        }
    }
}
