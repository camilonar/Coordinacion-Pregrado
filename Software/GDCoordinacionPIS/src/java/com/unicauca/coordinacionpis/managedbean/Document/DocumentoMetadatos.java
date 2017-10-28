/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.managedbean.Document;

import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.form.FormElement;
import com.openkm.sdk4j.bean.form.Input;
import java.util.HashMap;

/**
 *
 * @author David
 */
public class DocumentoMetadatos {

    Document document;
    HashMap<String, FormElement> propierties;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public HashMap<String, FormElement> getPropierties() {
 
        return propierties;
    }

    public void setPropierties(HashMap<String, FormElement> propierties) {
        this.propierties = propierties;
    }

    public DocumentoMetadatos(Document document, HashMap<String, FormElement> propierties) {
        this.document = document;
        this.propierties = propierties;
    }
    
    public String getPropiertieValue(String propiertie){
        return ((Input)(propierties.get(propiertie) )).getValue();
    }

}
