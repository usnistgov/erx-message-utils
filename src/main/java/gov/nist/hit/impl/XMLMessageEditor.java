package gov.nist.hit.impl;

import gov.nist.hit.MessageEditor;
import gov.nist.hit.MessageParserException;
import gov.nist.hit.core.domain.Message;
import gov.nist.hit.core.domain.TestContext;
import gov.nist.hit.core.xml.domain.XMLTestContext;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * Created by mcl1 on 1/19/16.
 */
public class XMLMessageEditor implements MessageEditor {

    @Override
    public String replaceInMessage(Message message, HashMap<String, String> idNewValueMap, TestContext context) throws Exception {
        if(context instanceof XMLTestContext){
            //XMLTestContext xmlTestContext = (XMLTestContext) context;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(IOUtils.toInputStream(message.getContent()));
            for(String key : idNewValueMap.keySet()){
                NodeList nodeList = doc.getElementsByTagName(key);
                for (int i=0; i<nodeList.getLength();i++){
                    nodeList.item(i).setTextContent(idNewValueMap.get(key));
                }
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            return result.getWriter().toString();

        }
        throw new MessageParserException("TestContext must be an instance of XMLTestContext ("+context.getClass().getName()+" found instead)");
    }
}
