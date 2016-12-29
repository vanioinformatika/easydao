// GENERATED FILE, DO NOT MODIFY! YOUR MODIFICATION WILL BE LOST!
${e.licenseText}
package ${e.packageOfJavaModel}.${e.database.name};

import java.sql.Timestamp;
import java.math.BigDecimal;

<#list t.fieldList as field><#if field.enumerated>import ${field.javaType};${'\n'}</#if></#list>
/**
 * ${t.javaName}.
 * <br>${t.comment}
 * <br>${messages('generatedFrom', t.dbName)}
 * <br>${messages('createdOn', e.database.modelCreationDate)}
 * <br>${messages('databaseName', e.database.name)}
 * <br>${messages('generatedBy')} ${project.name} v${project.version}
 */
public class ${t.javaName} implements hu.vanio.easydao.core.Model, java.io.Serializable {

    <#list t.fieldList as field>
    /* ${field.comment}. ${messages('database')}: ${field.dbName}, ${field.dbType}, <#if field.primaryKey>primary key, </#if><#if field.nullable>not nullable.<#else>nullable.</#if> */
    private <#if field.clob>String<#elseif field.blob>byte[]<#else>${field.javaTypeAsString}</#if> ${field.javaName};
    </#list>

    /** ${messages('defaultConstructorComment')} */
    public ${t.javaName}() {}

    /**
     * ${messages('modelClassConstructorComment')}
    <#list t.fieldList as field>
     * @param ${field.javaName} ${field.comment}
    </#list>
     */
    public ${t.javaName}(<#list t.fieldList as field><#if field.clob>String<#elseif field.blob>byte[]<#else>${field.javaTypeAsString}</#if> ${field.javaName}<#if field_has_next>, </#if></#list>) {
        <#list t.fieldList as field>
        this.${field.javaName} = ${field.javaName};
        </#list>
    }

    <#list t.fieldList as field>
    /**
     * ${field.comment}
     * <br>${field.dbName}, ${field.dbType}, <#if field.primaryKey>primary key, </#if><#if field.nullable>not nullable.<#else>nullable.</#if>
     * @return ${field.comment}
     */
    public <#if field.clob>String<#elseif field.blob>byte[]<#else>${field.javaTypeAsString}</#if> get${field.javaName?cap_first}() {
        return this.${field.javaName};
    }
    
    /**
     * ${field.comment}
     * <br>${field.dbName}, ${field.dbType}, <#if field.primaryKey>primary key, </#if><#if field.nullable>not nullable.<#else>nullable.</#if>
     * @param ${field.javaName} ${field.comment}
     */
    public void set${field.javaName?cap_first}(<#if field.clob>String<#elseif field.blob>byte[]<#else>${field.javaTypeAsString}</#if> ${field.javaName}) {
        this.${field.javaName} = ${field.javaName};
    }

    </#list>
    <#if e.generateModelToString>
    @Override
    public String toString() {
        return "${t.javaName} {" 
        <#list t.fieldList as field>
            + "${field.javaName}: \"" + ${field.javaName}<#if field_has_next> + "\", "<#else> + "\""</#if>
        </#list>
            + '}';
    }
    </#if>

    <#if t.compositePk>
    /** ${messages('pkClassComment')} */
    static public class Pk implements java.io.Serializable {

        <#list t.pkFields as field>
        /** ${field.comment} */
        private final ${field.javaTypeAsString} ${field.javaName};
        </#list>

        /**
         * ${messages('pkClassConstructorComment')}
         <#list t.pkFields as field>
         * @param ${field.javaName} ${field.comment}
         </#list>
         */
        public Pk(<#list t.pkFields as field>${field.javaTypeAsString} ${field.javaName}<#if field_has_next>, </#if></#list>) {
            <#list t.pkFields as field>
            this.${field.javaName} = ${field.javaName};
            </#list>
        }

        <#list t.pkFields as field>
        /**
         * ${field.comment}
         * @return ${messages('modelClassGetterReturnComment')}
         */
        public ${field.javaTypeAsString} get${field.javaName?cap_first}() {
            return this.${field.javaName};
        }
        </#list>

    }
    </#if>

}
