package lt.lb.objects;

import javax.persistence.*;

@Entity
public class Currency {

    @Id
    @GeneratedValue
    private Long id;
    private String Ccy;
    private String CcyNmLT;
    private String CcyNmEN;
    private String CcyNbr;
    private String CcyMnrUnts;

    public Currency(){}

    public Currency(String ccy, String ccyNmLT, String ccyNmEN, String ccyNbr, String ccyMnrUnts) {
        Ccy = ccy;
        CcyNmLT = ccyNmLT;
        CcyNmEN = ccyNmEN;
        CcyNbr = ccyNbr;
        CcyMnrUnts = ccyMnrUnts;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
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

    @Override
    public String toString() {
        return "Currency{" +
                "Ccy='" + Ccy + '\'' +
                ", CcyNmLT='" + CcyNmLT + '\'' +
                ", CcyNmEN='" + CcyNmEN + '\'' +
                ", CcyNbr='" + CcyNbr + '\'' +
                ", CcyMnrUnts='" + CcyMnrUnts + '\'' +
                '}';
    }
}
