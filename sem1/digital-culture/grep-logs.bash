#!/bin/bash
mkdir tmp
unzip  grep-logs.zip -d tmp
cd tmp
zgrep  "HTTP/1.1 5"  *.gz |  cut -d " " -f 5 | sort | uniq    

cd ../
rm -r  tmp
