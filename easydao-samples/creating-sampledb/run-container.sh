docker run --name sampledb -e POSTGRES_PASSWORD=sample -d easydao-sampledb

ip=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' sampledb) && export DB_IP_ADDRESS=$ip

