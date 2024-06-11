package _Team.bus_assistant_backend.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@Service
public class ApiService {

    public String call_api(String stationId, String routeId) throws IOException {
        String api_url = "http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalItem";
        String api_key = "8IUN6Mgf0R1P09LBFA/lr+EvH1UxkcBuiXcQ+3NVnLfzMUYdgQg25u3Eezkw6ZEzsWodaiuHB6fiZWcTVCXXjw==";
        String urlBuilder = api_url + "?" +
                "serviceKey=" + URLEncoder.encode(api_key, "UTF-8") +
                "&stationId=" + URLEncoder.encode(stationId, "UTF-8") +
                "&routeId=" + URLEncoder.encode(routeId, "UTF-8");

        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String getLocationNo1(String xml) throws Exception {
        Document doc = parseXml(xml);
        return getElementValue(doc, "locationNo1");
    }

    public String getPlateNo1(String xml) throws Exception {
        Document doc = parseXml(xml);
        return getElementValue(doc, "plateNo1");
    }

    private Document parseXml(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        return builder.parse(inputStream);
    }

    private String getElementValue(Document doc, String tagName) {
        NodeList nodeList = doc.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
