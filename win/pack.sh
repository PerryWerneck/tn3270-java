#!/bin/bash

myDIR=$(dirname $(dirname $(readlink -f $0)))

cd $myDIR

rm -f *.zip

win32-configure && make clean && make zip
if [ "$?" != "0" ]; then
	exit -1
fi

win64-configure && make clean && make zip
if [ "$?" != "0" ]; then
	exit -1
fi

if [ -d ~/public_html/win/pw3270/x86_32 ]; then
	cp *i686*.zip ~/public_html/win/pw3270/x86_32
elif [ -d ~/public_html/win/x86_32 ]; then
	cp *i686*.zip ~/public_html/win/x86_32
fi

if [ -d ~/public_html/win/pw3270/x86_64 ]; then
	cp *x86_64*.zip ~/public_html/win/pw3270/x86_64
elif [ -d ~/public_html/win/x86_32 ]; then
	cp *x86_64*.zip ~/public_html/win/x86_64
fi

