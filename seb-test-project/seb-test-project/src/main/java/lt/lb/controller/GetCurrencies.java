package lt.lb.controller;

import lt.lb.objects.Currency;
import lt.lb.objects.CurrencyChange;
import lt.lb.objects.CurrencyExchange;
import lt.lb.objects.NormalizedList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetCurrencies {

    private static HttpURLConnection connection;
    private static HttpURLConnection connection2;
    private static HttpURLConnection connection3;
    final static String CURRENT_FX_RATES_URL = "http://lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=EU";
    final static String CURRENCY_LIST_URL = "http://lb.lt//webservices/FxRates/FxRates.asmx/getCurrencyList";
    final static String CURRENCY_RATES_OVER_TIME_URL = "http://lb.lt/webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?";
    String Ccy;
    String CcyNmLt;
    String CcyNmEn;
    String CcyNbr;
    String CcyMnrUnts;

    String countryCode;
    String exchangeRate;

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
                        this.Ccy = element.getElementsByTagName("Ccy").item(0).getTextContent();
                        NodeList langList = element.getElementsByTagName("CcyNm");
                        for (int j = 0; j < langList.getLength(); j++) {
                            Node n = langList.item(j);
                            if (n.getNodeType() == Node.ELEMENT_NODE) {
                                Element el = (Element) n;
                                if (el.getAttribute("lang").equals("LT")) {
                                    this.CcyNmLt = el.getTextContent();
                                }
                                if (el.getAttribute("lang").equals("EN")) {
                                    this.CcyNmEn = el.getTextContent();
                                }
                            }
                        }
                        this.CcyNbr = element.getElementsByTagName("CcyNbr").item(0).getTextContent();
                        this.CcyMnrUnts = element.getElementsByTagName("CcyMnrUnts").item(0).getTextContent();
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
        return dataList;
    }

    public List<CurrencyExchange> getExchange(){
        List<CurrencyExchange> dataList = new ArrayList<>();
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try{
            URL url = new URL(CURRENT_FX_RATES_URL);
            connection2 = (HttpURLConnection) url.openConnection();
            connection2.setRequestMethod("GET");
            connection2.setConnectTimeout(5000);
            connection2.setReadTimeout(5000);
            int status = connection2.getResponseCode();
            if(status>299){
                reader = new BufferedReader(new InputStreamReader(connection2.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            } else{
                reader = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            try{
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(responseContent.toString())));
                NodeList exchangeList = document.getElementsByTagName("CcyAmt");
                for(int i=0; i<exchangeList.getLength(); i++){
                    Node node = exchangeList.item(i);
                    if(node.getNodeType() == Node.ELEMENT_NODE){
                        Element element = (Element) node;
                        if(!element.getElementsByTagName("Ccy").item(0).getTextContent().equals("EUR")){
                            this.countryCode=element.getElementsByTagName("Ccy").item(0).getTextContent();
                            this.exchangeRate=element.getElementsByTagName("Amt").item(0).getTextContent();
                        }
                    }
                    dataList.add(new CurrencyExchange(countryCode, exchangeRate));
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
        return dataList;
    }

    public List<NormalizedList> generateNormalizedList(){
        GetCurrencies getCurrencies = new GetCurrencies();
        List<Currency> currencyList = new ArrayList<>();
        currencyList = getCurrencies.fillCountryList();
        List<CurrencyExchange> currencyExchangeList = new ArrayList<>();
        currencyExchangeList = getCurrencies.getExchange();
        List<NormalizedList> normalizedList = new ArrayList<>();
        NormalizedList normalizedListItem;
        for(int i=0; i<currencyList.size(); i++){
            for(int j=0; j<currencyExchangeList.size(); j++){
                if(currencyList.get(i).getCcy().equals(currencyExchangeList.get(j).getCountryCode())){
                    normalizedListItem = new NormalizedList(currencyList.get(i).getCcy(), currencyList.get(i).getCcyNmLT(), currencyList.get(i).getCcyNmEN(), currencyExchangeList.get(j).getExchangeRate());
                    normalizedList.add(normalizedListItem);
                }
            }
        }
        return normalizedList;
    }

    public CurrencyChange returnChangeObject(String ccy, String from, String to){
        List<String> valuesList = new ArrayList<>();
        String fromString = "";
        String toString = "";
        String INPUT_URL = CURRENCY_RATES_OVER_TIME_URL + "tp=EU&ccy=" + ccy + "&dtFrom=" + from + "&dtTo=" + to;
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try{
            URL url = new URL(INPUT_URL);
            connection3 = (HttpURLConnection) url.openConnection();
            connection3.setRequestMethod("GET");
            connection3.setConnectTimeout(5000);
            connection3.setReadTimeout(5000);
            int status = connection3.getResponseCode();
            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection3.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            } else{
                reader = new BufferedReader(new InputStreamReader(connection3.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            try{
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(responseContent.toString())));
                NodeList nodeList = document.getElementsByTagName("CcyAmt");
                for(int i=0; i<nodeList.getLength(); i++){
                    Node node = nodeList.item(i);
                    if(node.getNodeType() == Node.ELEMENT_NODE){
                        Element element = (Element) node;
                        if(!element.getElementsByTagName("Ccy").item(0).getTextContent().equals("EUR")){
                            valuesList.add(element.getElementsByTagName("Amt").item(0).getTextContent());
                        }
                    }
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(valuesList!= null) {
            fromString = valuesList.get(0);
            toString = valuesList.get(valuesList.size()-1);
        }
        return new CurrencyChange(fromString, toString);
    }

}
