#!/bin/bash
sudo rmmod avrBridge_proc
make
sudo insmod avrBridge_proc.ko

