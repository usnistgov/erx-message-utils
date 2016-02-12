package gov.nist.hit.impl;

import gov.nist.hit.EDIMessageUtil;
import gov.nist.hit.MessageEditor;
import gov.nist.hit.core.domain.Message;
import gov.nist.hit.core.domain.TestContext;
import scala.Option;
import scala.Predef;
import scala.Tuple2;
import scala.collection.JavaConverters;
import scala.collection.immutable.Map;

import java.util.HashMap;

/**
 * Created by mcl1 on 1/19/16.
 */
public class EdiMessageEditor implements MessageEditor {

    @Override
    public String replaceInMessage(Message message, HashMap<String, String> idNewValueMap, TestContext context) throws Exception {
        hl7.v2.instance.Message parsedMessage = EDIMessageUtil.parseMessage(message,context);
        Option<Map<String,String>> option = Option.apply(toScalaMap(idNewValueMap));
        return parsedMessage.printString(option);
    }

    public static <A, B> Map<A, B> toScalaMap(HashMap<A, B> m) {
        return JavaConverters.mapAsScalaMapConverter(m).asScala().toMap(
                Predef.<Tuple2<A, B>>conforms()
        );
    }
}
