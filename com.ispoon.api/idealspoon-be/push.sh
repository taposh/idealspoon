#!/bin/bash
if [[ $1 = stg ]] ; then
	key=stg.pem
	shift 1
elif [[ $1 = qa ]] ; then
	key=qa.pem
	shift 1
else
	key=dev.pem
fi
if [[ -z $1 ]] ; then
	dis=ifb
else
	dis=$1
fi
echo $key - $dis
mvn -DskipTests package
scp -i ~/.ssh/${key} target/ifbrands.war ec2-user@${dis}:

