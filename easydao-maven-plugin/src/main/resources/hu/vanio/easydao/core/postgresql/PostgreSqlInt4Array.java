package hu.vanio.easydao.core.postgresql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
 
/** This is class provides {@link java.sql.Array} interface for PostgreSQL <code>int4</code> array.
 *
 * @author Valentine Gogichashvili
 *
 */
public class PostgreSqlInt4Array implements java.sql.Array {
 
    private final int[] intArray;
    private final String stringValue;
 
    public PostgreSqlInt4Array(int[] intArray) {
        this.intArray = intArray;
        this.stringValue = intArrayToPostgreSQLInt4ArrayString(intArray);
    }
 
    @Override
    public String toString() {
        return stringValue;
    }
 
    /**
     * This static method can be used to convert an integer array to string representation of PostgreSQL integer array.
     * @param a source integer array
     * @return string representation of a given integer array
     */
    public static String intArrayToPostgreSQLInt4ArrayString(int[] a) {
        if ( a == null ) {
            return null;
        }
        final int al = a.length;
        if ( al == 0 ) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder( 2 + al * 7 ); // as we usually operate with 6 digit numbers + 1 symbol for a delimiting comma
        sb.append('{');
        for (int i = 0; i < al; i++) {
            if ( i > 0 ) sb.append(',');
            sb.append(a[i]);
        }
        sb.append('}');
        return sb.toString();
    }
 
 
    public static String intArrayToCommaSeparatedString(int[] a) {
        if ( a == null ) {
            return null;
        }
        final int al = a.length;
        if ( al == 0 ) {
            return "";
        }
        StringBuilder sb = new StringBuilder( al * 7 ); // as we usually operate with 6 digit numbers + 1 symbol for a delimiting comma
        for (int i = 0; i < al; i++) {
            if ( i > 0 ) sb.append(',');
            sb.append(a[i]);
        }
        return sb.toString();
    }
 
    @Override
    public Object getArray() throws SQLException {
        return intArray == null ? null : Arrays.copyOf(intArray, intArray.length);
    }
 
    @Override
    public Object getArray(Map<String, Class<?>> map) throws SQLException {
        return getArray();
    }
 
    @Override
    public Object getArray(long index, int count) throws SQLException {
        return intArray == null ? null : Arrays.copyOfRange(intArray, (int)index, (int)index + count );
    }
 
    @Override
    public Object getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
        return getArray(index, count);
    }
 
    @Override
    public int getBaseType() throws SQLException {
        return java.sql.Types.INTEGER;
    }
 
    @Override
    public String getBaseTypeName() throws SQLException {
        return "int4";
    }
 
    @Override
    public ResultSet getResultSet() throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public ResultSet getResultSet(long index, int count) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException();
    }
 
    @Override
    public void free() throws SQLException {
    }
 
}