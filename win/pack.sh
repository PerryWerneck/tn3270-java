#!/bin/bash

myDIR=$(dirname $(dirname $(readlink -f $0)))

cd $myDIR

rm -f *.zip

win32() {

	win32-configure && bash win/makepackage.sh
	if [ "$?" != "0" ]; then
		exit -1
	fi

	if [ -d ~/public_html/win/pw3270/x86_32 ]; then
		cp ${myDIR}/*i686*.zip ~/public_html/win/pw3270/x86_32
	elif [ -d ~/public_html/win/x86_32 ]; then
		cp ${myDIR}/*i686*.zip ~/public_html/win/x86_32
	fi

}

win64() {

	win64-configure && bash win/makepackage.sh
	if [ "$?" != "0" ]; then
		exit -1
	fi

	if [ -d ~/public_html/win/pw3270/x86_64 ]; then
		cp -v ${myDIR}/*x86_64*.zip ~/public_html/win/pw3270/x86_64
	elif [ -d ~/public_html/win/x86_32 ]; then
		cp -v ${myDIR}/*x86_64*.zip ~/public_html/win/x86_64
	fi

}

win32
win64



