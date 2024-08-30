package cc.learning.proxy.jdk;

public class Man implements  People{
    private String name;
    private int age;

    public void eat(){
        System.out.println("eat...");
    }

    public void sleep(){
        System.out.println("sleep...");
    }

    public void walk(){
        System.out.println("walk...");
    }
}
