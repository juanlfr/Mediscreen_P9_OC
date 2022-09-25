#!/bin/bash

sleep 30

mongoimport --drop --host mongo --db Notes --collection notes --type json --jsonArray --file /tmp/init.json
