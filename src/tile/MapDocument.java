package src.tile;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class MapDocument {

    /**
     * Carrega um documento xml
     * @param path caminho do arquivo
     * @return Objeto com o documento carregado
     */
    public static Document loadDocument (String path) {
        //Pega caminho que o programa está rodando
        String CurrentDirectoryPath = System.getProperty("user.dir");
        File CurrentDirectory = new File(CurrentDirectoryPath);
        String AbsolutePath = CurrentDirectory.getAbsolutePath();
        Document doc;
        try {
            File inputFile = new File(AbsolutePath + path);
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

    /**
     * A partir de um documento xml, devolve uma lista contendo todos os elementos com a mesma tag
     * @param doc Documento xml
     * @param tag tag
     * @return Lista com os elementos
     */
    public static NodeList getListByTag(Document doc, String tag) {
        return doc.getElementsByTagName(tag);
    }

}
