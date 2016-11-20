#!/bin/sh

gradle CopyConfig
gradle CopyRuntimeLibs
gradle buildVidsellDataSimulatorBot
gradle CopyShellScripts

echo "\n !!!!! BUILD DONE !!!!! \n"
