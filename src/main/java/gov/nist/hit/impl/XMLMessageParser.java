package gov.nist.hit.impl;

import gov.nist.hit.MessageParser;
import gov.nist.hit.core.domain.Message;
import gov.nist.hit.core.domain.TestContext;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This software was developed at the National Institute of Standards and Technology by employees of
 * the Federal Government in the course of their official duties. Pursuant to title 17 Section 105
 * of the United States Code this software is not subject to copyright protection and is in the
 * public domain. This is an experimental system. NIST assumes no responsibility whatsoever for its
 * use by other parties, and makes no guarantees, expressed or implied, about its quality,
 * reliability, or any other characteristic. We would appreciate acknowledgement if the software is
 * used. This software can be redistributed and/or modified freely provided that any derivative
 * works bear some notice that they are derived from it, and any modified versions bear some notice
 * that they have been modified.
 * <p/>
 * Created by Maxence Lefort on 2/14/16.
 */
public class XMLMessageParser implements MessageParser {
    @Override
    public Map<String, String> readInMessage(Message message, ArrayList<String> dataToBeFound, TestContext context) throws Exception {
        HashMap<String,String> dataRead = new HashMap<>();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(IOUtils.toInputStream(message.getContent()));
        for(String key : dataToBeFound){
            NodeList nodeList = doc.getElementsByTagName(key);
            if(nodeList.getLength()>0){
                dataRead.put(key,nodeList.item(0).getTextContent());
            }
        }
        return dataRead;
    }
}
