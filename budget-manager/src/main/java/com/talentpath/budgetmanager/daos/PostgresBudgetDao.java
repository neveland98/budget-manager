package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.exceptions.*;
import com.talentpath.budgetmanager.models.Category;
import com.talentpath.budgetmanager.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
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
    public void reset() {
        template.update("TRUNCATE public.\"Transactions\", public.\"categories\", public.\"user_roles\", public.\"users\" RESTART IDENTITY;");
        template.update("ALTER SEQUENCE public.\"Transactions_transactionId_seq\" RESTART;\n" +
                "ALTER SEQUENCE public.\"categories_category_id_seq\" RESTART;\n" +
                "ALTER SEQUENCE public.\"users_user_id_seq\" RESTART;");
    }


    @Override
    public List<Transaction> getAllTransactions(Integer userId) throws NullArgumentException {
        if(userId == null) throw new NullArgumentException("Null userId passed to getAllTransactions in PostgresBudgetDao.");
        return template.query("SELECT \"transactionId\",\"userId\",\"charge\",\"description\",\"amount\",\"date\",\"associated_category_id\",\"category_name\"" +
                " FROM \"Transactions\" t, \"categories\" c" +
                " WHERE \"userId\" = '"+ userId +"'" +
                " AND t.\"associated_category_id\" = c.\"category_id\"" +
                " ORDER BY \"date\" ASC;",new TransactionMapper());
    }

    @Override
    public BigInteger getRunningTotal(Integer userId) throws NullArgumentException {
        if(userId == null) throw new NullArgumentException("Null userId parameter passed to getRunningTotal in PostgresBudgetDao.");
        List<BigInteger> totals = template.query("select sum(amount),charge from \"Transactions\" where \"userId\"='"+ userId +"'\n" +
                "group by charge\n" +
                "order by charge DESC;",new TotalMapper());//order by charge desc, means that charge is first and not charge is second
        BigInteger toReturn = new BigInteger("0");
        for(BigInteger number:totals) {
            toReturn = toReturn.add(number);
        }
        return toReturn;
    }

    @Override
    public Integer addTransaction(Transaction userTransaction) throws NullArgumentException, NullParameterException, InvalidArgumentException {
        if(userTransaction == null) throw new NullArgumentException("Null userTransaction passed to addTransaction in PostgresBudgetDao");
        else if(userTransaction.getUserId()==null||userTransaction.getAmount()==null||userTransaction.getCategory()==null||userTransaction.getDescription()==null||userTransaction.getDate()==null)
            throw new NullParameterException("One or more parameters in userTransaction passed to addTransaction in PostgresBudgetDao is null.");
        else if(userTransaction.getDescription().equals(""))
            throw new InvalidArgumentException("Blank description in userTransaction passed to addTransaction in PostgresBudgetDao.");

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
    public Integer editTransaction(Transaction updated) throws NullArgumentException, NullParameterException, InvalidArgumentException {
        if(updated == null) throw new NullArgumentException("Null updated transaction parameter passed to editTransaction in PostgresBudgetDao.");
        else if(updated.getUserId()==null||updated.getAmount()==null||updated.getDescription()==null||updated.getCategory()==null||updated.getDate()==null)
            throw new NullParameterException("One or more parameters in Transaction passed to editTransaction in PostgresBudgetDao is null.");
        else if(!updated.getCategory().isValid()) throw new InvalidArgumentException("Invalid category passed in updated Transaction in editTransaction in postgresBudgetDao");
        return template.queryForObject("update \"Transactions\"\n" +
                "set charge = '"+ updated.isCharge() +"', " +
                "description = '"+ updated.getDescription() +"', " +
                "amount = '"+ updated.getAmount() +"', " +
                "\"date\" = '"+ new Date(updated.getDate().getTimeInMillis()) +"', " +
                "associated_category_id = '" + updated.getCategory().getCategoryId() + "' " +
                "where \"transactionId\" = '"+ updated.getTransactionId() +"' returning \"transactionId\";",new IdMapper());
    }

    @Override
    public void deleteTransactionById(Integer transactionId) throws BudgetDaoException, NullArgumentException {
        if(transactionId == null) throw new NullArgumentException("Null transactionId passed to deleteTransactionById in PostgresBudgetDao.");
        try {
            template.update("DELETE FROM \"Transactions\" WHERE \"transactionId\" = '"+ transactionId +"';");
        }
        catch(DataAccessException e) {
            throw new BudgetDaoException("No transaction with id: " + transactionId);
        }
    }

    @Override
    public Transaction getTransactionById(Integer transactionId) throws BudgetDaoException, NullArgumentException {
        if(transactionId == null) throw new NullArgumentException("Null transactionId passed to getTransactionById in PostgresBudgetDao.");
        try {
            return template.queryForObject("SELECT \"transactionId\",\"userId\",\"charge\",\"description\",\"amount\",\"date\",\"associated_category_id\",\"category_name\"" +
                    " FROM \"Transactions\" t, \"categories\" c" +
                    " WHERE \"transactionId\" = '"+ transactionId +"'" +
                    " AND t.\"associated_category_id\" = c.\"category_id\"" +
                    " ORDER BY \"date\" ASC;",new TransactionMapper());
        }
        catch (DataAccessException e) {
            throw new BudgetDaoException("No transaction with id: " + transactionId);
        }
    }

    @Override
    public Category addCategory(Category userCategory) {
        Integer id = template.queryForObject("insert into \"categories\" (\"category_name\",\"user_id\",\"associated_user_user_id\") values ('"+ userCategory.getCategoryName() +"','"+ userCategory.getUser_id() +"','"+ userCategory.getUser_id() +"') returning category_id;",new CategoryIdMapper());
        userCategory.setCategoryId(id);
        return userCategory;
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
    public Integer editCategory(Category updated) throws NullArgumentException {
        if(updated == null) throw new NullArgumentException("Null updated Category passed to editCategory in PostgresBudgetDao.");
        return template.queryForObject("update categories set category_name='"+ updated.getCategoryName() +"' where category_id = '"+ updated.getCategoryId() +"' returning category_id;",new CategoryIdMapper());
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        template.update("DELETE FROM categories WHERE category_id = "+ categoryId +";");
    }

    @Override
    public BigInteger getMonthlyTotal(Integer userId, String dateString) {//java.sql.Date don't do me wrong lol
        List<BigInteger> totals = template.query(
                "select sum(amount),\"charge\" " +
                        "from \"Transactions\" " +
                        "where \"userId\" = "+ userId +" and EXTRACT(MONTH from date) = '"+ dateString.substring(5,7) +"' and EXTRACT(YEAR from date) = '"+ dateString.substring(0,4) +"'\n" +
                        "group by \"charge\"\n" +
                        "order by \"charge\" DESC;",new TotalMapper());
        BigInteger toReturn = new BigInteger("0");
        for(BigInteger number:totals) {
            toReturn = toReturn.add(number);
        }
        return toReturn;
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
            //System.out.println("SQL get date ms time: " + date.getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            //System.out.println("Calendar get date ms time: " + calendar.getTimeInMillis());
            toReturn.setDate(calendar);
            Category toSet = new Category();
            toSet.setCategoryId(resultSet.getInt("associated_category_id"));
            toSet.setCategoryName(resultSet.getString("category_name"));
            toSet.setUser_id(resultSet.getInt("userId"));
            toReturn.setCategory(toSet);
            //instead of doing this query, we have changed the queries that uses this mapper to include the columns we need from categories automatically.
//            toReturn.setCategory(template.queryForObject("select * from categories where category_id = '"+ resultSet.getInt("associated_category_id") +"';",new CategoryMapper()));
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
