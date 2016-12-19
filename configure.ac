
dnl Process this file with autoconf to produce a configure script.

dnl The minimum version of autoconf required.
AC_PREREQ(2.59)

dnl Initialise automake with the package name, version and
dnl bug-reporting address.
AC_INIT([pw3270-java], [5.1], [perry.werneck@gmail.com])

dnl Place auxilliary scripts here.
AC_CONFIG_AUX_DIR([scripts])

dnl Compute the canonical host-system type
AC_CANONICAL_HOST

dnl Put macro definitions here (though they aren't used).
AC_CONFIG_HEADER([src/include/config.h])

dnl Initialise automake stuff.
AM_INIT_AUTOMAKE

dnl Check for iconv
AM_ICONV

dnl Checks for programs.
AC_PROG_CXX
AC_PROG_CC
AC_PROG_SED
AC_PROG_LN_S

AC_LANG([C++])

dnl ---------------------------------------------------------------------------
dnl Check for OS specifics
dnl ---------------------------------------------------------------------------

case "$host" in
	*-mingw32)

		EXEEXT=".exe"
		DLLPREFIX=""
		DLLEXT=".dll"

		CXXFLAGS="$CXXFLAGS -Wall -Werror -D_WIN32_WINNT=0x0600"
		LIBS="$LIBS -lws2_32 -lntdll -lwtsapi32"
		;;

 	s390x-*)

		EXEEXT=""
		DLLPREFIX="lib"
		DLLEXT=".so"

		CXXFLAGS="$CXXFLAGS -pthread -Wall"
		LIBS="-Wl,--no-undefined -pthread $LIBS"
		;;


 	*)

		EXEEXT=""
		DLLPREFIX="lib"
		DLLEXT=".so"

		CXXFLAGS="$CXXFLAGS -Wall -Werror"
		LIBS="-pthread $LIBS"

esac

dnl ---------------------------------------------------------------------------
dnl Check for other programs
dnl ---------------------------------------------------------------------------
AC_PATH_TOOL([STRIP], [strip])
AC_PATH_TOOL([RPMBUILD], [rpmbuild], [no])
AC_PATH_TOOL([MAKENSIS], [makensis], [no])
AC_PATH_TOOL([VALGRIND], [valgrind], [no])
AC_PATH_TOOL([WINDRES], [windres], [no])
AC_PATH_TOOL([ZIP], [zip], [zip])
AC_PATH_TOOL([FIND], [find], [find])
AC_PATH_TOOL([AR], [ar], [ar])

dnl ---------------------------------------------------------------------------
dnl Version info
dnl ---------------------------------------------------------------------------

app_vrs_major=$(echo $VERSION | cut -d. -f1)
app_vrs_minor=$(echo $VERSION | cut -d. -f2)

AC_SUBST(PACKAGE_MAJOR_VERSION,$app_vrs_major)
AC_SUBST(PACKAGE_MINOR_VERSION,$app_vrs_minor)

AC_DEFINE_UNQUOTED(PACKAGE_MAJOR_VERSION, $app_vrs_major)
AC_DEFINE_UNQUOTED(PACKAGE_MINOR_VERSION, $app_vrs_minor)

AC_SUBST(PACKAGE_MAJOR_VERSION,$app_vrs_major)
AC_SUBST(PACKAGE_MINOR_VERSION,$app_vrs_minor)

app_cv_description="Java binding for pw3270/lib3270"

AC_DEFINE_UNQUOTED(PACKAGE_DESCRIPTION,"$app_cv_description")
AC_SUBST(PACKAGE_DESCRIPTION,"$app_cv_description")

dnl ---------------------------------------------------------------------------
dnl Check for JDK
dnl ---------------------------------------------------------------------------

AC_ARG_VAR([JAVA_HOME], [JAVA home directory])
AC_ARG_VAR([JDK_HOME], [JDK home directory])
AC_ARG_VAR([JRE_HOME], [JRE home directory])

AC_ARG_WITH([jnidir], [AS_HELP_STRING([--with-jnidir], [Directory for JNI libraries])], [ jnidir="$withval" ],[ jnidir=\$"{libdir}/java" ])
AC_ARG_WITH([javadocdir], [AS_HELP_STRING([--with-javadocdir], [Directory for javadoc files])], [ javadocdir="$withval" ],[ javadocdir=\$"{datadir}/javadoc" ])

AC_ARG_WITH([jvmjardir], [AS_HELP_STRING([--with-jvmjardir], [Directory for JAR files])], [ jvmjardir="$withval" ],[ jvmjardir=\$"{libdir}/jvm-exports" ])

AC_ARG_ENABLE([java],[AS_HELP_STRING([--disable-java], [Disable java integration])],[ app_cv_java="$enableval" ],[ app_cv_java="yes" ])

if test "$app_cv_java" = "yes" ; then

	# Check for JAVA_HOME
	if test "_$JAVA_HOME" = "_" ; then
		JAVA_HOME=$libdir/jvm/java
	fi

	# Check for JDK_HOME
	if test "_$JDK_HOME" = "_" ; then
		JDK_HOME=$JAVA_HOME
	fi

	# Check for JRE_HOME
	if test "_$JRE_HOME" = "_" ; then
		JRE_HOME=$JAVA_HOME/jre
	fi
fi

if test "$app_cv_java" = "yes" ; then

	AC_PATH_TOOL([JAVA], [java], [no], [$JDK_HOME/bin:$JRE_HOME/bin:$PATH])
	if test "$JAVA" = "no" ; then
		app_cv_java="no"
	fi

	AC_PATH_TOOL([JAVAC], [javac], [no], [$JDK_HOME/bin:$JRE_HOME/bin:$PATH])
	if test "$JAVAC" = "no" ; then
		app_cv_java="no"
	fi

	AC_PATH_TOOL([JAVAH], [javah], [no], [$JDK_HOME/bin:$JRE_HOME/bin:$PATH])
	if test "$JAVAH" = "no" ; then
		app_cv_java="no"
	fi

	AC_PATH_TOOL([JAR], [jar], [no], [$JDK_HOME/bin:$JRE_HOME/bin:$PATH])
	if test "$JAVA" = "no" ; then
		app_cv_java="no"
	fi

	AC_PATH_TOOL([JAVADOC], [javadoc], [no], [$JDK_HOME/bin:$JRE_HOME/bin:$PATH])
	if test "$JAVADOC" = "no" ; then
		app_cv_java="no"
	fi

fi

# Set JVM arch
case $host_cpu in

x86_64)
	jvm_arch=amd64
	;;

i?86)
	jvm_arch=i386
	;;
powerpc64le)
	jvm_arch=ppc64le
	;;
powerpc64)
	jvm_arch=ppc64
	;;

*)
	jvm_arch="$host_cpu"
	;;

esac

# JVMDIR
if test "_$JVMDIR" = "_" ; then

	case "$host" in
		*-mingw32)
			jniinc=$JDK_HOME/include/win32
			JVMDIR="\$(JRE_HOME)/bin"
			;;

		*-apple-*)
			jniinc=$JDK_HOME/include/apple
			JVMDIR="\$(JRE_HOME)/lib/$jvm_arch/server"
			;;

		*)
			jniinc=$JDK_HOME/include/linux
			JVMDIR="\$(JRE_HOME)/lib/$jvm_arch/server"
	esac

fi


# Java link options depends on OS
case "$host" in
	*-mingw32)

		if test -e "$JRE_HOME/bin/client/jvm.dll"; then
			AC_MSG_NOTICE(Found $JRE_HOME/bin/client/jvm.dll)
			jvm_libs="-L\"\$(JRE_HOME)/bin/client\" -ljvm"
		elif test -e "$JRE_HOME/bin/server/jvm.dll"; then
			AC_MSG_NOTICE(Found $JRE_HOME/bin/server/jvm.dll)
			jvm_libs="-L\"\$(JRE_HOME)/bin/server\" -ljvm"
		else
			AC_MSG_NOTICE([Can't find jvm.dll.])
			app_cv_java="no"
		fi
		;;

	*-apple-*)
		jvm_libs="-L\$(JVMDIR) -Wl,-rpath,\$(JVMDIR) -ljvm"
		;;

	*)
		jvm_libs="-L\$(JVMDIR) -Wl,-rpath,\$(JVMDIR) -ljvm"
esac

if test -d "$jniinc"; then
	AC_MSG_NOTICE([Found $jniinc.])
else
	AC_MSG_NOTICE([Can't find $jniinc.])
	app_cv_java="no"
fi

if test "$app_cv_java" = "yes" ; then

	AC_MSG_NOTICE([jdk is present and enabled.])

else

	AC_MSG_NOTICE([jdk is NOT present or disabled.])

fi

CXXFLAGS="$CXXFLAGS -I$JDK_HOME/include -I$jniinc"

AC_SUBST(JVM_LIBS,$jvm_libs)

AC_SUBST(JAVAC)
AC_SUBST(JAVAH)
AC_SUBST(JAVADOC)
AC_SUBST(JVMDIR)
AC_SUBST(jnidir)
AC_SUBST(jvmjardir)
AC_SUBST(javadocdir)
AC_SUBST(JAR)
AC_SUBST(jvm_arch)
AC_SUBST(JDK_HOME)
AC_SUBST(JRE_HOME)


dnl ---------------------------------------------------------------------------
dnl Check for integer types & library functions
dnl ---------------------------------------------------------------------------

AC_CHECK_TYPES([pid_t, uint8_t, uint16_t, uint32_t, int32_t, uint64_t, int64_t])
AC_CHECK_FUNCS(srandom random getopt asprintf vasprintf)
AC_CHECK_FUNCS(getaddrinfo sendfile poll eventfd statfs64)

dnl ---------------------------------------------------------------------------
dnl Check for libintl
dnl ---------------------------------------------------------------------------

AC_PATH_TOOL([XGETTEXT], [xgettext], [no])
AC_PATH_TOOL([MSGCAT], [msgcat], [no])
AC_PATH_TOOL([MSGINIT], [msginit], [no])
AC_PATH_TOOL([MSGMERGE], [msgmerge], [no])
AC_PATH_TOOL([MSGFMT], [msgfmt], [no])

AC_CHECK_HEADER(libintl.h, [
	AC_DEFINE(HAVE_LIBINTL, 1)
])

dnl ---------------------------------------------------------------------------
dnl Check for C++ 2011 support
dnl ---------------------------------------------------------------------------
AC_DEFUN([AX_CHECK_COMPILE_FLAG],
[AC_PREREQ(2.59)dnl for _AC_LANG_PREFIX
AS_VAR_PUSHDEF([CACHEVAR],[ax_cv_check_[]_AC_LANG_ABBREV[]flags_$4_$1])dnl
AC_CACHE_CHECK([whether _AC_LANG compiler accepts $1], CACHEVAR, [
  ax_check_save_flags=$[]_AC_LANG_PREFIX[]FLAGS
  _AC_LANG_PREFIX[]FLAGS="$[]_AC_LANG_PREFIX[]FLAGS $4 $1"
  AC_COMPILE_IFELSE([AC_LANG_PROGRAM()],
    [AS_VAR_SET(CACHEVAR,[yes])],
    [AS_VAR_SET(CACHEVAR,[no])])
  _AC_LANG_PREFIX[]FLAGS=$ax_check_save_flags])
AS_IF([test x"AS_VAR_GET(CACHEVAR)" = xyes],
  [m4_default([$2], :)],
  [m4_default([$3], :)])
AS_VAR_POPDEF([CACHEVAR])dnl
])dnl AX_CHECK_COMPILE_FLAGS

AX_CHECK_COMPILE_FLAG([-std=gnu++11], [CXXFLAGS="$CXXFLAGS -std=gnu++11"])

dnl ---------------------------------------------------------------------------
dnl Check for ELF symbol visibility support
dnl ---------------------------------------------------------------------------
m4_define([GLIB_CHECK_COMPILE_WARNINGS],
[m4_ifvaln([$1], [AC_LANG_CONFTEST([$1])])dnl
rm -f conftest.$ac_objext
glib_ac_compile_save="$ac_compile"
ac_compile='$CC -c $CFLAGS $CPPFLAGS conftest.$ac_ext'
AS_IF([_AC_EVAL_STDERR($ac_compile) &&
         AC_TRY_COMMAND([(if test -s conftest.err; then false ; else true; fi)])],
      [$2],
      [echo "$as_me: failed program was:" >&AS_MESSAGE_LOG_FD
cat conftest.$ac_ext >&AS_MESSAGE_LOG_FD
m4_ifvaln([$3],[$3])dnl])
ac_compile="$glib_ac_compile_save"
rm -f conftest.$ac_objext conftest.err m4_ifval([$1], [conftest.$ac_ext])[]dnl
])# GLIB_CHECK_COMPILE_WARNINGS

AC_MSG_CHECKING(for GNUC visibility attribute)
GLIB_CHECK_COMPILE_WARNINGS([
void
__attribute__ ((visibility ("hidden")))
     f_hidden (void)
{
}
void
__attribute__ ((visibility ("internal")))
     f_internal (void)
{
}
void
__attribute__ ((visibility ("protected")))
     f_protected (void)
{
}
void
__attribute__ ((visibility ("default")))
     f_default (void)
{
}
int main (int argc, char **argv)
{
        f_hidden();
        f_internal();
        f_protected();
        f_default();
        return 0;
}
],g_have_gnuc_visibility=yes,g_have_gnuc_visibility=no)
AC_MSG_RESULT($g_have_gnuc_visibility)
if test x$g_have_gnuc_visibility = xyes; then
  AC_DEFINE(HAVE_GNUC_VISIBILITY, 1, [supports GCC visibility attributes])
fi

dnl ---------------------------------------------------------------------------
dnl Check for compiler visibility flag
dnl ---------------------------------------------------------------------------
AC_MSG_CHECKING(whether $CC accepts -fvisibility=hidden)
AC_CACHE_VAL(app_cv_fvisibility_ok,
[ac_save_cc="$CC"
CC="$CC -fvisibility=hidden"
AC_TRY_RUN([int main() { return 0; }],
  app_cv_fvisibility_ok=yes,
  app_cv_fvisibility_ok=no,
  app_cv_fvisibility_ok=no)
CC="$ac_save_cc"])
AC_MSG_RESULT($app_cv_fvisibility_ok)
if test $app_cv_fvisibility_ok = yes; then
  CXXFLAGS="$CXXFLAGS -fvisibility=hidden"
fi

dnl ---------------------------------------------------------------------------
dnl Check for pic
dnl ---------------------------------------------------------------------------
AC_ARG_ENABLE([pic],
	[AS_HELP_STRING([--disable-pic], [disable support for PIC libraries (required to compile on 64bits)])],
[
	app_cv_pic="$enableval"
],[

	case "$host" in
		*-mingw32)
			app_cv_pic="no"
			;;

 	s390x-*)
			app_cv_pic="yes"
			;;

	*)
		if test "$host_cpu" = "x86_64"; then
			app_cv_pic="yes"
		else
			app_cv_pic="no"
		fi
	esac
])

if test "$app_cv_pic" == "yes"; then
	CXXFLAGS="$CXXFLAGS -fPIC"
fi

dnl ---------------------------------------------------------------------------
dnl Output config
dnl ---------------------------------------------------------------------------

AC_SUBST(BASEDIR,$ac_pwd)
AC_SUBST(LIBS)
AC_SUBST(EXEEXT)
AC_SUBST(DLLEXT)
AC_SUBST(DLLPREFIX)


dnl ---------------------------------------------------------------------------
dnl Configure which files to generate.
dnl ---------------------------------------------------------------------------

AC_CONFIG_FILES(Makefile)
AC_CONFIG_FILES(src/jni/Makefile)
AC_CONFIG_FILES(src/plugin/Makefile)

dnl ---------------------------------------------------------------------------
dnl Output the generated config.status script.
dnl ---------------------------------------------------------------------------

AC_OUTPUT

