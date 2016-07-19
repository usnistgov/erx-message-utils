package gov.nist.hit;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;
import gov.nist.healthcare.unified.model.*;
import gov.nist.hit.core.domain.TestContext;
import gov.nist.hit.core.edi.domain.EDITestContext;
import gov.nist.hit.core.xml.domain.XMLTestContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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
 * Created by Maxence Lefort on 6/9/16.
 */
public class ValidationLogUtil {

    public static String generateValidationLog(TestContext testContext,EnhancedReport report) throws NotFoundException, ConversionException {
        if (report != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Detections detections = report.getDetections();
            boolean validationSuccess = true;
            StringBuilder validationResult = new StringBuilder();

            for (Classification classification : detections.classes()) {
                if (classification.getName().equals("Affirmative")) {

                } else if (classification.getName().equals("Warning")) {
                    int warningCount = classification.keys().size();
                    validationResult.append(" - ");
                    validationResult.append(warningCount);
                    validationResult.append(" warning");
                    if (warningCount > 1) {
                        validationResult.append("s");
                    }
                } else if (classification.getName().equals("Error")) {
                    validationSuccess = false;
                    int errorCount = classification.keys().size();
                    if (errorCount > 0) {
                        HashMap<String, Integer> segmentCount = new HashMap<>();
                        validationResult.append(" - ");
                        validationResult.append(errorCount);
                        validationResult.append(" error");
                        if (errorCount > 1) {
                            validationResult.append("s");
                        }
                        for (String key : classification.keys()) {
                            Collection collection = classification.getArray(key);
                            collection.getName();
                            for (int i = 0; i < collection.size(); i++) {
                                Section section = collection.getObject(i);
                                String path = section.getString("path");
                                if (path != null && !"".equals(path)) {
                                    path = path.split("\\[")[0];
                                    int segmentErrorCount = 1;
                                    if (segmentCount.containsKey(path)) {
                                        segmentErrorCount = segmentCount.get(path) + 1;
                                    }
                                    segmentCount.put(path, segmentErrorCount);
                                }
                            }
                        }
                        boolean isFirst = true;
                        if (segmentCount.size() > 0) {
                            validationResult.append(" [");
                            for (String path : segmentCount.keySet()) {
                                if (!isFirst) {
                                    validationResult.append(", ");
                                }
                                validationResult.append(path);
                                validationResult.append(" (");
                                validationResult.append(segmentCount.get(path));
                                validationResult.append(" error");
                                if (segmentCount.get(path) > 1) {
                                    validationResult.append("s");
                                }
                                validationResult.append(")");
                                isFirst = false;
                            }
                            validationResult.append("]");
                        }
                    }
                }
            }
            StringBuilder validationLog = new StringBuilder();
            validationLog.append(simpleDateFormat.format(new Date()));
            validationLog.append(" [Validation] ");
            if(testContext instanceof EDITestContext){
                validationLog.append("EDI - ");
                EDITestContext ediTestContext = (EDITestContext) testContext;
                if (ediTestContext.getType() != null) {
                    validationLog.append(ediTestContext.getType());
                    validationLog.append(" - ");
                }
            } else if(testContext instanceof XMLTestContext){
                validationLog.append("XML - ");
                XMLTestContext xmlTestContext = (XMLTestContext) testContext;
                if (xmlTestContext.getType() != null) {
                    validationLog.append(xmlTestContext.getType());
                    validationLog.append(" - ");
                }
            }

            if (validationSuccess)
                validationLog.append("success");
            else
                validationLog.append("fail");
            validationLog.append(validationResult.toString());
            return validationLog.toString();
        }
        return "";
    }

}
