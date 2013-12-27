// GENERATED FILE, DO NOT MODIFY! YOUR MODIFICATION WILL BE LOST!

#
# Information about database.
# 
# Created on: ${e.database.modelCreationDate}
# Database name: ${e.database.name}
# Generated by ${appname} v${appversion}
#
# There are two sections: first one for help creating replacement files
# and second one for metadata about database.
#

# SECTION 1.
#
# Helper data for replacement property file.
# You can insert these table and filed names into replacement files.
#
# If you don't want a table in your model, then insert into 
# replacement-table.properties file:
#
# TABLE_NAME = 
#
# If you want this table with another name in your model, then insert:
#
# TABLE_NAME = MyNewModelName
#
# Class name will be generated as MyNewModelName.java
#
# If you don't want a field in your model, then insert into 
# replacement-field.properties file:
#
# TABLE_NAME.FIELD_NAME = 
#
# If you want this field with another name in your model, then insert:
#
# TABLE_NAME.FIELD_NAME = myNewFieldName
#
# So it will be generated as: private String myNewFieldName;

#
# TABLE names for replacement file:
#
<#list tList as t>
${t.dbName} = 
</#list>

#
# FIELD names for replacement file:
# 
<#list tList as t>
<#list t.fieldList as f>
${t.dbName}.${f.dbName} = 
</#list>
</#list>


# SECTION 2.

# TABLES
<#assign i=0>

${"TABLE NAME"?right_pad(50)} ${"JAVA NAME"?right_pad(50)} ${"FIELDS"?right_pad(6)} COMMENT
<#list tList as t><#assign i=i+t.fieldList?size>
${t.dbName?right_pad(50)} ${t.javaName?right_pad(50)} ${t.fieldList?size?left_pad(6)} ${t.comment}
</#list>

# FIELDS

${"TABLE.FIELD NAME"?right_pad(70)} ${"JAVA NAME"?right_pad(30)} ${"DB TYPE"?right_pad(20)} ${"JAVA TYPE"?right_pad(10)} ${"PK"?right_pad(5)} ${"NULLABLE"?right_pad(9)} ${"ARRAY"?right_pad(6)} COMMENT
<#list tList as t>
<#list t.fieldList as f>
${"${t.dbName}.${f.dbName}"?right_pad(70)} ${f.javaName?right_pad(30)} ${f.dbType?right_pad(20)} ${f.javaTypeAsString?right_pad(10)} ${"${f.primaryKey?c}"?right_pad(5)} ${"${f.nullable?c}"?right_pad(9)} ${"${f.array?c}"?right_pad(6)} ${f.comment}
</#list>
</#list>

# There is ${tList?size} tables and ${i} fields in database.
