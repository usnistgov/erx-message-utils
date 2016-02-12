package gov.nist.hit;

import gov.nist.hit.core.domain.TestContext;

import java.util.HashMap;

/**
 * Created by mcl1 on 1/19/16.
 */
public interface MessageEditor {
    String replaceInMessage(gov.nist.hit.core.domain.Message message, HashMap<String,String> idNewValueMap, TestContext context) throws Exception;
}
