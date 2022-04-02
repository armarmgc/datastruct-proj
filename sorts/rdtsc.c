#include <jni.h>
#include <x86intrin.h>
#include "LinkedList.h"

JNIEXPORT jlong JNICALL Java_LinkedList_rdtsc(JNIEnv *env, jobject obj) {
	return _rdtsc();
}

