package lt.lb.objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CurrencyExchange {

    @Id
    @GeneratedValue
    private Long id;
    private String countryCode;
    private String exchangeRate;

    public CurrencyExchange(String countryCode, String exchangeRate){
        this.countryCode=countryCode;
        this.exchangeRate=exchangeRate;
    }

    public CurrencyExchange(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "CurrencyExchange{" +
                "id=" + id +
                ", countryCode='" + countryCode + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                '}';
    }
}
