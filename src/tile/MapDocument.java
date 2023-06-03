package tile;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class MapDocument {
    String path;
    Document doc;

    public MapDocument (String path) {
        this.path = path;
        this.doc = loadDocument();
    }

    private Document loadDocument () {
        //Pega caminho que o programa está rodando
        String CurrentDirectoryPath = System.getProperty("user.dir");
        File CurrentDirectory = new File(CurrentDirectoryPath);
        String AbsolutePath = CurrentDirectory.getAbsolutePath();
        Document doc;
        try {
            File inputFile = new File(AbsolutePath + this.path);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder DocumentBuilder = builderFactory.newDocumentBuilder();
            doc = DocumentBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
    }

    public NodeList getListByTag(String tag) {
        return doc.getElementsByTagName(tag);
    }

}
