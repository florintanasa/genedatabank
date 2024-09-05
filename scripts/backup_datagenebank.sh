#!/bin/bash
###
### This script backup the database genedatabank
###

# get the day date
DAY=$(date +%d_%m_%Y)
# get the hour time
HOUR=$(date +%H_%M_%S)
# dump database and crompress with  gzip at better level compresion (-9)
$(pg_dump -U svgenebank -d genedatabank | gzip -9 > /opt/genedatabank/Backup_GeneDataBank/genedatabank_"$DAY"_"$HOUR".sql.gz)
$(tar -czf /opt/genedatabank/Backup_GeneDataBank/jmix_"$DAY"_"$HOUR".tar.gz .jmix/)
# dump database and crompress with  xz
#$(pg_dump -U svgenebank -d genedatabank | xz > /opt/genedatabank/Backup_GeneDataBank/genedatabank_"$DAY"_"$HOUR".sql.xz)
###
### To restore run:
### gunzip -c genedatabank_$DAY_$HOUR.sql.gz | psql genedatabank
### tar -xf jmix_"$DAY"_"$HOUR".tar.gz -C /opt/genedatabank  # attention to do not rewrite existing directory
###
### or for xv
###
### xzcat genedatabank_$DAY_$HOUR.sql.xz | psql genedatabank
###
