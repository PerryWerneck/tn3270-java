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
 #include <lib3270/ipc.h>
 #include <lib3270/ipc/session.h>
 #include <private/br_app_pw3270_Terminal.h>
 #include <stdexcept>

 using namespace std;

 struct Handler {

	std::shared_ptr<TN3270::Session> hSession;

	Handler(const char *id, const char *charset) {

		if(!id[0]) {
			id = nullptr;
		}

		if(!charset[0]) {
			charset = nullptr;
		}

		hSession = TN3270::Session::getInstance(id,charset);

	}

 };

 TN3270::Session & getSessionFromJObject(JNIEnv *env, jobject object) {

	Handler * handler =
		reinterpret_cast<Handler *>(
			env->GetLongField(
				object,
				env->GetFieldID(
					env->GetObjectClass(object),
					"hSession",
					"J"
				)
			)
		);

	if(!handler) {
		throw runtime_error("Session is not open");
	}

	return *handler->hSession;

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_open(JNIEnv *env, jobject object, jstring id, jstring charset) {

	try {

		auto field =
			env->GetFieldID(
				env->GetObjectClass(object),
				"hSession",
				"J"
			);

		Handler * handler =
			reinterpret_cast<Handler *>(
				env->GetLongField(
					object,
					field
				)
			);

		if(handler) {
			throw runtime_error("Session is already open");
		}

		handler = new Handler(env->GetStringUTFChars(id,0),env->GetStringUTFChars(charset,0));

		env->SetLongField(
			object,
			field,
			reinterpret_cast<jlong>(handler)
		);

	} catch(const std::exception &e) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());

	} catch(...) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), "Unexpected error opening session");

	}

 }

 JNIEXPORT void JNICALL Java_br_app_pw3270_Terminal_close(JNIEnv *env, jobject object) {

	try {

		auto field =
			env->GetFieldID(
				env->GetObjectClass(object),
				"hSession",
				"J"
			);

		Handler * handler =
			reinterpret_cast<Handler *>(
				env->GetLongField(
					object,
					field
				)
			);

		if(!handler) {
			throw runtime_error("Session is already closed");
		}

		env->SetLongField(object, field, 0);
		delete handler;

	} catch(const std::exception &e) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());

	} catch(...) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), "Unexpected error closing session");

	}

 }

 jint call(JNIEnv *env, jobject obj, const std::function<int(TN3270::Session &session)> &call) {

	try {

		return (jint) call(getSessionFromJObject(env,obj));

	} catch(const std::exception &e) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), e.what());

	} catch(...) {

		env->ThrowNew(env->FindClass("java/lang/Exception"), "Unexpected error");

	}

	return -1;

 }

