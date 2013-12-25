// GENERATED FILE, DO NOT MODIFY! YOUR MODIFICATION WILL BE LOST!
package ${e.packageOfJavaModel};

import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * ${t.javaName} class, generated from ${t.dbName} database table.
 * ${t.comment}
 * Created on: ${e.database.modelCreationDate}
 * Database name: ${e.database.name}
 * Generated by ${appname} v${appversion}
 */
public class ${t.javaName} implements java.io.Serializable {

    <#list t.fieldList as field>
    /* ${field.comment}. Database: ${field.dbName}, ${field.dbType}, <#if field.primaryKey>primary key, </#if><#if field.nullable>not nullable.<#else>nullable.</#if> */
    private ${field.javaTypeAsString} ${field.javaName};
    </#list>

    /** Default constructor. */
    public ${t.javaName}() {}

    /**
     * Constructor.
    <#list t.fieldList as field>
     * @param ${field.javaName} ${field.comment}
    </#list>
     */
    public ${t.javaName}(<#list t.fieldList as field>${field.javaTypeAsString} ${field.javaName}, </#list>) {
        <#list t.fieldList as field>
        this.${field.javaName} = ${field.javaName};
        </#list>
    }

}
