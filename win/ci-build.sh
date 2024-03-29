#!/bin/bash -x
#
# References:
#
#	* https://www.msys2.org/docs/ci/
#
#
echo "Running ${0}"

LOGFILE=build.log
rm -f ${LOGFILE}

die ( ) {
	[ "$1" ] && echo "$*"
	exit -1
}

myDIR=$(dirname $(dirname $(readlink -f ${0})))

cd ${myDIR}
rm -fr ./.build
mkdir -p ./.build

if [ ! -z "${JAVA_HOME}" ]; then
	JAVA_HOME=$(echo ${JAVA_HOME} | sed 's@C:\\@/c/@g;s@\\@/@g')
	echo "JAVA_HOME=${JAVA_HOME}"
	PATH="${PATH}:${JAVA_HOME}/bin"
fi

if [ ! -z "${JDK_HOME}" ]; then
	JDK_HOME=$(echo ${JDK_HOME} | sed 's@C:\\@/c/@g;s@\\@/@g')
	echo "JDK_HOME=${JDK_HOME}"
	PATH="${PATH}:${JDK_HOME}/bin"
fi

if [ ! -z "${JRE_HOME}" ]; then
	JRE_HOME=$(echo ${JRE_HOME} | sed 's@C:\\@/c/@g;s@\\@/@g')
	echo "JRE_HOME=${JRE_HOME}"
	PATH="${PATH}:${JRE_HOME}/bin"
fi

echo "javac=[$(which javac)]"

which javac
if [ "$?" != "0" ]; then
	echo "Cant find javac"
	exit -1;
fi

ls -l *

#
# Build LIB3270
#
if [ -e mingw-lib3270.tar.xz ]; then

	echo "Unpacking lib3270"
	tar -C / -Jxvf mingw-lib3270.tar.xz 

else
	echo "Building lib3270"
	git clone https://github.com/PerryWerneck/lib3270.git ./.build/lib3270 || die "clone lib3270 failure"
	cd ./.build/lib3270
	./autogen.sh || die "Lib3270 autogen failure"
	./configure || die "Lib3270 Configure failure"
	make clean || die "Lib3270 Make clean failure"
	make all || die "Lib3270 Make failure"
	make install || die "Lib3270 Install failure"
	cd ../..
fi

#
# Build LIBIPC3270
#
if [ -e mingw-libipc3270.tar.xz ]; then

	echo "Unpacking lib3270"
	tar -C / -Jxvf mingw-libipc3270.tar.xz 

else
	echo "Building libipc3270"
	git clone https://github.com/PerryWerneck/libipc3270.git ./.build/libipc3270 || die "clone libipc3270 failure"
	cd ./.build/libipc3270
	./autogen.sh || die "libipc3270 Autogen failure"
	./configure || die "libipc3270 Configure failure"
	make clean || die "libipc3270 Make clean failure"
	make all || die "libipc3270 Make failure"
	make install || die "libipc3270 Install failure"
	cd ../..
fi

#
# Build TN3270-Java
#
echo "Building TN3270-Java"
./autogen.sh || die "Autogen failure"
./configure || die "Configure failure"
make clean || die "Make clean failure"
make package || die "Make failure"
make zip  || die "Make failure"

echo "Build complete"

