#include <jni.h>
#include <string>
#include <iostream>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_jnitest_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_jnitest_MainActivity_printName(JNIEnv *env, jobject thiz, jobject person) {
    // TODO: implement printName()
    jclass person_class = env->FindClass("com/example/jnitest/Person");

    // step 2: field id
    jfieldID  fieldID = env->GetFieldID(person_class, "name", "Ljava/lang/String;");
    jfieldID  staticFieldID = env->GetStaticFieldID(person_class, "ID", "Ljava/lang/String;");
    // step 3 : get the field
    jobject name_obj = env->GetObjectField(person, fieldID);
    jstring name = (jstring) name_obj;
    const char* name_array = env->GetStringUTFChars(name,0);
    std::cout << name_array << "\n";

    jobject ID_obj = env->GetStaticObjectField(person_class, staticFieldID);
    jstring ID_name = (jstring) ID_obj;
    const char* ID = env->GetStringUTFChars(ID_name, 0);

    env->ReleaseStringUTFChars( ID_name, ID);
    env->ReleaseStringUTFChars(name, name_array);

    return name;
}