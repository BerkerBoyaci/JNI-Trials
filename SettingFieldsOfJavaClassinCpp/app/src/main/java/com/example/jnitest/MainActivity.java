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
    public native void setNameAndAge(Person person, String name, String ID);

    public static String setNameAndAgeWithNativeCpp(){
        Person person = new Person("Uncle Bob Dyson");

        MainActivity activity = new MainActivity();
        activity.setNameAndAge(person, "James Cpp" , "25");

        return person.getID();
    }

    public static String accessFieldWithReflection() throws NoSuchFieldException, SecurityException, IllegalAccessException {
        Person person = new Person("Bob");

        Class<?> clazz = person.getClass();

        Field f1 = clazz.getField("name");
        Object nameObj = f1.get(person);
        String name = (String)nameObj;
        return name;
    }

    public static String test2() throws NoSuchFieldException, IllegalAccessException {
        MainActivity test = new MainActivity();
        Person person = new Person("Uncle Bob Dyson");

        Class<?> clazz = Person.class;
        Field field = clazz.getDeclaredField("ID");
        field.set(person, "42");


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

    public static String setStaticFieldWithReflection() throws NoSuchFieldException, IllegalAccessException {
        Person person = new Person("Uncle Bob Dyson");

        Class<?> clazz = Person.class;
        Field field = clazz.getDeclaredField("ID");
        field.set(person, "42");


        return person.getID();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;

        tv.setText(setNameAndAgeWithNativeCpp());



    }

    /**
     * A native method that is implemented by the 'jnitest' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}