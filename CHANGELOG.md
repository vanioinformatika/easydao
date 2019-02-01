# EasyDao Changelog:


## 3.3.3

* Fixes #22: Custom field types are not imported in the generated DAO interfaces 
* Fixes #14: Same enum for different columns on same table results multiple import for the enum in the model.

## 3.3.2

Fixes #21: Don't generate deprecated constructors to the DAO sources 

## 3.3.1

Fixes #18: NPE when no `replacementTypeMapFilename` is specified in the maven plugin configuration 

## 3.3.0

Adds new maven plugin configuration property `addDbNameToPackageNames`

## 3.2.1

* Fixes Dao.update method generation with Oracle virtual columns

## 3.2.0

* Overriding of the default type map is now possible through a replacement-type.properties #16
* Small localization fixes

## 3.1.0

* Handling Oracle virtual (read-only) fields #13
* Handling Oracle _interval_ type as Java Integer

## 3.0.5

* Fixes #12 Enum field reference error (importing enum field classes in model classes)

## 3.0.4

* Model class instantiation can be overridden in classes that extend DaoImpl classes, just override the `createInstance` method

## 3.0.3

* Fixes #9: fixing "There is no Java type definiton for timestamp(6) with time zone database type" error (ORACLE11).
* Spring upgrade to 3.2.17 from 3.2.15 in demo (easydao-generating-dao, easydao-using-dao)

## 3.0.2

* Fixes #7: Return statement of DaoImpl.readIndexed_XXX method generated for a unique index throws IndexOutOfBoundsException

## 3.0.1

* DaoImpl.RowMapper inner class visibility changed from protected to public. fixes #6

## 3.0.0

* Generic Dao<T, P> interface has been eliminated.
* A separate interface is generated for each DAO classes.
* DAO class names have changed from XXXDao to XXXDaoImpl. The XXXDao name is used for the DAO interface from now on.
* Restricted DAOs can be generated for tables without primary key. In this case only readAll() and readIndexed_XXX() methods are generated.
* Dao.readIndexed_XXX methods for unique indexes don't throw EmptyResultDataAccessException anymore, they return null if no data has ben found instead.
* readLobFields and updateLobFields method parameters are not generated for DAOs that don't handle LOB fields

## 2.0.1

* handling ORACLE10 value in sequence query generator (bug: variable query not found)

## 2.0.0

* Oracle 12 support: set databaseType to ORACLE11
* easydao-core isn't necessary dependency anymore
* easydao project sources refactored on GitHub: easydao repository contains all necessary source and test codes
* continuous integration has been initialized with TravisCI: https://travis-ci.org/vanioinformatika/easydao

## 1.0.17

* Oracle DB support improved: introduced separate ORACLE10 and ORACLE11 database type

## 1.0.16

* Introduced checking of the existence of sequences (used for auto-generating PK values): https://github.com/vanioinformatika/easydao/issues/4

## 1.0.15

* Fixed source 'update(...)' generation issue when table has composite keys: https://github.com/vanioinformatika/easydao/issues/3

## 1.0.14

* Fixed fields' order issue: https://github.com/vanioinformatika/easydao/issues/2

## 1.0.13

* Various fixes regarding using enum fields in composite primary keys

## 1.0.12

* Enum fields can be used in composite primary keys and indexes

## 1.0.11

* Fixed enum handling in Dao.mapRow method

## 1.0.10

* Introduced handling of fields with enumerated values that do not comform to Java naming conventions (e.g. numeric enum values)

## 1.0.9

* Introduced handling of fields with enumerated values (these fields can be mapped to Java enums)

## 1.0.8

* Introduced encoding parameter (encoding property in EngineConfiguration)

## 1.0.7

* Introduced table name inclusion patterns (tableNameIncludes property in EngineConfiguration)

## 1.0.6

* First Bintray release.

## 1.0.5

* Introduced localisation of the comments in the generated Java classes (so far only Hungarian and English is supported)

## 1.0.4

* Introduced readIndexedXXX method generation for DAOs

## 1.0.3

* Small fix for DAO update method

## 1.0.2

* Introduced new parameter for the DAO update method: updateLobFields  
* Introduced toString method generation for model classes  
* DAO generation eliminated for PK-less tables

## 1.0.1

* Introduced insertion of License text into generated classes

## 1.0.0

* First release
