package com.atguigu.springcloud;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("now = " + now);

    }

    public static void paseData(){
        Person p1 = new Person(1, "对公", 220);
        Person p2 = new Person(1, "零售", 210);
        Person p3 = new Person(1, "运营", 290);
        Person p4 = new Person(1, "信息", 320);
        Person p5 = new Person(1, "安全", 420);
        Person p6 = new Person(2, "对公", 10);
        Person p7 = new Person(2, "零售", 666);
        Person p8 = new Person(2, "运营", 777);
        Person p9 = new Person(2, "信息", 888);
        List<Person> list = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9);

        List<String> nameList = list.stream()
                .map(Person::getName)
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(
                                () -> new TreeSet<String>(Comparator.comparing(String::toUpperCase)))
                        , ArrayList::new));

        Map<Integer, Map<String, Integer>> result = new HashMap<>();
        list.stream().forEach(item -> {
            Map<String, Integer> temp = result.get(item.getNum());
            if (temp == null || temp.size() < 0) {
                Map<String, Integer> finalTemp = new HashMap<>();
                nameList.stream().forEach(template -> {
                    finalTemp.put(template, 0);
                });
                finalTemp.put(item.getName(), item.getAge());
                result.put(item.getNum(), finalTemp);
            } else {
                temp.put(item.getName(), item.getAge());
                result.put(item.getNum(), temp);
            }
        });

        System.out.println("result = " + result);

    }

}


class Person {
    private Integer num;
    private String name;
    private Integer age;


    public Person(Integer num, String name, Integer age) {
        this.num = num;
        this.name = name;
        this.age = age;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Person{" +
                "num=" + num +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}