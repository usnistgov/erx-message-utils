package gov.nist.hit;

import gov.nist.hit.beans.Separators;

/**
 * Created by mcl1 on 1/20/16.
 */
public class MessageIdFinder {
    public static String findEdiMessageId(String message){
        Separators separators = EDIMessageUtil.getSeparators(message);
        message = message.replace("\n","");
        String[] segments = message.split(separators.getLineSeparator());
        for(int i=0;i<segments.length;i++){
            String segment = segments[i];
            if(segment.startsWith("UIH")){
                return segment.split("\\"+separators.getFieldSeparator())[2];
            }
        }
        return null;
    }
}
