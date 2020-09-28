package lt.lb;

import com.jcabi.immutable.Array;
import lt.lb.domain.Currency;
import lt.lb.domain.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SebTestProjectApplication {



	MainServer server;
	public static void main(String[] args) {
		SpringApplication.run(SebTestProjectApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(CurrencyRepository repository){
//		List<Currency> currencyList = server.fillCountryList();
//		return args -> {
//			//for (int i = 0; i < currencyList.size(); i++) {
//				//repository.save(currencyList.get(i));
//			//}
//			repository.save(new Currency("LT", "das", "asdasd", "asdasdas", "asdasd"));
//		};
//	}


}
