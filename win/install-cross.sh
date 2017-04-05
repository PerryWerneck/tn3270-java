#!/bin/bash

install_packages()
{

# Instala apicativos e temas necessários
sudo zypper --non-interactive in \
	${1}-cross-gcc-c++  \
	${1}-cross-pkg-config \
	${1}-cross-cpp \
	${1}-cross-binutils \
	${1}-pw3270 \
	${1}-lib3270-devel \
	${1}-pw3270-devel

}


until [ -z "${1}" ]
do
	if [ ${1:0:2} = '--' ]; then
		tmp=${1:2}
		parameter=${tmp%%=*}
		parameter=$(echo $parameter | tr "[:lower:]" "[:upper:]")

		case $parameter in

		ar)
			zypper ar --refresh http://download.opensuse.org/repositories/windows:/mingw:/win32/openSUSE_42.2/ mingw32
			zypper ar --refresh http://download.opensuse.org/repositories/windows:/mingw:/win64/openSUSE_42.2/ mingw64
			zypper ar --refresh http://download.opensuse.org/repositories/home:/PerryWerneck:/mingw32/openSUSE_Leap_42.2/ mingw32-pw3270
			zypper ar --refresh http://download.opensuse.org/repositories/home:/PerryWerneck:/mingw64/openSUSE_Leap_42.2/ mingw64-pw3270
			zypper ref
			;;

		32)
			install_packages mingw32
			;;

		64)
			install_packages mingw64
			;;

		ALL)
			install_packages mingw32
			install_packages mingw64
			;;


		*)
			value=${tmp##*=}
			eval $parameter=$value
		esac

	fi

	shift
done


