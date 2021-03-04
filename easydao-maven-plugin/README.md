# Test and release

Install Oracle JDBC driver before start testing or building:

    mvn install:install-file -Dfile=./ojdbc5-11.1.0.7.0.jar -DgroupId=com.oracle -DartifactId=ojdbc5 -Dversion=11.1.0.7.0 -Dpackaging=jar

    mvn install:install-file -Dfile=./orai18n-11.1.0.7.0.jar -DgroupId=com.oracle -DartifactId=orai18n -Dversion=11.1.0.7.0 -Dpackaging=jar

Start, stop sample database:

    docker-compose up -d --build
    docker-compose down

Test easydao (docker postgres image):

    mvn clean verify

Build easydao:

    mvn clean install

Releasing:

    git pull
    docker-compose up -d --build
    mvn release:prepare
    mvn release:perform
    git push && git push --tag
    docker-compose down

## Working with database

### Connecting to db with psql

    psql -U postgres -h 127.0.0.1 -p 5432
    Password: sample

### Check database

    postgres=# \c sampledb
    You are now connected to database "sampledb" as user "postgres".

    sampledb=# \d
                    List of relations
    Schema |          Name           |   Type   |  Owner   
    --------+-------------------------+----------+----------
    public | adr_address             | table    | postgres
    public | cus_customer            | table    | postgres
    public | seq_adr_address_adr_pk  | sequence | postgres
    public | seq_cus_customer_cus_pk | sequence | postgres
    (4 rows)

    sampledb=# select * from cus_customer;
    cus_pk | cus_adr_pk |   cus_name   |       cus_email        |            cus_type            |         cus_reg_ts         | cus_locked |     cus_modificaton_ts     
    --------+------------+--------------+------------------------+--------------------------------+----------------------------+------------+----------------------------
        1 |          1 | Jack Daniels | jack.daniels@earth.sun | RETAIL                         | 2015-11-21 12:26:53.632058 | f          | 2015-11-21 12:26:53.632058
        2 |          2 | John Python  | john.python@venus.sun  | WHOLESALE                      | 2015-11-21 12:26:53.634029 | t          | 2015-11-21 12:26:53.634029
        3 |          3 | Jessie Java  | jessie.java@mars.sun   | GOVERNMENT                     | 2015-11-21 12:26:53.635883 | f          | 2015-11-21 12:26:53.635883
    (3 rows)

    sampledb=# select * from adr_address;
    adr_pk |     adr_street      | adr_locality  |   adr_postal_code    |               adr_country                | adr_locked |     adr_modificaton_ts     
    --------+---------------------+---------------+----------------------+------------------------------------------+------------+----------------------------
        1 | 1145 17th St NW     | WASHINGTON DC | 20036                | USA                                      | f          | 2015-11-21 12:26:53.626752
        2 | 600 Maryland Ave SW | WASHINGTON DC | 20002                | USA                                      | f          | 2015-11-21 12:26:53.628808
        3 | 736 Sicard St SE    | WASHINGTON DC | 20374                | USA                                      | f          | 2015-11-21 12:26:53.630444
    (3 rows)

    sampledb=# select cus_name, adr_street from cus_customer, adr_address where cus_adr_pk = adr_pk;
      cus_name   |     adr_street      
    --------------+---------------------
    Jack Daniels | 1145 17th St NW
    John Python  | 600 Maryland Ave SW
    Jessie Java  | 736 Sicard St SE
    (3 rows)

