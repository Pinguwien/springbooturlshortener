package com.dwis.shorten;

/**
 * Created with IntelliJ IDEA.
 * User: Dominik
 * Date: 09.09.14
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
