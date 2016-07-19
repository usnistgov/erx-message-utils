import gov.nist.hit.core.domain.Message;
import gov.nist.hit.core.domain.TestContext;
import gov.nist.hit.core.xml.domain.XMLTestContext;
import gov.nist.hit.impl.EdiMessageParser;
import gov.nist.hit.impl.XMLMessageEditor;
import gov.nist.hit.impl.XMLMessageParser;
import gov.nist.hit.utils.XMLUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mcl1 on 1/19/16.
 */
public class MessageEditorTest {




    @Test
    public void testXMLEditor() throws ParserConfigurationException, IOException, SAXException, TransformerException {

        String xmlMessage = IOUtils.toString(this.getClass().getResourceAsStream("message.xml"));
        XMLMessageEditor xmlMessageEditor = new XMLMessageEditor();
        gov.nist.hit.core.domain.Message message = new Message();
        message.setContent(xmlMessage);
        HashMap<String,String> toBeReplaced = new HashMap<>();
        toBeReplaced.put("/Message/Header/MessageID","1234567890");
        toBeReplaced.put("NCPDPID","DLDRPZ");
        toBeReplaced.put("/Message/Body/NewRx/Pharmacy/Identification/NPI","QWERTYUIOP");
        try {
            String editedMessage = xmlMessageEditor.replaceInMessage(message,toBeReplaced,new XMLTestContext());
            XMLMessageParser xmlMessageParser = new XMLMessageParser();
            message.setContent(editedMessage);
            Map<String,String> data = xmlMessageParser.readInMessage(message, new ArrayList(toBeReplaced.keySet()), null);
            for(String key : toBeReplaced.keySet()){
                Assert.assertEquals(toBeReplaced.get(key), data.get(key));
            }
            //Test if all the instances of messageID have been modified
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(IOUtils.toInputStream(message.getContent()));
            NodeList nodeList = XMLUtils.getNodesByNameOrXPath("/Message/Header/MessageID",doc);
            Assert.assertTrue(nodeList.getLength()==2);
            for(int i=0;i<nodeList.getLength();i++){
                Assert.assertEquals(nodeList.item(i).getTextContent(),toBeReplaced.get("/Message/Header/MessageID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Test
        public void testXMLParser() throws IOException {
            String xmlMessage = IOUtils.toString(this.getClass().getResourceAsStream("message.xml"));

            XMLMessageParser xmlMessageParser = new XMLMessageParser();
                gov.nist.hit.core.domain.Message message = new Message();
                message.setContent(xmlMessage);
                HashMap<String,String> toBeFoundWithValue = new HashMap<>();
                toBeFoundWithValue.put("/Message/Header/MessageID","90927");
                toBeFoundWithValue.put("NCPDPID","1629900");
                toBeFoundWithValue.put("/Message/Body/NewRx/Pharmacy/Identification/NPI","3030000003");
                try {
                        Map<String,String> data = xmlMessageParser.readInMessage(message, new ArrayList(toBeFoundWithValue.keySet()), null);
                        for(String key : toBeFoundWithValue.keySet()){
                                Assert.assertEquals(toBeFoundWithValue.get(key), data.get(key));
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
