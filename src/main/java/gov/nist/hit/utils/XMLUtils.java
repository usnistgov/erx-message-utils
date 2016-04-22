package gov.nist.hit.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

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
 * Created by Maxence Lefort on 3/3/16.
 */
public class XMLUtils {
    public static NodeList getNodesByNameOrXPath(String nameOrXPath,Document doc) {
        if (!nameOrXPath.contains("/")) {
            NodeList nodeList = doc.getElementsByTagName(nameOrXPath);
            if (nodeList.getLength() > 0) {
                return nodeList;
            }
        } else {
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodes = null;
            try {
                nodes = (NodeList) xPath.evaluate(nameOrXPath, doc.getDocumentElement(), XPathConstants.NODESET);
                if (nodes != null) {
                    return nodes;
                }
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
