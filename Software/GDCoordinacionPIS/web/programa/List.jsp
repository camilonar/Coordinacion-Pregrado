<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing Programa Items</title>
            <link rel="stylesheet" type="text/css" href="/GDCoordinacionPIS/GDCP/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Programa Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Programa Items Found)<br />" rendered="#{programa.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{programa.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{programa.pagingInfo.firstItem + 1}..#{programa.pagingInfo.lastItem} of #{programa.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{programa.prev}" value="Previous #{programa.pagingInfo.batchSize}" rendered="#{programa.pagingInfo.firstItem >= programa.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{programa.next}" value="Next #{programa.pagingInfo.batchSize}" rendered="#{programa.pagingInfo.lastItem + programa.pagingInfo.batchSize <= programa.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{programa.next}" value="Remaining #{programa.pagingInfo.itemCount - programa.pagingInfo.lastItem}"
                                   rendered="#{programa.pagingInfo.lastItem < programa.pagingInfo.itemCount && programa.pagingInfo.lastItem + programa.pagingInfo.batchSize > programa.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{programa.programaItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="IdPrograma"/>
                            </f:facet>
                            <h:outputText value="#{item.idPrograma}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="NombrePrograma"/>
                            </f:facet>
                            <h:outputText value="#{item.nombrePrograma}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Idfacultad"/>
                            </f:facet>
                            <h:outputText value="#{item.idfacultad}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText escape="false" value="&nbsp;"/>
                            </f:facet>
                            <h:commandLink value="Show" action="#{programa.detailSetup}">
                                <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][programa.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{programa.editSetup}">
                                <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][programa.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{programa.remove}">
                                <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][programa.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>

                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{programa.createSetup}" value="New Programa"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />


            </h:form>
        </body>
    </html>
</f:view>
