package com.example.jnitest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.jnitest.databinding.ActivityMainBinding;
import  java.lang.reflect.Field;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'jnitest' library on application startup.
    static {
        System.loadLibrary("jnitest");
    }

    private ActivityMainBinding binding;

    public native String printName(Person person);

    public static String accessFieldWithReflection() throws NoSuchFieldException, SecurityException, IllegalAccessException {
        Person person = new Person("Bob");

        Class<?> clazz = person.getClass();

        Field f1 = clazz.getField("name");
        Object nameObj = f1.get(person);
        String name = (String)nameObj;
        return name;
    }

    public static String test2(){
        MainActivity test = new MainActivity();
        Person person = new Person("Uncle Bob Dyson");

        String res = test.printName(person);

        return res;
    }

    public static String getStaticFieldWithReflection() throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = Person.class;

        Field field = clazz.getDeclaredField("ID");

        Object IDObj = field.get(Person.class);
        String ID = (String) IDObj;
        return ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;

        tv.setText(test2());



    }

    /**
     * A native method that is implemented by the 'jnitest' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}