#!/bin/bash

echo "ZAUSTAVI"
docker stop fantunovi_payara_micro
echo "OBRISI"
docker container rm fantunovi_payara_micro
echo "PRIPREMI"
./scripts/pripremiSliku.sh
echo "POKRENI"
./scripts/pokreniSliku.sh