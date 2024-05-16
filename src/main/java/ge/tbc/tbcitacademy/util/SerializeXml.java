package ge.tbc.tbcitacademy.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;

public class SerializeXml {
    public static <T> String marshallXml(T requestObjectToXml){

        try {
            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

           JAXBContext jaxbContext = JAXBContext.newInstance(requestObjectToXml.getClass());
           Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
           jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
           jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT,Boolean.TRUE);

           jaxbMarshaller.marshal(requestObjectToXml,document);

           soapMessage.getSOAPBody().addDocument(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            Source sourceContent = soapMessage.getSOAPPart().getContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            transformer.transform(sourceContent, new StreamResult(outputStream));

            return outputStream.toString();

        } catch (JAXBException | SOAPException | ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
