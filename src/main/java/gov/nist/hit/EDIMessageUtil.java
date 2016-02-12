package gov.nist.hit;

import gov.nist.hit.beans.Separators;
import gov.nist.hit.core.domain.Message;
import gov.nist.hit.core.domain.TestContext;
import gov.nist.hit.core.edi.domain.EDITestContext;
import gov.nist.hit.core.service.edi.JParser;
import hl7.v2.profile.Profile;
import ncpdp.script.profile.XMLDeserializer;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

/**
 * Created by mcl1 on 1/20/16.
 */
public class EDIMessageUtil {
    public static Separators getSeparators(String message){
        Separators separators = new Separators(message.split("\n")[0]);
        return separators;
    }

    public static hl7.v2.instance.Message parseMessage(Message message, TestContext context) throws Exception{
        if (context instanceof EDITestContext) {
            EDITestContext testContext = (EDITestContext) context;
            String ediMessage = message.getContent();
            String profileXml = testContext.getConformanceProfile().getIntegrationProfile().getXml();
            if (profileXml == null) {
                throw new MessageParserException("No Conformance Profile Provided to Parse the Message");
            }
            String conformanceProfileId = testContext.getConformanceProfile().getSourceId();
            if (!"".equals(ediMessage) && ediMessage != null && !"".equals(conformanceProfileId)) {
                InputStream profileStream = IOUtils.toInputStream(profileXml);
                Profile profile = XMLDeserializer.deserialize(profileStream).get();
                JParser p = new JParser();
                hl7.v2.instance.Message parsedMessage = p.jparse(ediMessage, profile.messages().apply(conformanceProfileId));
                return parsedMessage;
            }
        } else {
            throw new MessageParserException("Context must be EDI");
        }
        throw new MessageParserException("An error occured while parsing the message");
    }
}
