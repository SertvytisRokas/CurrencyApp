package lt.lb.controller;

import lt.lb.repositories.NormalizedListRepository;
import lt.lb.objects.CurrencyChange;
import lt.lb.objects.NormalizedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestfulController {

    @Autowired
    NormalizedListRepository normalizedListRepository;

    @GetMapping("/displayAll")
    public List<NormalizedList> showAll(){
        return (List<NormalizedList>) normalizedListRepository.findAll();
    }

    @GetMapping("/exchange")
    public String exchangeSingle(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to, @RequestParam(value = "amount") String amount){
        String answer="";
        String eurValueFrom="";
        String eurValueTo="";
        List<NormalizedList> normalizedList = (List<NormalizedList>) normalizedListRepository.findAll();
        for(int i=0; i<normalizedList.size(); i++){
            if(normalizedList.get(i).getCcy().equals(from)){
                eurValueFrom = normalizedList.get(i).getExchangeRate();
            }
            if(normalizedList.get(i).getCcy().equals(to)){
                eurValueTo = normalizedList.get(i).getExchangeRate();
            }
        }
        double rate = Double.parseDouble(eurValueTo)/Double.parseDouble(eurValueFrom);
        String rateString = String.valueOf(rate);
        double answerDouble = (1/Double.parseDouble(eurValueFrom)) * Double.parseDouble(eurValueTo);
        double timesAmount = answerDouble * Double.parseDouble(amount);
        answer = String.valueOf(timesAmount);
        String exchanged = amount + " " + from + " exchanged to " + to +" is " + answer + " " + to + " with an exchange rate of " +rateString;
        return exchanged;
    }

    @GetMapping("/exchangeOverPeriod")
    public String exchangePeriod(@RequestParam(value = "ccy") String ccy, @RequestParam(value = "from") String from, @RequestParam(value = "to") String to){
        GetCurrencies getCurrencies = new GetCurrencies();
        CurrencyChange currencyChange = getCurrencies.returnChangeObject(ccy,from, to);
        String change;
        if(Double.parseDouble(currencyChange.from) > Double.parseDouble(currencyChange.to)){
            change = "DROPPED";
        } else{
            change = "INCREASED";
        }
        String result = "At " + from + " value of " + ccy + " was " + currencyChange.from + " and until " + to + " it has " + change + " to " + currencyChange.to;
        return result;
    }



}
