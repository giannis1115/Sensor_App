package com.apps.mj.sensor_app;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by john on 14/12/2016.
 */

public class Message {

    public static boolean ligMessage ;
    public static boolean accMessage ;

    private static String xml = "<INFO></INFO>";

    public static byte[] getBytes(){

        return xml.getBytes();
    }

    public static void setAttribute(String attribute, String value){
       try {
           Document doc = StringToDocument(xml);
           updateNodeValue(doc,attribute,value);
           xml = DocumentToString(doc);
       }
       catch (Exception e) {
           e.printStackTrace();
       }

    }

    public static String getAttribute(String attribute) {
        Document parse=null;

        try {

            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            parse = newDocumentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        }
        catch (ParserConfigurationException e){
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
           return parse.getElementsByTagName(attribute).item(0).getTextContent();

        }
        catch (NullPointerException e){
            return null;
        }

    }



    public static void updateNodeValue(Document doc, String attribute, String value) {

        Node rootNode = doc.getFirstChild();
        NodeList list = rootNode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {

            Element element = (Element) list.item(i);
            Node node = list.item(i);
            if (attribute.equals(node.getNodeName())) {
                element.setTextContent(value);
                return;
            }
        }
        Element element = doc.createElement(attribute);
        element.setTextContent(value);
        rootNode.appendChild(element);
    }

    public static String DocumentToString(Document doc) throws Exception {

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String output = writer.getBuffer().toString();
        return output;
    }

    public static Document StringToDocument(String strXml) throws Exception {

        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringReader strReader = new StringReader(strXml);
            InputSource is = new InputSource(strReader);
            doc = (Document) builder.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return doc;
    }


}
