<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>New Programa</title>
            <link rel="stylesheet" type="text/css" href="/GDCoordinacionPIS/GDCP/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>New Programa</h1>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{programa.validateCreate}" value="value"/>
                <h:panelGrid columns="2">
                    <h:outputText value="NombrePrograma:"/>
                    <h:inputText id="nombrePrograma" value="#{programa.programa.nombrePrograma}" title="NombrePrograma" />
                    <h:outputText value="UsuarioList:"/>
                    <h:selectManyListbox id="usuarioList" value="#{programa.programa.jsfcrud_transform[jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method.arrayToList].usuarioList}" title="UsuarioList" size="6" converter="#{usuario.converter}" >
                        <f:selectItems value="#{usuario.usuarioItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="EstudianteList:"/>
                    <h:selectManyListbox id="estudianteList" value="#{programa.programa.jsfcrud_transform[jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method.arrayToList].estudianteList}" title="EstudianteList" size="6" converter="#{estudiante.converter}" >
                        <f:selectItems value="#{estudiante.estudianteItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="AnteproyectoList:"/>
                    <h:selectManyListbox id="anteproyectoList" value="#{programa.programa.jsfcrud_transform[jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method.arrayToList].anteproyectoList}" title="AnteproyectoList" size="6" converter="#{anteproyecto.converter}" >
                        <f:selectItems value="#{anteproyecto.anteproyectoItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="Idfacultad:"/>
                    <h:selectOneMenu id="idfacultad" value="#{programa.programa.idfacultad}" title="Idfacultad" required="true" requiredMessage="The idfacultad field is required." >
                        <f:selectItems value="#{facultad.facultadItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                    <h:outputText value="ProfesorList:"/>
                    <h:selectManyListbox id="profesorList" value="#{programa.programa.jsfcrud_transform[jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method.arrayToList].profesorList}" title="ProfesorList" size="6" converter="#{profesor.converter}" >
                        <f:selectItems value="#{profesor.profesorItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{programa.create}" value="Create"/>
                <br />
                <br />
                <h:commandLink action="#{programa.listSetup}" value="Show All Programa Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
