package learn;

import learn.ui.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@PropertySource("classpath:data.properties")
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        Controller controller = context.getBean(Controller.class);
        controller.run();

        //run all tests using this email
        //Host: irosenkranc8w@reverbnation.com
        //Guest: slomas0@mediafire.com
    }
}
