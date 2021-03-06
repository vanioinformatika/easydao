```java
// GENERATED FILE, DO NOT MODIFY! YOUR MODIFICATION WILL BE LOST!
/** No license specified */
package hu.vanio.easydao.sample.dao.sampledb;

import java.math.BigDecimal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import hu.vanio.easydao.sample.model.sampledb.Customer;


/**
 * CustomerDao.
 * <br>Customer table
 * <br>Generated from cus_customer database table.
 * <br>Created on: 2015-11-25 17:59:20.007
 * <br>Database name: sampledb
 * <br>Generated by easydao-maven-plugin v2.0.2-SNAPSHOT
 */
@Repository
public class CustomerDao implements hu.vanio.easydao.core.Dao<Customer, Long> {

    /** Selected fields of the database table */
    static final public String SELECTED_FIELDS = "cus_pk, cus_adr_pk, cus_name, cus_email, cus_type, cus_reg_ts, cus_locked, cus_modificaton_ts";

    /** Datasource that can be used for acquiring SQL connections */
    protected DataSource dataSource;
    /** Runs SQL operations */
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("sampledb") DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Reads a domain object with the specified primary key from the datastore
     * @param pk Customer unique ID
     * @param readLobFields Specifies whether BLOB/CLOB fields has to be read from the datastore
     * @return Customer instance
     */
    @Override
    public Customer read(Long pk, boolean readLobFields) {
        String query = "select " + SELECTED_FIELDS + " from cus_customer where cus_pk = ?";
        Customer retVal = this.jdbcTemplate.queryForObject(query, new CustomerRowMapper(readLobFields), pk);
        return retVal;
    }

    /**
     * Reads all instances of the domain object from the datastore
     * @param readLobFields Specifies whether BLOB/CLOB fields has to be read from the datastore
     * @return All instances of the domain object
     */
    @Override
    public List<Customer> readAll(boolean readLobFields) {
        String query = "select " + SELECTED_FIELDS + " from cus_customer";
        List<Customer> retVal = this.jdbcTemplate.query(query, new CustomerRowMapper(readLobFields));
        return retVal;
    }

    /**
     * Reads a domain object with the specified keys from the datastore
     * @param pk Customer unique ID
     * @param readLobFields Specifies whether BLOB/CLOB fields has to be read from the datastore
     * @return Customer instance
     */
    public Customer readIndexed_PkKey(Long pk, boolean readLobFields) {
        String query = "select " + SELECTED_FIELDS + " from cus_customer where "
                + "cus_pk = ? ";

        List params = new java.util.ArrayList(1);
                params.add(pk);

        Customer retVal = this.jdbcTemplate.queryForObject(query, new CustomerRowMapper(readLobFields), params.toArray());
        return retVal;
    }
    /**
     * Reads a domain object with the specified keys from the datastore
     * @param name Customer name
     * @param readLobFields Specifies whether BLOB/CLOB fields has to be read from the datastore
     * @return Customer instance
     */
    public Customer readIndexed_NameKey(String name, boolean readLobFields) {
        String query = "select " + SELECTED_FIELDS + " from cus_customer where "
                + "cus_name = ? ";

        List params = new java.util.ArrayList(1);
                params.add(name);

        Customer retVal = this.jdbcTemplate.queryForObject(query, new CustomerRowMapper(readLobFields), params.toArray());
        return retVal;
    }
    /**
     * Reads a list of domain objects with the specified keys from the datastore
     * @param name Customer name
     * @param readLobFields Specifies whether BLOB/CLOB fields has to be read from the datastore
     * @return Customer instances
     */
    public List<Customer> readIndexed_CustomerCusNameIdx(String name, boolean readLobFields) {
        String query = "select " + SELECTED_FIELDS + " from cus_customer where "
                + "cus_name = ? ";
        
        List params = new java.util.ArrayList(1);
                params.add(name);

        List<Customer> retVal = this.jdbcTemplate.query(query, new CustomerRowMapper(readLobFields), params.toArray());
        return retVal;
    }
    /**
     * Reads a domain object with the specified keys from the datastore
     * @param email FIXME: Warning: There is no comment in database!
     * @param readLobFields Specifies whether BLOB/CLOB fields has to be read from the datastore
     * @return Customer instance
     */
    public Customer readIndexed_EmailKey(String email, boolean readLobFields) {
        String query = "select " + SELECTED_FIELDS + " from cus_customer where "
                + "cus_email = ? ";

        List params = new java.util.ArrayList(1);
                params.add(email);

        Customer retVal = this.jdbcTemplate.queryForObject(query, new CustomerRowMapper(readLobFields), params.toArray());
        return retVal;
    }
    /**
     * Reads a list of domain objects with the specified keys from the datastore
     * @param email FIXME: Warning: There is no comment in database!
     * @param readLobFields Specifies whether BLOB/CLOB fields has to be read from the datastore
     * @return Customer instances
     */
    public List<Customer> readIndexed_CustomerCusEmailIdx(String email, boolean readLobFields) {
        String query = "select " + SELECTED_FIELDS + " from cus_customer where "
                + "cus_email = ? ";
        
        List params = new java.util.ArrayList(1);
                params.add(email);

        List<Customer> retVal = this.jdbcTemplate.query(query, new CustomerRowMapper(readLobFields), params.toArray());
        return retVal;
    }

    /**
     * Creates a new primary key instance on the specified domain object instance
     * @param instance The domain object instance
     */
    @Override
    public void createPk(final Customer instance) {
        throw new IllegalStateException("Missing sequence: cus_customer_SEQ");
    }

    /**
     * Creates a new domain object in the datastore based on the specified instance
     * @param instance The domain object instance
     * @param createPk Indicates whether a new primary key needs to be created
     * @return The re-read updated domain object instance (it needs to be re-read because of the computed fields)
     */
    @Override
    public Customer create(Customer instance, boolean createPk) {
        String sql = "insert into cus_customer (cus_pk,cus_adr_pk,cus_name,cus_email,cus_type,cus_reg_ts,cus_locked,cus_modificaton_ts) values (?,?,?,?,?,?,?,?)";
        if (createPk) createPk(instance);
        Object[] params = new Object[] {
                        instance.getPk(),
                        instance.getAddressPk(),
                        instance.getName(),
                        instance.getEmail(),
                        instance.getType(),
                        instance.getRegTs(),
                        instance.getLocked(),
                        instance.getModificatonTs()
        };
        this.jdbcTemplate.update(sql, params);

            return read(instance.getPk(), false);
    }

    /**
     * Updates the specified domain object instance
     * @param instance The domain object instance
     * @return The re-read updated domain object instance (it needs to be re-read because of the computed fields)
     */
    @Override
    public Customer update(Customer instance, boolean updateLobFields) {
        String sql = "update cus_customer " +
                     "set " +
                     "    cus_adr_pk = ? , " +
                     "    cus_name = ? , " +
                     "    cus_email = ? , " +
                     "    cus_type = ? , " +
                     "    cus_reg_ts = ? , " +
                     "    cus_locked = ? , " +
                     "    cus_modificaton_ts = ? " +
                     "where cus_pk = ?";

            Object[] params = new Object[] {
                        instance.getAddressPk(),
                        instance.getName(),
                        instance.getEmail(),
                        instance.getType(),
                        instance.getRegTs(),
                        instance.getLocked(),
                        instance.getModificatonTs(),
                instance.getPk()
            };

        int updRows = this.jdbcTemplate.update(sql, params);
        if (updRows != 1) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(sql, 1, updRows);
        }

            return read(instance.getPk(), false);
    }

    /**
     * Deletes the domain object instance specified with its primary key
     * @param pk Customer unique ID
     */
    @Override
    public void delete(Long pk) {
        String sql = "delete from cus_customer where cus_pk = ?";
        int updRows = this.jdbcTemplate.update(sql, pk);
        if (updRows != 1) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(sql, 1, updRows);
        }
    }

    /** RowMapper implementation */
    protected class CustomerRowMapper implements RowMapper<Customer> {
        
        /** Specifies whether BLOB/CLOB fields has to be read from the datastore */
        private final boolean readLobFields;
        
        /**
         * Constructs a new instance
         * @param readLobFields Specifies whether BLOB/CLOB fields has to be read from the datastore
         */
        public CustomerRowMapper(boolean readLobFields) {
            this.readLobFields = readLobFields;
        }

        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            String tmp;
            
                    Long pk = (tmp = rs.getString("cus_pk")) != null ? new Long(tmp) : null;

                    Long addressPk = (tmp = rs.getString("cus_adr_pk")) != null ? new Long(tmp) : null;

                        String name = rs.getString("cus_name");

                        String email = rs.getString("cus_email");

                        String type = rs.getString("cus_type");

                        Timestamp regTs = rs.getTimestamp("cus_reg_ts");

                    Boolean locked = (tmp = rs.getString("cus_locked")) != null ? new Boolean(tmp) : null;

                        Timestamp modificatonTs = rs.getTimestamp("cus_modificaton_ts");

            return new Customer(pk, addressPk, name, email, type, regTs, locked, modificatonTs);
        }
    }

}

```