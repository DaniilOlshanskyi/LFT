package lft.demo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The main application class for the SpringBoot.
 * 
 * @author Daniil Olshanskyi
 *
 */
@SpringBootApplication
//@ComponentScan(basePackages={"lft.demo","lft.demo.games","lft.demo.reports","lft.demo.user_has_games","lft.demo.user_has_report","lft.demo.websocket"})
//@EntityScan(basePackages={"lft.demo","lft.demo.games","lft.demo.reports","lft.demo.user_has_games","lft.demo.user_has_report","lft.demo.websocket"})
//@EnableJpaRepositories(basePackages={"lft.demo","lft.demo.games","lft.demo.reports","lft.demo.user_has_games","lft.demo.user_has_report","lft.demo.websocket"})
public class Application {

    public static void main(String[] args) {
//    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//    	try {
//            ctx.register(Application.class);
//            ctx.refresh();
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println("Spring component scant test");
//            System.out.println();
//            System.out.println();
//            System.out.println("Bean profiles: "
//                    + ctx.getBean("Profiles"));
//            System.out.println();
//            System.out.println();
//           
//        } finally {
//            ctx.close();
//        }
        SpringApplication.run(Application.class, args);
    }
    /*
	TESTBLOCK SHOULD BE UPDATED
	*/
    /*
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
*/
    
}
