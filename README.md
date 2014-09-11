EasyDao
=======

EasyDao is a lightweight, fast and flexible model and dao code generator.

This project is the engine of the source code generator.

#Changelog:

>*1.0.13:*

>* Various fixes regarding using enum fields in composite primary keys

>*1.0.12:*

>* Enum fields can be used in composite primary keys and indexes

>*1.0.11:*

>* Fixed enum handling in Dao.mapRow method

>*1.0.10:*

>* Introduced handling of fields with enumerated values that do not comform to Java naming conventions (e.g. numeric enum values)

>*1.0.9:*

>* Introduced handling of fields with enumerated values (these fields can be mapped to Java enums)

>*1.0.8:*

>* Introduced encoding parameter (encoding property in EngineConfiguration)

>*1.0.7:*

>* Introduced table name inclusion patterns (tableNameIncludes property in EngineConfiguration)

>*1.0.6:*

>* First Bintray release.

>*1.0.5:*

>* Introduced localisation of the comments in the generated Java classes (so far only Hungarian and English is supported)

>*1.0.4:*

>* Introduced readIndexedXXX method generation for DAOs

>*1.0.3:*

>* Small fix for DAO update method

>*1.0.2:* 

>* Introduced new parameter for the DAO update method: updateLobFields  
>* Introduced toString method generation for model classes  
>* DAO generation eliminated for PK-less tables

>*1.0.1:*

>* Introduced insertion of License text into generated classes

>*1.0.0:*

>* First release

# Fields with enumerated vaules
As of 1.0.9, you can use Java enums for fields with enumerated values. You have two choices: regular and irregular enumerations.
If you use fields with enumerated values, values stored in the database need to be mapped to the corresponding enum value and vica versa.
EasyDao generates the necessary code for you, however if you use irregular enums you have to comply with a few rules. For the details, see 
the Irregular enumerations section.

## Regular enumerations 
An enumeration is regular if all values can be used as a Java identifier (i.e. it starts with a letter, doesn't contain dots and dashes...)
Regular enumerations will be converted to String via the Java enum's name() method. Strings will be converted to Java enum instances by 
calling the enum's valueOf() method.

### Example
Let's assume you have a field MY_TABLE.MY_FIELD that can only contain 'A', 'B' and 'C'

#### The steps to handle this field as a regular enumerated field are the following:

   * Create a java enum that contains A, B and C values (e.g. MyFieldEnum)
   * Specify the field name and the corresponding enum's fully qualified class name in the enum-field.properties file:
     <p><code>MY_TABLE.MY_FIELD = com.mycompany.myapp.model.MyFieldEnum</code></p>
   * Specify the location of the enum-field.properties file in the EngineConfiguration (enumFieldFileName property)
   * Start generation

### Example regular enum implementation 

```java
    public enum MyFieldEnum {
        A, B, C
    }
```

## Iregular enumerations
An enumeration is irregular if at least one of the values cannot be used as a Java identifier. 
Irregular enumerations have to comply with two rules. They have to implement getEnumName() and static getEnumInstance() methods.
Irregular enumerations will be converted to String via the Java enum's getEnumName() method. Strings will be converted to 
Java enum instances by calling the enum's getEnumInstance() method.

### Example
Let's assume you have a field MY_TABLE.MY_FIELD that can only contain 'NORMAL', '2WAY' and '3WAY'
2WAY and 3WAY ar not legal Java identifiers, so a regular Java enum cannot be used here.
All values that cannot be used as a Java identifier, need to be changed, (e.g. 2WAY -> \_2WAY, 3.5 -> \_3_5) 
Of course storing the original value is important as it needs to be used when writing the field to the database and reading it back.
You can see an example implementation below.

#### The steps to handle the above field as an irregular enumerated field are the following:
   * Create a java enum that contains NORMAL, \_2WAY and \_3WAY values (e.g. MyFieldIrregularEnum)
     <p>The underscore at the beginning of the names are there to tackle the Java identifier name problem.</p>
   * Implement getEnumName() method (it will be used to create the String representation of the enum value)
   * Implement static getEnumInstance(String) method (it will be used to find the enum value that corresponds to the specified String)
   * Specify the field name and the corresponding enum's fully qualified class name in the enum-field.properties file and indicate that it is an irregular enum:
    <p><code>MY_TABLE.MY_FIELD = com.mycompany.myapp.model.MyFieldIrregularEnum, IRREGULAR</code></p>
   * Specify the location of the enum-field.properties file in the EngineConfiguration (enumFieldFileName property)
   * Start generation

### Example irregular enum implementation

```java
public enum MyFieldIrregularEnum {

    NORMAL("NORMAL"),
    _2WAY("2WAY"),
    _3WAY("3WAY");

    private final String enumName;

    MyFieldIrregularEnum(String enumName) {
        this.enumName = enumName;
    }

    public String getEnumName() {
        return enumName;
    }

    public static MyFieldIrregularEnum getEnumInstance(String value) {
        for (MyFieldIrregularEnum v : values()) {
            if (v.getEnumName().equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException("No enum constant " + MyFieldIrregularEnum.class.getName() + "." + value);
    }

}
```

> If you just want **to use** this easy model and dao generator, then use the maven plugin at https://github.com/vanioinformatika/easydao-maven-plugin and a small dependeny in your project: https://github.com/vanioinformatika/easydao-core
