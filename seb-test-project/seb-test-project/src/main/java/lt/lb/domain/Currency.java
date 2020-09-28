package lt.lb.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Currency {

    @Id @GeneratedValue
    private String Ccy;
    private String CcyNmLT;
    private String CcyNmEN;
    private String CcyNbr;
    private String CcyMnrUnts;

    public Currency(String ccy, String ccyNmLT, String ccyNmEN, String ccyNbr, String ccyMnrUnts) {
        Ccy = ccy;
        CcyNmLT = ccyNmLT;
        CcyNmEN = ccyNmEN;
        CcyNbr = ccyNbr;
        CcyMnrUnts = ccyMnrUnts;
    }

    public String getCcy() {
        return Ccy;
    }

    public void setCcy(String ccy) {
        Ccy = ccy;
    }

    public String getCcyNmLT() {
        return CcyNmLT;
    }

    public void setCcyNmLT(String ccyNmLT) {
        CcyNmLT = ccyNmLT;
    }

    public String getCcyNmEN() {
        return CcyNmEN;
    }

    public void setCcyNmEN(String ccyNmEN) {
        CcyNmEN = ccyNmEN;
    }

    public String getCcyNbr() {
        return CcyNbr;
    }

    public void setCcyNbr(String ccyNbr) {
        CcyNbr = ccyNbr;
    }

    public String getCcyMnrUnts() {
        return CcyMnrUnts;
    }

    public void setCcyMnrUnts(String ccyMnrUnts) {
        CcyMnrUnts = ccyMnrUnts;
    }
}
