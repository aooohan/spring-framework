package com.example.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class FindAnnotationOnBeanTests {

    @Test
    void beanDefinedInInstanceMethodDoesNotHaveAnnotationsFromItsConfigurationClass() {
        beanDoesNotHaveAnnotationsFromItsConfigurationClass(InstanceBeanMethodConfiguration.class);
    }

    @Test
    void beanDefinedInStaticMethodDoesNotHaveAnnotationsFromItsConfigurationClass() {
        beanDoesNotHaveAnnotationsFromItsConfigurationClass(StaticBeanMethodConfiguration.class);
    }

    void beanDoesNotHaveAnnotationsFromItsConfigurationClass(Class<?> config) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(config)) {
            ExampleAnnotation annotation = context.getBeanFactory().findAnnotationOnBean("exampleBean",
                    ExampleAnnotation.class);
            assertThat(annotation).isNull();
        }
    }

    @Configuration
    @ExampleAnnotation
    static class StaticBeanMethodConfiguration {

        @Bean
        static String exampleBean() {
            return "example";
        }

    }

    @Configuration
    @ExampleAnnotation
    static class InstanceBeanMethodConfiguration {

        @Bean
        String exampleBean() {
            return "example";
        }

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    static @interface ExampleAnnotation {

    }

}