#
# spec file for package tn3270-java
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

# Please submit bugfixes or comments via https://github.com/PerryWerneck/tn3270-java/issues
#

Summary:        Java class to interact with pw3270/tn3270
Name:			tn3270-java
Version: 5.2.0
Release:		0
License:		LGPL-3.0-only
Source:			%{name}-%{version}.tar.xz
URL:			https://github.com/PerryWerneck/tn3270-java
Group: 			Development/Languages/Java

BuildRoot:		/var/tmp/%{name}-%{version}

BuildRequires:	meson
BuildRequires:	gcc-c++
BuildRequires:	m4
BuildRequires:	libtool
BuildRequires:	pkgconfig
BuildRequires:	pkgconfig(libipc3270) >= 5.5.0
BuildRequires:	gettext-tools

BuildRequires:	java-devel
BuildRequires:	javapackages-tools
BuildRequires:	fdupes

%if 0%{?suse_version}
BuildRequires:	appstream-glib
%endif

Recommends:		pw3270-plugin-ipc

%description
This package provides Java class for lib3270/pw3270 interaction.

%package -n tn3270-javadoc 
Summary:		Javadoc for %{name} 
Group:			Documentation 
Requires:		jpackage-utils
BuildArch:		noarch

%description -n tn3270-javadoc
API documentation for %{name}. 

%prep
%autosetup
%meson

%build
%meson_build

%install
%meson_install
%fdupes -s %{buildroot}%{_javadocdir}

%if 0%{?suse_version}
appstream-util validate-relax --nonet %{buildroot}%{_datadir}/metainfo/*.metainfo.xml
%endif

%clean
rm -rf $RPM_BUILD_ROOT

%files
%defattr(-,root,root)
%doc README.md
%license LICENSE
%dir %{_jnidir}
%dir %{_jvmjardir}

%{_jnidir}/*.so
%{_jvmjardir}/*.jar

%{_datadir}/metainfo/*.metainfo.xml

%files -n tn3270-javadoc
%defattr(-,root,root)
%dir %{_javadocdir}/tn3270
%{_javadocdir}/tn3270/*

%changelog


