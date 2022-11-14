#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_callinginstancemethodincpp_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_callinginstancemethodincpp_MainActivity_printPersonName(JNIEnv *env, jclass clazz,
                                                                         jobject person) {
    // TODO: implement printPersonName()
    //static jclass person_class = env->FindClass("com/example/callinginstancemethodincpp/Person");
    static jclass person_class = env->GetObjectClass(person);
    // String döndüren parametresiz fonksiyon ->
    jmethodID get_mame_id = env->GetMethodID(person_class, "getName", "()Ljava/lang/String;");
    jobject str = env->CallObjectMethod(person, get_mame_id);
    jstring name = (jstring)str;

    // String döndüren int parametre alan fonksiyon ->
    jmethodID print_info_id = env->GetMethodID(person_class,"printInfo", "(I)Ljava/lang/String;");
    jobject str_print_info = env->CallObjectMethod(person, print_info_id, 21);
    jstring info = (jstring) str_print_info;


    return info;
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_callinginstancemethodincpp_MainActivity_callStaticPrintInfo(JNIEnv *env,
                                                                             jclass clazz,
                                                                             jobject person) {
    // Step 1 : get jclass
    jclass person_class = env->FindClass("com/example/callinginstancemethodincpp/Person");
    // Step 2 : get method identifier
    jmethodID print_info_id = env->GetStaticMethodID(person_class, "printPersonInfo", "(Lcom/example/callinginstancemethodincpp/Person;)Ljava/lang/String;");
    // Step 3 : call the method
    jobject obj = env->CallStaticObjectMethod(person_class, print_info_id, person);
    jstring str = (jstring)obj;

    return str;
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_callinginstancemethodincpp_MainActivity_callSetInfo(JNIEnv *env, jclass clazz,
                                                                     jobject person, jstring name,
                                                                     jint age) {

    // Step 1 : get jclass
    jclass person_class = env->FindClass("com/example/callinginstancemethodincpp/Person");
    // Step 2 : get method identifier
    jmethodID print_info_id = env->GetStaticMethodID(person_class,"setInfo", "(Lcom/example/callinginstancemethodincpp/Person;Ljava/lang/String;I)V");
    // Step 3 : call the method
    env->CallStaticVoidMethod(person_class, print_info_id, person, name, age);
}