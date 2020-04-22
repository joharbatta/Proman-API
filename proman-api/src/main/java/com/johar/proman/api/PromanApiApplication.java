package com.johar.proman.api;

import com.johar.proman.service.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ServiceConfiguration.class)
public class PromanApiApplication {

    public static void main(String[] x)
    {
        SpringApplication.run(PromanApiApplication.class,x);
    }
}


//editor.swagger.io
// mvn clean install -Psetup -DskipTests
//http://localhost:3030/api/swagger-ui.html

//client           api           server



//json


//name = abc
//id = 101
//marks = 89



//{
// "name":'abc',
// "id":101,
// "marks": 89
//}









