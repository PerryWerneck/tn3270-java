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

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_getWidth(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getScreenWidth();
	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_getHeight(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getScreenHeight();
	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_getLength(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getScreenLength();
	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_getCursorAddress(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getCursorAddress();
	});

 }

 JNIEXPORT jobject JNICALL Java_br_app_pw3270_Terminal_getCursorPosition(JNIEnv *env, jobject obj) {

	return call(env,obj,[env,obj](TN3270::Session &session){

		TN3270::Session::Cursor cursor{session.getCursorPosition()};

		jclass jclass = env->FindClass("br/app/pw3270/Terminal$CursorPosition");

		if(!jclass) {
			throw runtime_error("Can't find 'CursorPosition' class");
		}

		jmethodID cid = env->GetMethodID(jclass, "<init>", "(Lbr/app/pw3270/Terminal;II)V");
		if(!cid) {
			throw runtime_error("Cant get 'CursorPosition' constructor cid");
		}

		return env->NewObject(jclass, cid, obj, (int) cursor.row, (int) cursor.col);

	});

 }

 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_getLib3270Version(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return session.getVersion();
	});

 }

 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_getLib3270Revision(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return session.getRevision();
	});

 }

 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_getAssociatedLUName(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return session.getAssociatedLUName();
	});

 }

 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_getURL(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return session.getHostURL();
	});

 }

 JNIEXPORT jboolean JNICALL Java_br_app_pw3270_Terminal_getConnected(JNIEnv *env, jobject obj, jint timeout) {

	return call(env,obj,[timeout](TN3270::Session &session){
		if(timeout) {
			session.waitForConnected(timeout);
		}
		return (int) session.connected();
	});

 }

 JNIEXPORT jboolean JNICALL Java_br_app_pw3270_Terminal_getReady(JNIEnv *env, jobject obj, jint timeout) {

	return call(env,obj,[timeout](TN3270::Session &session){
		if(timeout) {
			session.waitForReady(timeout);
		}
		return (int) session.ready();
	});
 }

 JNIEXPORT jboolean JNICALL Java_br_app_pw3270_Terminal_contains(JNIEnv *env, jobject obj, jstring chars) {

	return call(env,obj,[env,chars](TN3270::Session &session){
		return (int) session.contains(env->GetStringUTFChars(chars,0));
	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_find(JNIEnv *env, jobject obj, jstring chars, jint pos) {

	return call(env,obj,[env,chars,pos](TN3270::Session &session){
		return (int) session.find(env->GetStringUTFChars(chars,0),pos);
	});

 }


