package lt.lb.objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NormalizedList {

    public NormalizedList(){}

    @Id
    @GeneratedValue
    private Long id;
    private String Ccy;
    private String CcyNmLt;
    private String CcyNmEn;
    private String exchangeRate;

    public NormalizedList(String Ccy, String CcyNmLt, String CcyNmEn, String exchangeRate){
        this.Ccy = Ccy;
        this.CcyNmLt=CcyNmLt;
        this.CcyNmEn=CcyNmEn;
        this.exchangeRate=exchangeRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NormalizedList{" +
                "Ccy='" + Ccy + '\'' +
                ", CcyNmLt='" + CcyNmLt + '\'' +
                ", CcyNmEn='" + CcyNmEn + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                '}';
    }

    public String getCcy() {
        return Ccy;
    }

    public void setCcy(String ccy) {
        Ccy = ccy;
    }

    public String getCcyNmLt() {
        return CcyNmLt;
    }

    public void setCcyNmLt(String ccyNmLt) {
        CcyNmLt = ccyNmLt;
    }

    public String getCcyNmEn() {
        return CcyNmEn;
    }

    public void setCcyNmEn(String ccyNmEn) {
        CcyNmEn = ccyNmEn;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
