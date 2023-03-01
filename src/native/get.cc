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

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_get_1screen_1width(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getScreenWidth();
	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_get_1screen_1height(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getScreenHeight();
	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_get_1screen_1length(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getScreenLength();
	});

 }

 JNIEXPORT jint JNICALL Java_br_app_pw3270_Terminal_get_1cursor_1address(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return (int) session.getCursorAddress();
	});

 }

 JNIEXPORT jobject JNICALL Java_br_app_pw3270_Terminal_get_1cursor_1position(JNIEnv *env, jobject obj) {

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

 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_get_1lib3270_1version(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return session.getVersion();
	});

 }

 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_get_1lib3270_1revision(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return session.getRevision();
	});

 }

 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_get_1associated_1lu_1name(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return session.getAssociatedLUName();
	});

 }

 JNIEXPORT jstring JNICALL Java_br_app_pw3270_Terminal_get_1host_1url(JNIEnv *env, jobject obj) {

	return call(env,obj,[](TN3270::Session &session){
		return session.getHostURL();
	});

 }
