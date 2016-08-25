package edu.clemson.resolve.jetbrains;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by daniel on 8/24/16.
 */
public class Foo {

    @Nullable
    public static void incr(@NotNull Person x) {

    }

    public static void main(String[] args) {
        Person y = new Person();
        y = nullify(y);
        int i;
        i=0;
    }

    public static class Person {
        public int x, y;
        public String name = "Jerry";

    }
}
