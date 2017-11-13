/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.sessionbean;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;
import com.unicauca.coordinacionpis.classMetadatos.MetadatosAntepoyecto;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Camilo
 */
@Named(value="visualizarDocumentoBean")
@Stateless
public class VisualizarDocumentoBean {
    
 
    private StreamedContent streamedContent;
    private List<com.openkm.sdk4j.bean.Document> listadoDocsAnteproecto;
    private com.openkm.sdk4j.bean.Document documento;
    String url = "http://localhost:8083/OpenKM";
    String user = "okmAdmin";
    String pass = "admin";
    OKMWebservices okm = OKMWebservicesFactory.newInstance(url, user, pass);
   
    public void visualizarDocumento(com.openkm.sdk4j.bean.Document documento) {

        try {
            this.documento = documento;
            InputStream in = okm.getContent(documento.getPath());
            streamedContent = new DefaultStreamedContent(in, "application/pdf");
            //-------
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":visualizacion");
            requestContext.execute("PF('visualizarPDF').show()");
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    
    
           public void visualizarDocumentoUID(String UUID ) {

        try {
            String path = okm.getNodePath(UUID);
            InputStream in = okm.getContent(path);
            streamedContent = new DefaultStreamedContent(in, "application/pdf");
            //-------
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":visualizacion");
            requestContext.execute("PF('visualizarPDF').show()");
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }
}
