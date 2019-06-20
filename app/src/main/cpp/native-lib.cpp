#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_zx_haijixing_HaiNativeHelper_baseUrl(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "3EQFT5RGGF5Y45FFSAAD23";
    return env->NewStringUTF(hello.c_str());
}
