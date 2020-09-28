package lt.lb;


import com.jcabi.xml.XMLDocument;
import lt.lb.domain.Currency;
import org.hibernate.internal.util.xml.XmlDocument;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.jcabi.*;

public class MainServer {
    String tp; //Type
    //String ccy; //Currency
    String dtFrom; //Date from
    String drTo; //Date to

    String Ccy;
    String CcyNmLt;
    String CcyNmEn;
    String CcyNbr;
    String CcyMnrUnts;
    final static String CURRENT_FX_RATES_URL = "http://lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=EU";
    final static String CURRENCY_LIST_URL = "http://lb.lt//webservices/FxRates/FxRates.asmx/getCurrencyList";
    final static String CURRENCY_RATES_OVER_TIME_URL = "http://lb.lt/webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?";

    //http://lb.lt/webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?tp="+tp+"&ccy="+ccy+"&dtFrom="+dtFrom+"&dtTo="+drTo


    public static void main(String[] args) {
        MainServer server = new MainServer();
        server.fillCountryList();
    }
    

    private static HttpURLConnection connection;
    public List<Currency> fillCountryList() {
        List<Currency> dataList = new ArrayList<>();
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL url = new URL(CURRENCY_LIST_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            try {
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(responseContent.toString())));
                NodeList currencyList = document.getElementsByTagName("CcyNtry");
                for (int i = 0; i < currencyList.getLength(); i++) {
                    Node node = currencyList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        Ccy = element.getElementsByTagName("Ccy").item(0).getTextContent();
                        //System.out.println("Country code: " + element.getElementsByTagName("Ccy").item(0).getTextContent());
                        NodeList langList = element.getElementsByTagName("CcyNm");
                        for (int j = 0; j < langList.getLength(); j++) {
                            Node n = langList.item(j);
                            if (n.getNodeType() == Node.ELEMENT_NODE) {
                                Element el = (Element) n;
                                if (el.getAttribute("lang").equals("LT")) {
                                    CcyNmLt = el.getTextContent();
                                    //System.out.println("Country name in Lithuanian: " + el.getTextContent());
                                }
                                if (el.getAttribute("lang").equals("EN")) {
                                    CcyNmEn = el.getTextContent();
                                    //System.out.println("Country name in English: " + el.getTextContent());
                                }
                            }
                        }
                        CcyNbr = element.getElementsByTagName("CcyNbr").item(0).getTextContent();
                        //System.out.println("Country number: " + element.getElementsByTagName("CcyNbr").item(0).getTextContent());
                        CcyMnrUnts = element.getElementsByTagName("CcyMnrUnts").item(0).getTextContent();
                        //System.out.println("Country MnrUnts: " + element.getElementsByTagName("CcyMnrUnts").item(0).getTextContent());
                    }
                    dataList.add(new Currency(Ccy, CcyNmLt, CcyNmEn, CcyNbr, CcyMnrUnts));
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dataList);
        return dataList;
    }


}
