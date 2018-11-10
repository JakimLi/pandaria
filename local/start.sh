#!/usr/bin/env bash
mkdir build
nohup java -jar local/moco-runner-0.12.0-standalone.jar http -p 12306 -c local/server.json -s 9527 > build/nohup.out 2> build/error.out &
