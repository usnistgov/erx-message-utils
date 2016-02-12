package gov.nist.hit.impl;

import gov.nist.hit.EDIMessageUtil;
import gov.nist.hit.MessageParser;
import gov.nist.hit.core.domain.Message;
import gov.nist.hit.core.domain.TestContext;
import scala.collection.JavaConverters;

import java.util.ArrayList;
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
 * Created by Maxence Lefort on 2/12/16.
 */
public class EdiMessageParser implements MessageParser {


    @Override
    public Map<String,String> readInMessage(Message message, ArrayList<String> dataToBeFound, TestContext context) throws Exception {
        hl7.v2.instance.Message parsedMessage = EDIMessageUtil.parseMessage(message, context);
        return JavaConverters.mapAsJavaMapConverter(parsedMessage.getDataFromMessage(scala.collection.JavaConversions.asScalaIterable(dataToBeFound).toList())).asJava();
    }
}
