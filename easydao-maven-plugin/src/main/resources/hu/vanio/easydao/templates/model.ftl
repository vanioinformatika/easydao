// GENERATED FILE, DO NOT MODIFY! YOUR MODIFICATION WILL BE LOST!
${e.licenseText}
package ${e.packageOfJavaModel}.${e.database.name};

import java.sql.Timestamp;
import java.math.BigDecimal;
<#list t.fieldList as field><#if field.enumerated>import ${field.javaType};${'\n'}</#if></#list>
/**
 * ${t.javaName}.
 * ${t.comment}
 * ${messages('generatedFrom', t.dbName)}
 * ${messages('createdOn', e.database.modelCreationDate)}
 * ${messages('databaseName', e.database.name)}
 * ${messages('generatedBy')} ${project.name} v${project.version}
 */
public class ${t.javaName} implements hu.vanio.easydao.core.Model, java.io.Serializable {

    <#list t.fieldList as field>
    /* <@compress single_line=true>${field.comment}. ${messages('database')}: ${field.dbName}, ${field.dbType}, <#if field.virtual>virtual, </#if><#if field.primaryKey>primary key, </#if><#if field.nullable>not nullable.<#else>nullable.</#if> </@compress>*/
    private <#if field.clob>String<#elseif field.blob>byte[]<#else>${field.javaTypeAsString}</#if> ${field.javaName};
    </#list>

    /** ${messages('defaultConstructorComment')} */
    public ${t.javaName}() {}

    /**
     * ${messages('modelClassConstructorComment')}
<#list t.fieldList as field>     * @param ${field.javaName} <@compress single_line=true>${field.comment}<#if field.virtual>, virtual field (read-only)</#if></@compress>
</#list>
     */
<#assign constructorString><#list t.fieldList as field><#if field.clob>String<#elseif field.blob>byte[]<#else>${field.javaTypeAsString}</#if> ${field.javaName}, </#list></#assign>
    public ${t.javaName}(${constructorString?remove_ending(", ")}) {
<#list t.fieldList as field>        this.${field.javaName} = ${field.javaName};
</#list>
    }

    <#list t.fieldList as field>
    /**
     * <@compress single_line=true>${field.comment}</@compress>
     * ${field.dbName}, ${field.dbType}, <#if field.virtual>virtual, </#if><#if field.primaryKey>primary key, </#if><#if field.nullable>not nullable.<#else>nullable.</#if>
     * @return <@compress single_line=true>${field.comment}</@compress>
     */
    public <#if field.clob>String<#elseif field.blob>byte[]<#else>${field.javaTypeAsString}</#if> get${field.javaName?cap_first}() {
        return this.${field.javaName};
    }
    
<#if !field.virtual>
    /**
     * <@compress single_line=true>${field.comment}</@compress>
     * ${field.dbName}, ${field.dbType}, <#if field.primaryKey>primary key, </#if><#if field.nullable>not nullable.<#else>nullable.</#if>
     * @param ${field.javaName} <@compress single_line=true>${field.comment}</@compress>
     */
    public void set${field.javaName?cap_first}(<#if field.clob>String<#elseif field.blob>byte[]<#else>${field.javaTypeAsString}</#if> ${field.javaName}) {
        this.${field.javaName} = ${field.javaName};
    }
</#if>

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
        /** <@compress single_line=true>${field.comment}</@compress> */
        private final ${field.javaTypeAsString} ${field.javaName};
        </#list>

        /**
         * ${messages('pkClassConstructorComment')}
         <#list t.pkFields as field>
         * @param ${field.javaName} <@compress single_line=true>${field.comment}</@compress>
         </#list>
         */
        public Pk(<#list t.pkFields as field>${field.javaTypeAsString} ${field.javaName}<#if field_has_next>, </#if></#list>) {
            <#list t.pkFields as field>
            this.${field.javaName} = ${field.javaName};
            </#list>
        }

        <#list t.pkFields as field>
        /**
         * <@compress single_line=true>${field.comment}</@compress>
         * @return ${messages('modelClassGetterReturnComment')}
         */
        public ${field.javaTypeAsString} get${field.javaName?cap_first}() {
            return this.${field.javaName};
        }
        </#list>

    }
    </#if>

}
