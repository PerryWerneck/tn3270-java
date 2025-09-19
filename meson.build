# Maintainer: Perry Werneck <perry.werneck@gmail.com>

# SPDX-License-Identifier: LGPL-3.0-or-later 

# Copyright (C) 2025 Perry Werneck <perry.werneck@gmail.com>
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License as published
# by the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.

project(
	'jni3270', 
	['cpp', 'java'],
  default_options : ['c_std=c11', 'cpp_std=c++17'],
  meson_version: '>=1.0.0',
	version: '5.3.0',
	license: 'GPL-3.0-or-later',
)

project_description='Java Native Interface (JNI) library for 3270 terminal emulators'

#
# Versioning
#
pkg_version = meson.project_version()
version_array = pkg_version.split('.')
pkg_major_version = version_array[0].to_int()
pkg_minor_version = version_array[1].to_int()
pkg_micro_version = version_array[2].to_int()

libversion = '@0@.@1@'.format(pkg_major_version,pkg_minor_version)

#
# i18n
#
#subdir('po')

#
# Compiler flags
#
cxx = meson.get_compiler('cpp')
enable_debug = get_option('debug') or get_option('buildtype').contains('debug')

compiler_flags_common = [
  '-fvisibility=hidden',
  '-DHAVE_CONFIG_H=1',
  '-fPIC',
  get_option('buildtype').contains('debug') ? '-DDEBUG=1' : '-DNDEBUG=1',
]

add_project_arguments(cxx.get_supported_arguments(compiler_flags_common), language: 'cpp')

#
# Dependencies
#
jni = dependency('jni')

ipc3270 = dependency(
  'libipc3270', 
  required: true,
  fallback: [ 
    'libipc3270', 
    'static_library'
  ]
)

lib_deps = [
  ipc3270,
  jni
]

#
# Configuration
#
app_conf = configuration_data()
app_conf.set_quoted('PACKAGE_NAME', meson.project_name())
app_conf.set_quoted('PACKAGE_VERSION', meson.project_version())
app_conf.set_quoted('PACKAGE_DESCRIPTION', project_description)
app_conf.set('PACKAGE_VERSION_MAJOR', pkg_major_version)
app_conf.set('PACKAGE_VERSION_MINOR', pkg_minor_version)
app_conf.set('PACKAGE_VERSION_MICRO', pkg_micro_version)
app_conf.set('HAVE_GNUC_VISIBILITY', 1)

app_conf.set('PRODUCT_ID', ipc3270.get_variable('product_id'))
app_conf.set('PRODUCT_NAME', ipc3270.get_variable('product_name'))
#app_conf.set('PRODUCT_VERSION',lib3270.get_variable('sdk_version'))

config_src = [
  configure_file(
      output : 'config.h', 
      configuration : app_conf
  )
]

#
# Source files
#
sources = [
  'src/native/action.cc',
  'src/native/actions.cc',
  'src/native/connect.cc',
  'src/native/get.cc',
  'src/native/gettext.cc',
  'src/native/handler.cc',
  'src/native/info.cc',
  'src/native/set.cc',
  'src/native/states.cc',
  'src/native/tools.cc',
  'src/native/wait.cc',
  'src/native/workers.cc',
]

# 'src/native/os/windows/init.cc',
# 'src/native/os/windows/resources.rc.in',

includes_dir = include_directories(
        'src/include',
        '.',
    )

#
# Java stuff
#
jmod = import('java')

generated_headers = jmod.native_headers(
  [
    'src/Terminal.java',
  ],
  package: 'br.app.pw3270',
  classes: [ 'Terminal' ],
)

java_jar = jar(
  'java-tn3270-@0@'.format(meson.project_version()), 
  [ 'src/Terminal.java' ],
  install: host_machine.system() != 'windows',
  install_dir: '@0@/jvm-exports'.format(get_option('libdir'))
)

#
# Targets
#
if cxx.get_id() == 'msvc'

  native_lib = shared_library(
    meson.project_name(), 
    sources,
    install: false,
    dependencies: lib_deps,
    include_directories: includes_dir
  )

else

  native_lib = shared_library(
    meson.project_name(), 
    sources + [ generated_headers ],
    install: true,
    cpp_args: [ '-ffat-lto-objects' ],
    gnu_symbol_visibility: 'hidden',
    dependencies: lib_deps,
    include_directories: includes_dir,
    install_dir: '@0@/java'.format(get_option('libdir')),
  )
  
endif

# jar_compress = find_program('jar', required: true)

#if host_machine.system() == 'windows'
#
#  custom_target(
#    'bundle',
#    output: 'java-tn3270-bundle-@0@'.format(meson.project_version()), 
#    input: [java_jar, native_lib],
#    command: [
#        jar_compress,
#        'uf',
#        '@INPUT0@', # The JAR file from the previous step
#        native_lib.full_path()
#    ],
#    install: true,
#    install_dir: '@0@/java'.format(get_option('bindir')),
#    install_tag: 'devel',
#  )
#
#endif

configure_file(
  input : 'branding/metainfo.xml.in',
  output : ipc3270.get_variable('product_id') + '.metainfo.xml',
  install_dir: get_option('datadir') / 'metainfo',
  install: host_machine.system() != 'windows',
  configuration : app_conf
)

javadoc = find_program('javadoc', required: true)

custom_target(
  'javadoc',
  input: [ 'src/Terminal.java' ],
  output: 'tn3270',
  install: true,
  install_dir: '@0@/javadoc'.format(get_option('datadir')),
  command: [
    javadoc,
    '-encoding', 'utf-8',
    '-d', '@OUTPUT@',
    '@INPUT@',
  ],  
)
