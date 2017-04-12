// GENERATED FILE, DO NOT MODIFY! YOUR MODIFICATION WILL BE LOST!
${e.licenseText}
package ${e.packageOfJavaDao}.${e.database.name};

import java.math.BigDecimal;
<#if t.hasBlobField>import java.sql.Blob;${'\n'}</#if><#if t.hasClobField>import java.sql.Clob;${'\n'}</#if><#if t.hasArrayField>import java.sql.Array;${'\n'}</#if>
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

import ${e.packageOfJavaModel}.${e.database.name}.${t.javaName};

<#list t.fieldList as field><#if field.enumerated>import ${field.javaType};${'\n'}</#if></#list>
/**
 * ${t.javaName}${e.daoSuffix}.
 * ${t.comment}
 * ${messages('generatedFrom', t.dbName)}
 * ${messages('createdOn', e.database.modelCreationDate)}
 * ${messages('databaseName', e.database.name)}
 * ${messages('generatedBy')} ${project.name} v${project.version}
 */
@Repository
public class ${t.javaName}${e.daoSuffix}Impl implements ${t.javaName}${e.daoSuffix} {

    /** ${messages('selectedFields')} */
    static final public String SELECTED_FIELDS = "<#list t.fieldList as field>${field.dbName}<#if field_has_next>, </#if></#list>";

    /** ${messages('dataSourceComment')} */
    protected DataSource dataSource;
    /** ${messages('jdbcTemplateComment')} */
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("${e.database.name}") DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    <#if t.hasPkField>

    @Override
    <#if t.compositePk>
    public ${t.javaName} read(${t.javaName}.Pk pk<#if t.hasBlobField || t.hasClobField>, boolean readLobFields</#if>) {
    <#else>
    public ${t.javaName} read(${t.pkField.javaTypeAsString} ${t.pkField.javaName}<#if t.hasBlobField || t.hasClobField>, boolean readLobFields</#if>) {
    </#if>
        <#if t.compositePk>
        String query = "select " + SELECTED_FIELDS + " from ${t.dbName} where <#list t.pkFields as field>${field.dbName} = ?<#if field_has_next> and </#if></#list>";
        ${t.javaName} retVal = this.jdbcTemplate.queryForObject(query, new ${t.javaName}RowMapper(<#if t.hasBlobField || t.hasClobField>readLobFields</#if>), 
                <#list t.pkFields as field> 
                <#if field.enumerated>
                    <#if field.irregularEnum>
                        pk.get${field.javaName?cap_first}() != null ? pk.get${field.javaName?cap_first}().getEnumName() : null<#if field_has_next>,</#if>
                    <#else>
                        pk.get${field.javaName?cap_first}() != null ? pk.get${field.javaName?cap_first}().name() : null<#if field_has_next>,</#if>
                    </#if>
                <#else>
                    pk.get${field.javaName?cap_first}()<#if field_has_next>, </#if>
                </#if>
                </#list>);
        <#else>
        String query = "select " + SELECTED_FIELDS + " from ${t.dbName} where ${t.pkField.dbName} = ?";
        ${t.javaName} retVal = this.jdbcTemplate.queryForObject(query, new ${t.javaName}RowMapper(<#if t.hasBlobField || t.hasClobField>readLobFields</#if>), ${t.pkField.javaName});
        </#if>
        return retVal;
    }

    </#if>

    @Override
    public List<${t.javaName}> readAll(<#if t.hasBlobField || t.hasClobField>boolean readLobFields</#if>) {
        String query = "select " + SELECTED_FIELDS + " from ${t.dbName}";
        List<${t.javaName}> retVal = this.jdbcTemplate.query(query, new ${t.javaName}RowMapper(<#if t.hasBlobField || t.hasClobField>readLobFields</#if>));
        return retVal;
    }

    <#list t.indexList as index>
    <#if index.unique>
    @Override
    public ${t.javaName} readIndexed_${index.javaName}(<#list index.fields as field>${field.javaTypeAsString} ${field.javaName}<#if field_has_next>, </#if></#list><#if t.hasBlobField || t.hasClobField>, boolean readLobFields</#if>) {
        String query = "select " + SELECTED_FIELDS + " from ${t.dbName} where "<#list index.fields as field>
                <#if field_index == 0>+ "${field.dbName} = ? "<#else>+ (${field.javaName}!=null?"and ${field.dbName} = ? ":"")</#if></#list>;

        List params = new java.util.ArrayList(${index.fields?size});
        <#list index.fields as field> 
        <#if field_index == 0>
            <#if field.enumerated>
                <#if field.irregularEnum>
                    params.add(${field.javaName}.getEnumName());
                <#else>
                    params.add(${field.javaName}.name());
                </#if>
            <#else>
                params.add(${field.javaName});
            </#if>
        <#else>
            <#if field.enumerated>
                <#if field.irregularEnum>
                    if (${field.javaName}!=null) {params.add(${field.javaName}.getEnumName());}
                <#else>
                    if (${field.javaName}!=null) {params.add(${field.javaName}.name());}
                </#if>
            <#else>
                if (${field.javaName}!=null) {params.add(${field.javaName});}
            </#if>
        </#if>
        </#list>

        List<${t.javaName}> retVal = this.jdbcTemplate.query(query, new ${t.javaName}RowMapper(<#if t.hasBlobField || t.hasClobField>readLobFields</#if>), params.toArray());
        return retVal.isEmpty()?null:retVal.get(0);
    }
    <#else>
    @Override
    public List<${t.javaName}> readIndexed_${index.javaName}(<#list index.fields as field>${field.javaTypeAsString} ${field.javaName}<#if field_has_next>, </#if></#list><#if t.hasBlobField || t.hasClobField>, boolean readLobFields</#if>) {
        String query = "select " + SELECTED_FIELDS + " from ${t.dbName} where "<#list index.fields as field>
                <#if field_index == 0>+ "${field.dbName} = ? "<#else>+ (${field.javaName}!=null?"and ${field.dbName} = ? ":"")</#if></#list>;
        
        List params = new java.util.ArrayList(${index.fields?size});
        <#list index.fields as field> 
        <#if field_index == 0>
            <#if field.enumerated>
                <#if field.irregularEnum>
                    params.add(${field.javaName}.getEnumName());
                <#else>
                    params.add(${field.javaName}.name());
                </#if>
            <#else>
                params.add(${field.javaName});
            </#if>
        <#else>
            <#if field.enumerated>
                <#if field.irregularEnum>
                    if (${field.javaName}!=null) {params.add(${field.javaName}.getEnumName());}
                <#else>
                    if (${field.javaName}!=null) {params.add(${field.javaName}.name());}
                </#if>
            <#else>
                if (${field.javaName}!=null) {params.add(${field.javaName});}
            </#if>
        </#if>
        </#list>

        List<${t.javaName}> retVal = this.jdbcTemplate.query(query, new ${t.javaName}RowMapper(<#if t.hasBlobField || t.hasClobField>readLobFields</#if>), params.toArray());
        return retVal;
    }
    </#if>
    </#list>


    <#if t.hasPkField>

    @Override
    public void createPk(final ${t.javaName} instance) {
    <#if !t.missingSequence>
        <#if t.compositePk>
            throw new IllegalStateException("Primary key value cannot be auto-generated for tables with composite primary key");
        <#else>
            <#if (t.sequenceName)??>
            <#if e.databaseType.name() == 'ORACLE10' || e.databaseType.name() == 'ORACLE11'>
            String query = "select ${t.sequenceName}.nextval PK from dual";
            </#if>
            <#if e.databaseType.name() == 'POSTGRESQL9'>
            String query = "select nextval ('${t.sequenceName}') as PK";
            </#if>
            <#assign pkField = t.pkField>
            this.jdbcTemplate.query(query, new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    <#if pkField.readAsString>
                    String tmp;
                    ${pkField.javaTypeAsString} ${pkField.javaName} = (tmp = rs.getString("PK")) != null ? new ${pkField.javaTypeAsString}(tmp) : null;
                    <#else>
                    ${pkField.javaTypeAsString} ${pkField.javaName} = rs.get${pkField.javaTypeAsString}("PK");
                    </#if>
                    instance.set${pkField.javaName?cap_first}(${pkField.javaName});
                }
            });
            <#else>
            throw new IllegalStateException("Primary key value cannot be auto-generated for tables with non-integer primary key");
            </#if>
        </#if>
    <#else>
        throw new IllegalStateException("Missing sequence: ${t.sequenceName}");
    </#if>
    }</#if>
    
    <#if t.hasPkField>
    @Override
    public ${t.javaName} create(${t.javaName} instance, boolean createPk) {
<#assign insertFieldNameList><#list t.fieldList as field><#if !field.virtual>${field.dbName},</#if></#list></#assign>
<#assign insertFieldValueList><#list t.fieldList as field><#if !field.virtual>?,</#if></#list></#assign>

        String sql = "insert into ${t.dbName} (${insertFieldNameList?remove_ending(",")}) values (${insertFieldValueList?remove_ending(",")})";
        if (createPk) createPk(instance);
        Object[] params = new Object[] {
<#assign instanceGetList>
            <#list t.fieldList as field>
                <#if !field.virtual>
                <#if field.array>
                    <#if e.databaseType.name() == 'POSTGRESQL9'>
                        hu.vanio.easydao.core.postgresql.PostgreSqlArrayFactory.getForType(instance.get${field.javaName?cap_first}())<#if field_has_next>,</#if>
                    <#else>
                        FIXME: array handling is not yet implemented for Oracle
                    </#if>
                <#else>
                    <#if field.enumerated>
                        <#if field.irregularEnum>
                            instance.get${field.javaName?cap_first}() != null ? instance.get${field.javaName?cap_first}().getEnumName() : null<#if field_has_next>,</#if>
                        <#else>
                            instance.get${field.javaName?cap_first}() != null ? instance.get${field.javaName?cap_first}().name() : null<#if field_has_next>,</#if>
                        </#if>
                    <#else>
                        instance.get${field.javaName?cap_first}()<#if field_has_next>,</#if>
                    </#if>
                </#if>
                </#if>
            </#list>
</#assign>${instanceGetList?remove_ending(",")}
        };
        this.jdbcTemplate.update(sql, params);

        <#if t.compositePk>
        ${t.javaName}.Pk pk = new ${t.javaName}.Pk(<#list t.pkFields as field>instance.get${field.javaName?cap_first}()<#if field_has_next>, </#if></#list>);
        return read(pk<#if t.hasBlobField || t.hasClobField>, false</#if>);
        <#else>
        return read(instance.get${t.pkField.javaName?cap_first}()<#if t.hasBlobField || t.hasClobField>, false</#if>);
        </#if>
    }
    </#if>

    <#if t.hasPkField>

    @Override
    public ${t.javaName} update(${t.javaName} instance<#if t.hasBlobField || t.hasClobField>, boolean updateLobFields</#if>) {
        String sql = "update ${t.dbName} " +
                     "set " +
<#assign fieldList>
<#list t.nonPkFields as field>
<#if !field.virtual>
<#if field.blob||field.clob>(updateLobFields?"${field.dbName} = ? <#if field_has_next>, </#if>":"") +<#else>                     "${field.dbName} = ? <#if field_has_next>, </#if>" +</#if>
</#if>
</#list>
</#assign>
${fieldList?remove_ending(", ")}
                     <#if t.compositePk>"where <#list t.pkFields as field>${field.dbName} = ?<#if field_has_next> and </#if></#list>";<#else>"where ${t.pkField.dbName} = ?";</#if>

        <#if t.hasClobField||t.hasBlobField>
            List paramsList = new java.util.ArrayList();

            <#list t.nonPkFields as field>
                <#if field.array>
                    <#if e.databaseType.name() == 'POSTGRESQL9'>
                        paramsList.add(hu.vanio.easydao.core.postgresql.PostgreSqlArrayFactory.getForType(instance.get${field.javaName?cap_first}()));
                    <#else>
                        FIXME: array handling is not yet implemented for Oracle
                    </#if>
                <#else>
                    <#if field.blob||field.clob>
                        if (updateLobFields) { paramsList.add(instance.get${field.javaName?cap_first}()); }
                    <#else>
                        <#if field.enumerated>
                            <#if field.irregularEnum>
                                paramsList.add(instance.get${field.javaName?cap_first}().getEnumName());
                            <#else>
                                paramsList.add(instance.get${field.javaName?cap_first}().name());
                            </#if>
                        <#else>
                            paramsList.add(instance.get${field.javaName?cap_first}());
                        </#if>
                    </#if>
                </#if>
            </#list>
            <#if t.compositePk>
                <#list t.pkFields as field>
                    <#if field.enumerated>
                        <#if field.irregularEnum>
                            if (${field.javaName}!=null) {paramsList.add(instance.get${field.javaName?cap_first}().getEnumName());}
                        <#else>
                            if (${field.javaName}!=null) {paramsList.add(instance.get${field.javaName?cap_first}().name());}
                        </#if>
                    <#else>
                        paramsList.add(instance.get${field.javaName?cap_first}());
                    </#if>
                </#list>
            <#else>
                paramsList.add(instance.get${t.pkField.javaName?cap_first}());
            </#if>
            Object[] params = paramsList.toArray();
        <#else>
            Object[] params = new Object[] {
            <#list t.nonPkFields as field>
                <#if !field.virtual>
                <#if field.array>
                    <#if e.databaseType.name() == 'POSTGRESQL9'>
                        hu.vanio.easydao.core.postgresql.PostgreSqlArrayFactory.getForType(instance.get${field.javaName?cap_first}()),
                    <#else>
                        FIXME: array handling is not yet implemented for Oracle
                    </#if>
                <#else>
                    <#if field.enumerated>
                        <#if field.irregularEnum>
                            instance.get${field.javaName?cap_first}() != null ? instance.get${field.javaName?cap_first}().getEnumName() : null,
                        <#else>
                            instance.get${field.javaName?cap_first}() != null ? instance.get${field.javaName?cap_first}().name() : null,
                        </#if>
                    <#else>
                        instance.get${field.javaName?cap_first}(),
                    </#if>
                </#if>
                </#if>
            </#list>
            <#if t.compositePk>
                <#list t.pkFields as field>
                    <#if field.enumerated>
                        <#if field.irregularEnum>
                            instance.get${field.javaName?cap_first}().getEnumName()<#if field_has_next>,</#if>
                        <#else>
                            instance.get${field.javaName?cap_first}().name()<#if field_has_next>,</#if>
                        </#if>
                    <#else>
                        instance.get${field.javaName?cap_first}()<#if field_has_next>,</#if>
                    </#if>
                </#list>
            <#else>
                instance.get${t.pkField.javaName?cap_first}()
            </#if>
            };
        </#if>

        int updRows = this.jdbcTemplate.update(sql, params);
        if (updRows != 1) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(sql, 1, updRows);
        }

        <#if t.compositePk>
            ${t.javaName}.Pk pk = new ${t.javaName}.Pk(<#list t.pkFields as field>instance.get${field.javaName?cap_first}()<#if field_has_next>, </#if></#list>);
            return read(pk<#if t.hasBlobField || t.hasClobField>, false</#if>);
        <#else>
            return read(instance.get${t.pkField.javaName?cap_first}()<#if t.hasBlobField || t.hasClobField>, false</#if>);
        </#if>
    }

    @Override
    <#if t.compositePk>
    public void delete(${t.javaName}.Pk pk) {
    <#else>
    public void delete(${t.pkField.javaTypeAsString} ${t.pkField.javaName}) {
    </#if>
        <#if t.compositePk>
        String sql = "delete from ${t.dbName} where <#list t.pkFields as field>${field.dbName} = ?<#if field_has_next> and </#if></#list>";
        int updRows = this.jdbcTemplate.update(sql, 
                <#list t.pkFields as field> 
                <#if field.enumerated>
                    <#if field.irregularEnum>
                        pk.get${field.javaName?cap_first}().getEnumName()<#if field_has_next>,</#if>
                    <#else>
                        pk.get${field.javaName?cap_first}().name()<#if field_has_next>,</#if>
                    </#if>
                <#else>
                    pk.get${field.javaName?cap_first}()<#if field_has_next>, </#if>
                </#if>
                </#list>);
        <#else>
        String sql = "delete from ${t.dbName} where ${t.pkField.dbName} = ?";
        int updRows = this.jdbcTemplate.update(sql, ${t.pkField.javaName});
        </#if>
        if (updRows != 1) {
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(sql, 1, updRows);
        }
    }
    </#if>
    /** 
     * ${messages('createInstanceMethodComment')}
<#list t.fieldList as field><#if field.virtual>     * Virtual (read-only): ${field.javaName}, <@compress single_line=true>${field.comment}</@compress>
</#if></#list>
<#list t.fieldList as field><#if !field.virtual>     * @param ${field.javaName} <@compress single_line=true>${field.comment}</@compress>
</#if></#list>
     * @return ${t.javaName}
     */
<#assign constructorString><#list t.fieldList as field><#if field.clob>String<#elseif field.blob>byte[]<#else>${field.javaTypeAsString}</#if> ${field.javaName}, </#list></#assign>
<#assign returnString><#list t.fieldList as field>${field.javaName}, </#list></#assign>
    protected ${t.javaName} createInstance(${constructorString?remove_ending(", ")}) {
        return new ${t.javaName}(${returnString?remove_ending(", ")}); 
    }

    /** ${messages('rowMapperClassComment')} */
    public class ${t.javaName}RowMapper implements RowMapper<${t.javaName}> {
        <#if t.hasBlobField || t.hasClobField>
        /** ${messages('readLobFieldsParamComment')} */
        private final boolean readLobFields;

        /**
         * ${messages('rowMapperConstructorComment')}
         * @param readLobFields ${messages('readLobFieldsParamComment')}
         */
        public ${t.javaName}RowMapper(boolean readLobFields) {
            this.readLobFields = readLobFields;
        }
        </#if>

        @Override
        public ${t.javaName} mapRow(ResultSet rs, int rowNum) throws SQLException {
            String tmp;
        <#if t.hasBlobField>    Blob tmpBlob;${'\n'}</#if><#if t.hasClobField>Clob tmpClob;${'\n'}</#if><#if t.hasArrayField>Array tmpArray;${'\n'}</#if>
        <#list t.fieldList as field>
        <#if field.readAsString><#if field.javaTypeAsString == "Boolean">
            ${field.javaTypeAsString} ${field.javaName} = (tmp = rs.getString("${field.dbName}")) != null ? Boolean.valueOf(tmp) : null;
        <#else>
            ${field.javaTypeAsString} ${field.javaName} = (tmp = rs.getString("${field.dbName}")) != null ? new ${field.javaTypeAsString}(tmp) : null;
        </#if><#else><#if !field.blob && !field.clob && !field.array && !field.enumerated>
            ${field.javaTypeAsString} ${field.javaName} = rs.get${field.javaTypeAsString}("${field.dbName}");
        </#if><#if field.enumerated><#if field.irregularEnum>
            ${field.javaTypeAsString} ${field.javaName} = (tmp = rs.getString("${field.dbName}")) != null ? ${field.javaTypeAsString}.getEnumInstance(tmp) : null;
        <#else>
            ${field.javaTypeAsString} ${field.javaName} = (tmp = rs.getString("${field.dbName}")) != null ? ${field.javaTypeAsString}.valueOf(tmp) : null;
        </#if></#if></#if><#if field.blob>
            byte[] ${field.javaName} = null;
            if (readLobFields) {
                ${field.javaName} = (tmpBlob = rs.getBlob("${field.dbName}")) != null ? tmpBlob.getBytes(1, (int)tmpBlob.length()) : null;
            }
        </#if><#if field.clob>
            String ${field.javaName} = null;
            if (readLobFields) {
                ${field.javaName} = (tmpClob = rs.getClob("${field.dbName}")) != null ? tmpClob.getSubString(1, (int)tmpClob.length()) : null;
            }
        </#if><#if field.array>
            ${field.javaTypeAsString} ${field.javaName} = (tmpArray = rs.getArray("${field.dbName}")) != null ? (${field.javaTypeAsString})tmpArray.getArray() : null;
        </#if></#list>
            return ${t.javaName}DaoImpl.this.createInstance(${returnString?remove_ending(", ")});
        }
    }

}
