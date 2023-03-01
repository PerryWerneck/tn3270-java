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

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_setURL(JNIEnv *env, jobject obj, jstring url) {

 	call(env,obj,[env,url](TN3270::Session &session){

		session.setHostURL(env->GetStringUTFChars(url,0));
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_setUnlockDelay(JNIEnv *env, jobject obj, jint delay) {

 	call(env,obj,[env,delay](TN3270::Session &session){

		session.setUnlockDelay((int) delay);
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_setTimeout(JNIEnv *env, jobject obj, jint timeout) {

 	call(env,obj,[env,timeout](TN3270::Session &session){

		session.setTimeout((int) timeout);
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_setLockOnOperatorError(JNIEnv *env, jobject obj, jboolean lock) {

 	call(env,obj,[env,lock](TN3270::Session &session){

		session.setLockOnOperatorError((bool) lock);
		return 0;

	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_setCursorPosition__I(JNIEnv *env, jobject obj, jint addr) {

 	return call(env,obj,[env,addr](TN3270::Session &session){

		return session.setCursor(addr);

	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_setCursorPosition__II(JNIEnv *env, jobject obj, jint row, jint col) {

 	return call(env,obj,[env,row,col](TN3270::Session &session){

		return session.setCursor(row,col);

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_setCharSet(JNIEnv *env, jobject obj, jstring charset) {

 	call(env,obj,[env,charset](TN3270::Session &session){

		session.setCharSet(env->GetStringUTFChars(charset,0));
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_setProperty__Ljava_lang_String_2I(JNIEnv *env, jobject obj, jstring name, jint value) {

 	call(env,obj,[env,name,value](TN3270::Session &session){

		session.setProperty(env->GetStringUTFChars(name,0),(int) value);
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_setProperty__Ljava_lang_String_2Z(JNIEnv *env, jobject obj, jstring name, jboolean value) {

 	call(env,obj,[env,name,value](TN3270::Session &session){

		session.setProperty(env->GetStringUTFChars(name,0),(bool) value);
		return 0;

	});

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_setProperty__Ljava_lang_String_2Ljava_lang_String_2(JNIEnv *env, jobject obj, jstring name, jstring value) {

 	call(env,obj,[env,name,value](TN3270::Session &session){

		session.setProperty(env->GetStringUTFChars(name,0),env->GetStringUTFChars(value,0));
		return 0;

	});

 }
