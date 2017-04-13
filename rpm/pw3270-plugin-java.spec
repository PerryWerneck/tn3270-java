#
# spec file for package pw3270-plugin-java
#
# Copyright (c) 2015 SUSE LINUX GmbH, Nuernberg, Germany.
# Copyright (C) <2008> <Banco do Brasil S.A.>
#
# All modifications and additions to the file contributed by third parties
# remain the property of their copyright owners, unless otherwise agreed
# upon. The license for this file, and modifications and additions to the
# file, is the same license as for the pristine package itself (unless the
# license for the pristine package is not an Open Source License, in which
# case the license is the MIT License). An "Open Source License" is a
# license that conforms to the Open Source Definition (Version 1.9)
# published by the Open Source Initiative.

# Please submit bugfixes or comments via http://bugs.opensuse.org/
#

%define vrslib %(pkg-config --modversion lib3270)

Summary:		Plugin module for embedding java on pw3270.
Name:			pw3270-plugin-java
Version:		5.1
Release:		0
License:		GPL-2.0
Source:			%{name}-%{version}.tar.bz2
URL:			https://softwarepublico.gov.br/gitlab/pw3270/pw3270-java
Group: 			Development/Languages/Java

BuildRoot:		/var/tmp/%{name}-%{version}

BuildRequires:  autoconf >= 2.61
BuildRequires:  automake
BuildRequires:  binutils
BuildRequires:  coreutils
BuildRequires:  gcc-c++
BuildRequires:  m4
BuildRequires:  pkgconfig
BuildRequires:	pkgconfig(pw3270) >= 5.1
BuildRequires:	pkgconfig(lib3270) >= 5.1

BuildRequires:  java-devel
BuildRequires:  javapackages-tools
BuildRequires:	fdupes

Requires:		pw3270 >= 5.1
Requires:		java-extension-tn3270 = %{version}

%description

Plugin module for java plugins in pw3270.

This package provides a pw3270 plugin allowing use o rexx scripts
directly from pw3270 main window.

%package -n pw3270-java

Summary:        Java class to interact with pw3270
Group:          Development/Libraries/Java
Requires:       java
Requires:		lib3270 = %{vrslib}

%description -n pw3270-java

This package provides Java class for lib3270/pw3270 interaction.

%package -n pw3270-java-doc 
Summary:        Javadoc for %{name} 
Group:          Documentation 
Requires:       jpackage-utils

%description -n pw3270-java-doc 
API documentation for %{name}. 

%prep

%setup

export CFLAGS="$RPM_OPT_FLAGS"
export CXXFLAGS="$RPM_OPT_FLAGS"
export FFLAGS="$RPM_OPT_FLAGS"
export JAVA_HOME=%{java_home}

aclocal
autoconf

%configure	--with-jnidir="%{_jnidir}" \
			--with-jvmjardir="%{_jvmjardir}" \
			--with-javadocdir="%{_javadocdir}"

%build
make clean
make Release

%install
rm -rf $RPM_BUILD_ROOT

%make_install

%fdupes $RPM_BUILD_ROOT

%clean
rm -rf $RPM_BUILD_ROOT

%files
%defattr(-,root,root)
%{_libdir}/pw3270-plugins/j3270.so

%files -n pw3270-java
%defattr(-,root,root)
%doc README.md
%dir %{_jnidir}
%dir %{_jvmjardir}

%{_jnidir}/libjni3270.so
%{_jvmjardir}/pw3270.jar

%files -n pw3270-java-doc
%defattr(-,root,root)
%dir %{_javadocdir}/pw3270
%{_javadocdir}/pw3270/*

%changelog


