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

 using namespace std;

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_connect__Ljava_lang_String_2I(JNIEnv *env, jobject obj, jstring url, jint timeout) {

	call(env,obj,[env,url,timeout](TN3270::Session &session){
		session.connect(
			env->GetStringUTFChars(url,0),
			timeout
		);
		return 0;
	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_connect__I(JNIEnv *env, jobject obj, jint timeout) {

	call(env,obj,[timeout](TN3270::Session &session){
		session.connect(timeout);
		return 0;
	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_disconnect(JNIEnv *env, jobject obj) {

	call(env,obj,[](TN3270::Session &session){
		session.disconnect();
		return 0;
	});

 }
