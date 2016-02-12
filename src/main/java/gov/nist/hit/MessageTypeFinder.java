package gov.nist.hit;

import gov.nist.hit.beans.Separators;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by mcl1 on 1/19/16.
        */
public class MessageTypeFinder {
    private Map<String,String> xmlMessageTypes = new HashMap<>();
    private List<String> ediMessageTypes = Arrays.asList("canres", "canrx", "chgres", "error", "newrx", "refreq", "refres", "rxchg", "rxfill", "rxhreq", "rxhres", "status", "verify");

    private static MessageTypeFinder instance = null;

    private MessageTypeFinder() {
        initializeXmlMessageTypes();
    }

    public static MessageTypeFinder getInstance(){
        if(instance==null){
            instance = new MessageTypeFinder();
        }
        return instance;
    }

    public String findEdiMessageType(String message){
        Separators separators = EDIMessageUtil.getSeparators(message);
        message = message.replace("\n","");
        String[] segments = message.split(separators.getLineSeparator());
        for(int i=0;i<segments.length;i++){
            String segment = segments[i];
            if(segment.startsWith("UIH")){
                for(String type : ediMessageTypes) {
                    if(segment.toLowerCase().contains(type)){
                        return type;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public String findXmlMessageType(String message) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(IOUtils.toInputStream(message));
        for(String key : xmlMessageTypes.keySet()){
            NodeList bodyChildren = doc.getElementsByTagName("Body").item(0).getChildNodes();
            for(int i=0;i<bodyChildren.getLength();i++){
                if(bodyChildren.item(i).getNodeName()==key){
                    return xmlMessageTypes.get(key);
                }
            }
        }
        return null;
    }

    private void initializeXmlMessageTypes() {
        xmlMessageTypes.put("CancelRxResponse","canres");
        xmlMessageTypes.put("CancelRx","canrx");
        xmlMessageTypes.put("RxChangeResponse","chgres");
        xmlMessageTypes.put("Error","error");
        xmlMessageTypes.put("NewRx","newrx");
        xmlMessageTypes.put("RefillRequest","refreq");
        xmlMessageTypes.put("RefillResponse","refres");
        xmlMessageTypes.put("RxChangeRequest","rxchg");
        xmlMessageTypes.put("RxFill","rxfill");
        xmlMessageTypes.put("RxHistoryRequest","rxhreq");
        xmlMessageTypes.put("RxHistoryResponse","rxhres");
        xmlMessageTypes.put("Status","status");
        xmlMessageTypes.put("Verify","verify");
    }
}
