package what.the.jpa2nd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Jpa2ndApplication {

	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.setData("ddd");
		String data = hello.getData();

		SpringApplication.run(Jpa2ndApplication.class, args);
	}

}
