package com.bridgelabz.indiancensusanalyser.controller.service;

public class Demo {
    void call() {
        String a = "Soumen";
        String b = " Soumen";
        String c = a.concat(b);
        String d = a + b;

        System.out.println(a.hashCode());
        System.out.println(b.hashCode());

    }

    public static void main(String[] args) {
        Demo d = new Demo();
        d.call();

    }
}
