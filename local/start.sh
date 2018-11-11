#!/usr/bin/env bash
mkdir -p build
nohup python -m SimpleHTTPServer 12306 > build/nohup.out 2>build/error.out &
