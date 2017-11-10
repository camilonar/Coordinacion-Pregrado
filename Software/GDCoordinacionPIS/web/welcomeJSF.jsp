<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%--
    This file is an entry point for JavaServer Faces application.
--%>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>JSP Page</title>
<link rel="stylesheet" type="text/css" href="/GDCoordinacionPIS/GDCP/jsfcrud.css" />
        </head>
        <body>
            <h1><h:outputText value="JavaServer Faces"/></h1>
                <h:form>
                    <h:commandLink action="#{formatob.listSetup}" value="Show All Formatob Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{formatoa.listSetup}" value="Show All Formatoa Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{estudiante.listSetup}" value="Show All Estudiante Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{anteproyecto.listSetup}" value="Show All Anteproyecto Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{programa.listSetup}" value="Show All Programa Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{profesor.listSetup}" value="Show All Profesor Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{formatoc.listSetup}" value="Show All Formatoc Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{formatob.listSetup}" value="Show All Formatob Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{formatoa.listSetup}" value="Show All Formatoa Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{facultad.listSetup}" value="Show All Facultad Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{estudiante.listSetup}" value="Show All Estudiante Items"/>
                </h:form>

                <h:form>
                    <h:commandLink action="#{anteproyecto.listSetup}" value="Show All Anteproyecto Items"/>
                </h:form>

        </body>
    </html>
</f:view>
