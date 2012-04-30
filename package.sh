#!/bin/sh
rm -rf net immibis
mkdir -p net/minecraft/server
./compile.sh
cp mod_*.class net/minecraft/server/
zip -r immibis-core-tubestuff-mcpc125-r1.zip net/ immibis
