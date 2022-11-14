package com.example.callinginstancemethodincpp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.callinginstancemethodincpp.databinding.ActivityMainBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'callinginstancemethodincpp' library on application startup.
    static {
        System.loadLibrary("callinginstancemethodincpp");
    }

    private ActivityMainBinding binding;

    public static String callMethodWithReflection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Person person = new Person("Bob");

        Class<?> clazz = person.getClass();
        Method m1 = clazz.getDeclaredMethod("getName");
        Object obj = m1.invoke(person);
        String name = (String)obj;

        // with parameter
        Method m2 = clazz.getDeclaredMethod("printInfo", int.class);
        Object objPrintInfo = m2.invoke(person, 21);
        String Info = (String)objPrintInfo;

        // Static methods ->
        Method methodStaticMethod = Person.class.getDeclaredMethod("printPersonInfo", Person.class);
        Object objStaticMethod = methodStaticMethod.invoke(null, person);
        String printPersoninfoResult = (String) objStaticMethod;
        return printPersoninfoResult;
    }

    public static native String printPersonName(Person person);

    public static String callMethodWithNative(){
        Person person = new Person("Bjarne");
        return printPersonName(person);
    }

    public static native String callStaticPrintInfo(Person person);
    public static native void callSetInfo(Person person, String name, int age);

    public static String callStaticMethodWithNative(){
        Person person = new Person("Joe", 21);
        return callStaticPrintInfo(person);
    }

    public static String callSetInfoWithNative(){
        Person person = new Person("Bob",54);
        callSetInfo(person, "Jason", 42);
        return person.getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(callSetInfoWithNative());
    }

    /**
     * A native method that is implemented by the 'callinginstancemethodincpp' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}