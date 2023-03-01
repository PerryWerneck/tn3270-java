/* SPDX-License-Identifier: LGPL-3.0-or-later */

/*
 * Copyright (C) 2023 Perry Werneck <perry.werneck@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

 #pragma once
 #include <config.h>
 #include <lib3270/ipc.h>

 #if defined(_WIN32)

		#include <windows.h>

		#define DLL_PRIVATE	extern
		#define DLL_PUBLIC	extern __declspec (dllexport)

 #elif defined(__SUNPRO_C) && (__SUNPRO_C >= 0x550)

		#define DLL_PRIVATE		__hidden extern
		#define DLL_PUBLIC		extern

 #elif defined (HAVE_GNUC_VISIBILITY)

		#define DLL_PRIVATE		__attribute__((visibility("hidden"))) extern
		#define DLL_PUBLIC		__attribute__((visibility("default"))) extern

 #else

		#error Unable to set visibility attribute

 #endif

 #ifdef DEBUG
	#include <stdio.h>
	#undef trace
	#define trace( fmt, ... )       fprintf(stderr, "%s(%d) " fmt "\n", __FILE__, __LINE__, __VA_ARGS__ ); fflush(stderr);
	#define debug( fmt, ... )       fprintf(stderr, "%s(%d) " fmt "\n", __FILE__, __LINE__, __VA_ARGS__ ); fflush(stderr);
 #else
	#undef trace
	#define trace(x, ...)           // __VA_ARGS__
	#define debug(x, ...)           // __VA_ARGS__
 #endif

 #include <functional>

 DLL_PRIVATE TN3270::Session	& getSessionFromJObject(JNIEnv *env, jobject object);

 DLL_PRIVATE jint call(JNIEnv *env, jobject obj, const std::function<int(TN3270::Session &session)> &call);

