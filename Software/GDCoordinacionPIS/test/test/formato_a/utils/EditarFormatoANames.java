/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.formato_a.utils;

/**
 * Clase que contiene constantes de identificaciones de campos en el formulario
 * de edición de formato A
 * @author Camilo
 */
public interface EditarFormatoANames {
    public static String idFormulario = "formMetadatosEditFormatoA";
    public static String idRegistrarBtn = idFormulario+":"+"submit-btn";
    //Título
    public static String idTitulo = idFormulario+":"+"titulo";
    public static String idMsgTitulo = idFormulario+":"+"msgTitulo";
    //Fecha
    public static String idFecha = idFormulario+":"+"fecha_input";
    public static String idMsgFecha = idFormulario+":"+"msgFecha";
    //Estudiante 1
    public static String idNombre1 = idFormulario+":"+"nomEstudiante1";
    public static String idMsgNombre1 = idFormulario+":"+"msgNombre1";
    //Estudiante 2
    public static String idNombre2 = idFormulario+":"+"nomEstudiante2";
    public static String idMsgNombre2 = idFormulario+":"+"msgNombre2";
}
