/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.materias.utils;

/**
 * Clase que contiene constantes de identificaciones de campos en el formulario
 * de edición de materias
 * @author Camilo
 */
public interface EditarMateriaNames {
    public static String idFormulario = "MateriaEditForm";
    public static String idRegistrarBtn = idFormulario+":"+"submit-btn";
    //Código
    public static String idCodigo = idFormulario+":"+"codigoMateria";
    public static String idMsgCodigo = idFormulario+":"+"msgCodigo";
    //Nombre
    public static String idNombre = idFormulario+":"+"nombreMateria";
    public static String idMsgNombre = idFormulario+":"+"msgNombre";
    //Créditos
    public static String idCreditos = idFormulario+":"+"creditos";
    public static String idMsgCreditos = idFormulario+":"+"msgCreditos";
    //Intensidad horaria
    public static String idIntensidad = idFormulario+":"+"intensidadHoraria";
    public static String idMsgIntensidad = idFormulario+":"+"msgIhoraria";
}
