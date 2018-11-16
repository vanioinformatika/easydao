// GENERATED FILE, DO NOT MODIFY! YOUR MODIFICATION WILL BE LOST!
${e.licenseText}
package ${e.packageOfJavaDao}<#if e.addDbNameToPackageNames>.${e.database.name}</#if>;

import java.sql.Timestamp;
import java.util.List;

import ${e.packageOfJavaModel}<#if e.addDbNameToPackageNames>.${e.database.name}</#if>.${t.javaName};

<#list t.fieldList as field><#if field.enumerated>import ${field.javaType};${'\n'}</#if></#list>
/**
 * ${t.javaName}${e.daoSuffix}.
 * <br>${t.comment}
 * <br>${messages('generatedFrom', t.dbName)}
 * <br>${messages('createdOn', e.database.modelCreationDate)}
 * <br>${messages('databaseName', e.database.name)}
 * <br>${messages('generatedBy')} ${project.name} v${project.version}
 */
public interface ${t.javaName}${e.daoSuffix} {

    <#if t.hasPkField>

    /**
     * ${messages('readMethodComment')}
     <#if t.compositePk>
     * @param pk ${messages('pkParamComment')}
     <#else>
     * @param ${t.pkField.javaName} ${t.pkField.comment}
     </#if>
     * <#if t.hasBlobField || t.hasClobField>@param readLobFields ${messages('readLobFieldsParamComment')}</#if>
     * @return ${t.javaName} ${messages('instance')}
     */
    <#if t.compositePk>
    ${t.javaName} read(${t.javaName}.Pk pk<#if t.hasBlobField || t.hasClobField>, boolean readLobFields</#if>);
    <#else>
    ${t.javaName} read(${t.pkField.javaTypeAsString} ${t.pkField.javaName}<#if t.hasBlobField || t.hasClobField>, boolean readLobFields</#if>);
    </#if>

    </#if>

    /**
     * ${messages('readAllMethodComment')}
     * <#if t.hasBlobField || t.hasClobField>@param readLobFields ${messages('readLobFieldsParamComment')}</#if>
     * @return ${messages('readAllMethodReturnComment')}
     */
    List<${t.javaName}> readAll(<#if t.hasBlobField || t.hasClobField>boolean readLobFields</#if>);

    <#if t.hasPkField>

    /**
     * ${messages('createPkMethodComment')}
     * @param instance ${messages('instanceParamComment')}
     */
    void createPk(final ${t.javaName} instance);

    </#if>

    <#if t.hasPkField>

    /**
     * ${messages('createMethodComment')}
     * @param instance ${messages('instanceParamComment')}
     * @param createPk ${messages('createPkParamComment')}
     * @return ${messages('createMethodReturnComment')}
     */
    ${t.javaName} create(${t.javaName} instance, boolean createPk);
    
    </#if>

    <#if t.hasPkField>

    /**
     * ${messages('updateMethodComment')}
     * @param instance ${messages('instanceParamComment')}
     * <#if t.hasBlobField || t.hasClobField>@param updateLobFields ${messages('updateLobFieldsParamComment')}</#if>
     * @return ${messages('updateMethodReturnComment')}
     */
    ${t.javaName} update(${t.javaName} instance<#if t.hasBlobField || t.hasClobField>, boolean updateLobFields</#if>);

    /**
     * ${messages('deleteMethodComment')}
     <#if t.compositePk>
     * @param pk ${messages('pkParamComment')}
     <#else>
     * @param ${t.pkField.javaName} ${t.pkField.comment}
     </#if>
     */
    <#if t.compositePk>
    void delete(${t.javaName}.Pk pk);
    <#else>
    void delete(${t.pkField.javaTypeAsString} ${t.pkField.javaName});
    </#if>
    
    </#if>

<#list t.indexList as index>
    <#if index.unique>
    /**
     * ${messages('readIndexedUniqueMethodComment')}
     <#list index.fields as field>
     * @param ${field.javaName} ${field.comment}
     </#list>
     * <#if t.hasBlobField || t.hasClobField>@param readLobFields ${messages('readLobFieldsParamComment')}</#if>
     * @return ${t.javaName} ${messages('instance')}
     */
    ${t.javaName} readIndexed_${index.javaName}(
            <#list index.fields as field>${field.javaTypeAsString} ${field.javaName}<#if field_has_next>, </#if></#list>
            <#if t.hasBlobField || t.hasClobField>, boolean readLobFields</#if>);
    <#else>
    /**
     * ${messages('readIndexedNonUniqueMethodComment')}
     <#list index.fields as field>
     * @param ${field.javaName} ${field.comment}
     </#list>
     * <#if t.hasBlobField || t.hasClobField>@param readLobFields ${messages('readLobFieldsParamComment')}</#if>
     * @return ${t.javaName} ${messages('instances')}
     */
    List<${t.javaName}> readIndexed_${index.javaName}(
            <#list index.fields as field>${field.javaTypeAsString} ${field.javaName}<#if field_has_next>, </#if></#list>
            <#if t.hasBlobField || t.hasClobField>, boolean readLobFields</#if>);
    </#if>
    </#list>

} // DAO interface
