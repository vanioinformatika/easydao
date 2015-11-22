Sample database running in docker container.

Run docker commands in creating-sampledb directory.

You can manage docker image start and stop with maven:

    mvn initialize -Pstart
    mvn initialize -Pstop
    
# Build docker image

    docker build -t easydao-sampledb .

# Start database and generating dao
    
    docker run --name sampledb -e POSTGRES_PASSWORD=sample -d easydao-sampledb
    ip=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' sampledb) && export DB_IP_ADDRESS=$ip

Build easydao:

    mvn clean install 

Test easydao:

    mvn clean verify

# Connecting to db with psql
    
    docker run -it --link sampledb:postgres --rm postgres sh -c 'exec psql -h "$POSTGRES_PORT_5432_TCP_ADDR" -p "$POSTGRES_PORT_5432_TCP_PORT" -U postgres'
    Password: sample

# Check database

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
    
# Docker postgres IP address

    docker inspect sampledb | grep IPAddress
    
# Stop and remove container and images

    docker stop sampledb && docker rm sampledb && docker rmi easydao-sampledb

