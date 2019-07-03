#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_zx_haijixing_HaiNativeHelper_baseUrl(
        JNIEnv* env,
        jobject /* this */) {
    std::string baseUrl = "3EQFT5RGGF5Y45FFSAAD23";
    return env->NewStringUTF(baseUrl.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_zx_haijixing_HaiNativeHelper_ossUrl(
        JNIEnv* env,
        jobject /* this */) {
    std::string ossUrl = "3EQFT5RGGF5Y45FFSAAD23";
    return env->NewStringUTF(ossUrl.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_zx_haijixing_HaiNativeHelper_ossToken(
        JNIEnv* env,
        jobject /* this */) {
    std::string ossToken = "3EQFT5RGGF5Y45FFSAAD23";
    return env->NewStringUTF(ossToken.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_zx_haijixing_HaiNativeHelper_ossBuck(
        JNIEnv* env,
        jobject /* this */) {
    std::string ossBuck = "3EQFT5RGGF5Y45FFSAAD23";
    return env->NewStringUTF(ossBuck.c_str());
}