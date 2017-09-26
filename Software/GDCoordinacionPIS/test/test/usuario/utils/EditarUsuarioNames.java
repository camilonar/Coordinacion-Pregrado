/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.usuario.utils;

/**
 * Clase que contiene constantes de identificaciones de campos en el formulario
 * de edición de usuarios
 * @author Camilo
 */
public interface EditarUsuarioNames {
    public static String idFormulario = "UsuarioEditForm";
    public static String idActualizarBtn = idFormulario+":"+"submit-btn";
    public static String idActualizarContrasenaBtn = idFormulario+":"+"contrasena-btn";
    public static String idAceptarContrasenaBtn = idFormulario+":"+"aceptar-contrasena-btn";
    //Nombres
    public static String idNombre = idFormulario+":"+"usunombres";
    public static String idMsgNombre = idFormulario+":"+"msgCaracteresNombre";
    //Apellidos
    public static String idApellido = idFormulario+":"+"usuapellidos";
    public static String idMsgApellido = idFormulario+":"+"msgCaracteresApellido";
    //Fecha de nacimiento
    public static String idFechaNacimiento = idFormulario+":"+"usufechanacimiento_input";
    public static String idMsgFechaNacimiento = idFormulario+":"+"msgUsuFechaNacimiento";
    //Contraseña
    public static String idContrasena = idFormulario+":"+"usucontrasena";
    public static String idMsgContrasena = idFormulario+":"+"msgCaracteresContrasena";
}
