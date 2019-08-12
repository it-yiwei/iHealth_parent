package com.yiwei.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class App {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationContext-jobs.xml");

    }

}
