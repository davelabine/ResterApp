#! /bin/bash -e
until nc -z $SVC_HOST $SVC_PORT;
do
echo waiting for $SVC_HOST;
sleep 2;
done;


curl -v -H "Content-Type: application/json" -X POST http://$SVC_HOST:$SVC_PORT/api/students/populate?num=$SVC_POP


