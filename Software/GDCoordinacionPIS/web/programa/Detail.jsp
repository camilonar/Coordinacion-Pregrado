<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Programa Detail</title>
            <link rel="stylesheet" type="text/css" href="/GDCoordinacionPIS/GDCP/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Programa Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="IdPrograma:"/>
                    <h:outputText value="#{programa.programa.idPrograma}" title="IdPrograma" />
                    <h:outputText value="NombrePrograma:"/>
                    <h:outputText value="#{programa.programa.nombrePrograma}" title="NombrePrograma" />
                    <h:outputText value="Idfacultad:"/>
                    <h:panelGroup>
                        <h:outputText value="#{programa.programa.idfacultad}"/>
                        <h:panelGroup rendered="#{programa.programa.idfacultad != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{facultad.detailSetup}">
                                <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentFacultad" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa.idfacultad][facultad.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="programa"/>
                                <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{facultad.editSetup}">
                                <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentFacultad" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa.idfacultad][facultad.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="programa"/>
                                <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{facultad.destroy}">
                                <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentFacultad" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa.idfacultad][facultad.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="programa"/>
                                <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>

                    <h:outputText value="UsuarioList:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty programa.programa.usuarioList}" value="(No Items)"/>
                        <h:dataTable value="#{programa.programa.usuarioList}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty programa.programa.usuarioList}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Usufoto"/>
                                </f:facet>
                                <h:outputText value="#{item.usufoto}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Usuestado"/>
                                </f:facet>
                                <h:outputText value="#{item.usuestado}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Usuid"/>
                                </f:facet>
                                <h:outputText value="#{item.usuid}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Usufechanacimiento"/>
                                </f:facet>
                                <h:outputText value="#{item.usufechanacimiento}">
                                    <f:convertDateTime pattern="MM/dd/yyyy HH:mm:ss" />
                                </h:outputText>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Usunombres"/>
                                </f:facet>
                                <h:outputText value="#{item.usunombres}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Usuapellidos"/>
                                </f:facet>
                                <h:outputText value="#{item.usuapellidos}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Usugenero"/>
                                </f:facet>
                                <h:outputText value="#{item.usugenero}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Usunombreusuario"/>
                                </f:facet>
                                <h:outputText value="#{item.usunombreusuario}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Usucontrasena"/>
                                </f:facet>
                                <h:outputText value="#{item.usucontrasena}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Usuemail"/>
                                </f:facet>
                                <h:outputText value="#{item.usuemail}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Carid"/>
                                </f:facet>
                                <h:outputText value="#{item.carid}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{usuario.detailSetup}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentUsuario" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][usuario.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{usuario.editSetup}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentUsuario" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][usuario.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{usuario.destroy}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentUsuario" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][usuario.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                    <h:outputText value="EstudianteList:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty programa.programa.estudianteList}" value="(No Items)"/>
                        <h:dataTable value="#{programa.programa.estudianteList}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty programa.programa.estudianteList}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="IdEstudiante"/>
                                </f:facet>
                                <h:outputText value="#{item.idEstudiante}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="CodigoEstudiante"/>
                                </f:facet>
                                <h:outputText value="#{item.codigoEstudiante}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="NombreEstudiante"/>
                                </f:facet>
                                <h:outputText value="#{item.nombreEstudiante}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="ProgramaEstudiante"/>
                                </f:facet>
                                <h:outputText value="#{item.programaEstudiante}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{estudiante.detailSetup}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentEstudiante" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][estudiante.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{estudiante.editSetup}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentEstudiante" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][estudiante.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{estudiante.destroy}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentEstudiante" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][estudiante.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                    <h:outputText value="AnteproyectoList:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty programa.programa.anteproyectoList}" value="(No Items)"/>
                        <h:dataTable value="#{programa.programa.anteproyectoList}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty programa.programa.anteproyectoList}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="IdAnteproyecto"/>
                                </f:facet>
                                <h:outputText value="#{item.idAnteproyecto}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="TituloAnteproyecto"/>
                                </f:facet>
                                <h:outputText value="#{item.tituloAnteproyecto}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="FechaAnteproyecto"/>
                                </f:facet>
                                <h:outputText value="#{item.fechaAnteproyecto}">
                                    <f:convertDateTime pattern="MM/dd/yyyy" />
                                </h:outputText>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="DirectorAnteproyecto"/>
                                </f:facet>
                                <h:outputText value="#{item.directorAnteproyecto}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="ProgramaAnteproyecto"/>
                                </f:facet>
                                <h:outputText value="#{item.programaAnteproyecto}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{anteproyecto.detailSetup}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentAnteproyecto" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][anteproyecto.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{anteproyecto.editSetup}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentAnteproyecto" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][anteproyecto.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{anteproyecto.destroy}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentAnteproyecto" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][anteproyecto.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                    <h:outputText value="ProfesorList:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty programa.programa.profesorList}" value="(No Items)"/>
                        <h:dataTable value="#{programa.programa.profesorList}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty programa.programa.profesorList}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="IdProfesor"/>
                                </f:facet>
                                <h:outputText value="#{item.idProfesor}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="NombreProfesor"/>
                                </f:facet>
                                <h:outputText value="#{item.nombreProfesor}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="UniversidadProfesor"/>
                                </f:facet>
                                <h:outputText value="#{item.universidadProfesor}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="ExternoProfesor"/>
                                </f:facet>
                                <h:outputText value="#{item.externoProfesor}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="ProgramaProfesor"/>
                                </f:facet>
                                <h:outputText value="#{item.programaProfesor}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{profesor.detailSetup}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentProfesor" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][profesor.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{profesor.editSetup}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentProfesor" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][profesor.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{profesor.destroy}">
                                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentProfesor" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][profesor.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="programa" />
                                    <f:param name="jsfcrud.relatedControllerType" value="com.unicauca.coordinacionpis.managedbean.Document.ProgramaController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>

                </h:panelGrid>
                <br />
                <h:commandLink action="#{programa.remove}" value="Destroy">
                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{programa.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentPrograma" value="#{jsfcrud_class['com.unicauca.coordinacionpis.managedbean.Document.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][programa.programa][programa.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{programa.createSetup}" value="New Programa" />
                <br />
                <h:commandLink action="#{programa.listSetup}" value="Show All Programa Items"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
