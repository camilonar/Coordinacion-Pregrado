/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.plan_estudios.utils;

/**
 * Clase que contiene constantes de identificaciones de campos en el formulario
 * de registro de planes de estudio
 * @author Camilo
 */
public interface RegistrarPlanEstudiosNames {
    public static String idFormulario = "formMetadatosPlanEstudio";
    public static String idRegistrarBtn = idFormulario+":"+"submit-btn";
    //Acuerdo
    public static String idAcuerdo = idFormulario+":"+"acPlanEstudio";
    public static String idMsgAcuerdo = idFormulario+":"+"msgAcPlan";
    //Vigencia
    public static String idVigencia = idFormulario+":"+"vigPlanEstudio_input";
    public static String idMsgVigencia = idFormulario+":"+"msgVigPlan";
    
}
