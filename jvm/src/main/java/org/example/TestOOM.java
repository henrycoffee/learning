package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestOOM {
    //假设你希望启动一个Java应用程序，并设置初始堆大小为128MB，最大堆大小为512MB，可以使用以下命令：
    //java -Xms128m -Xmx512m -jar your-application.jar
    public static void main(String[] args) {
//        ArrayList<Object> objects = new ArrayList<>();
//        while (true) {
//            StringBuilder stringBuilder = new StringBuilder();
//            objects.add(stringBuilder);
//        }
        testPermOOM();
    }


    /**
     * 常量池OOM
     *  VM Args：
     * JDK7之前：-XX:PermSize=6M -XX:MaxPermSize=6M
     * JDK7之后 字符串常量池挪到了堆中 使用  Xms1m -Xmx2m
     *   Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     *
     * @author zzm
     */

    public static void testPermOOM() {
        // 使用Set保持着常量池引用，避免Full GC回收常量池行为
        Set<String> set = new HashSet<>();
        // 在short范围内足以让6MB的PermSize产生OOM了
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }

}