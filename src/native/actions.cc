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
 #include <lib3270/ipc/action.h>
 #include <lib3270/ipc/session.h>
 #include <private/br_app_pw3270_Terminal.h>
 #include <stdexcept>

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_activate(JNIEnv *env, jobject obj, jstring action) {

	call(env,obj,[env, action](TN3270::Session &session){

		session.activate(env->GetStringUTFChars(action,0));
		return 0;

	});

 }

 JNIEXPORT jboolean JNICALL Java_br_app_pw3270_Terminal_activatable(JNIEnv *env, jobject obj, jstring action) {

	return call(env,obj,[env,action](TN3270::Session &session){

		return (int) session.activatable(env->GetStringUTFChars(action,0));

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_pfkey(JNIEnv *env, jobject obj, jint key) {

	call(env,obj,[env, key](TN3270::Session &session){

		session.pfkey((int) key);
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_pakey(JNIEnv *env, jobject obj, jint key) {

	call(env,obj,[env, key](TN3270::Session &session){

		session.pakey((int) key);
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_enter(JNIEnv *env, jobject obj) {

	call(env,obj,[env](TN3270::Session &session){

		session.push(TN3270::ENTER);
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_erase(JNIEnv *env, jobject obj) {

	call(env,obj,[env](TN3270::Session &session){

		session.push(TN3270::ERASE);
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_erase_1eol(JNIEnv *env, jobject obj) {

	call(env,obj,[env](TN3270::Session &session){

		session.push(TN3270::ERASE_EOL);
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_erase_1input(JNIEnv *env, jobject obj) {

	call(env,obj,[env](TN3270::Session &session){

		session.push(TN3270::ERASE_INPUT);
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_erase_1eof(JNIEnv *env, jobject obj) {

	call(env,obj,[env](TN3270::Session &session){

		session.push(TN3270::ERASE_EOF);
		return 0;

	});

 }

