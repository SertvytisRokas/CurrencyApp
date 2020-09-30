package lt.lb;

import lt.lb.controller.GetCurrencies;
import lt.lb.objects.Currency;
import lt.lb.objects.CurrencyExchange;
import lt.lb.repositories.CurrencyExchangeRepository;
import lt.lb.repositories.CurrencyRepository;
import lt.lb.repositories.NormalizedListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SebTestProjectApplication {

	@Autowired
	NormalizedListRepository normalizedListRepository;


	public static void main(String[] args) {
		SpringApplication.run(SebTestProjectApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(CurrencyRepository currencyRepository, CurrencyExchangeRepository currencyExchangeRepository){
		GetCurrencies getCurrencies = new GetCurrencies();
		List<Currency> list = getCurrencies.fillCountryList();

		List<CurrencyExchange> listExchange = getCurrencies.getExchange();


		return args -> {
			currencyRepository.saveAll(list);
			currencyExchangeRepository.saveAll(listExchange);
			this.normalizedListRepository.saveAll(getCurrencies.generateNormalizedList());
		};
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
