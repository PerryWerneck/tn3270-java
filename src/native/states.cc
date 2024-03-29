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

 #include <config.h>
 #include <jni.h>
 #include <jni3270.h>
 #include <lib3270/ipc/session.h>
 #include <private/br_app_pw3270_Terminal.h>
 #include <stdexcept>

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_getProgramMessage(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getProgramMessage();
	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_getConnectionState(JNIEnv *env, jobject obj) {

 	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getConnectionState();
	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_getSSLState(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getSSLState();
	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_getKeyboardLockState(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getKeyboardLockState();
	});

 }

