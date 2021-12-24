#!/bin/bash
grep -w -o  "[1-9]\{1\}[0-9]\{1,14\}" phone-numbers | wc -w
