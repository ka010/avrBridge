#!/bin/bash
sudo rmmod avrBridge
make
sudo insmod avrBridge.ko
sudo cat /dev/avrBridge0
