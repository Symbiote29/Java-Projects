#!/bin/bash
NETWORK=fantunovi_mreza_1

docker run -it -d \
  -p 8070:8080 \
  --network=$NETWORK \
  --ip 200.20.0.4 \
  --name=fantunovi_payara_micro \
  --hostname=fantunovi_payara_micro \
  fantunovi_payara_micro:6.2023.4 \
  --deploy /opt/payara/deployments/fantunovi_aplikacija_2-1.0.0.war \
  --contextroot fantunovi_aplikacija_2 \
  --noCluster &

wait
