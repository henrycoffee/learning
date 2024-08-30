package cc.learning.proxy.jdk;

public class StaticProxy implements People {

    private People people;

    public StaticProxy(People people) {
        this.people = people;
    }

    @Override
    public void eat() {
        System.out.println("before eat...");
        people.eat();
        System.out.println("after eat...");
    }

    @Override
    public void sleep() {
        System.out.println("before sleep...");
        people.sleep();
        System.out.println("after sleep...");
    }

    @Override
    public void walk() {
        System.out.println("before walk...");
        people.walk();
        System.out.println("after walk...");
    }

    public static void main(String[] args) {
        Man man = new Man();
        StaticProxy staticProxy = new StaticProxy(man);
        staticProxy.eat();
        staticProxy.sleep();
        staticProxy.walk();
    }
}
