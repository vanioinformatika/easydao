docker run -d -p 15432:5432 --name sampledb -e POSTGRES_PASSWORD=sample easydao-sampledb

#ip=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' sampledb) && export DB_IP_ADDRESS=$ip
export DB_IP_ADDRESS=127.0.0.1:15432
