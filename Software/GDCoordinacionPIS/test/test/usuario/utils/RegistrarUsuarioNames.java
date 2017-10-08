/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.usuario.utils;

/**
 * Clase que contiene constantes de identificaciones de campos en el formulario
 * de registro de usuarios
 * @author Camilo
 */
public interface RegistrarUsuarioNames {
    public static String idFormulario = "UsuarioCreateForm";
    public static String idRegistrarBtn = idFormulario+":"+"submit-btn";
    //Nombres
    public static String idNombre = idFormulario+":"+"usunombres";
    public static String idMsgNombre = idFormulario+":"+"msgCaracteresNombre";
    //Apellidos
    public static String idApellido = idFormulario+":"+"usuapellidos";
    public static String idMsgApellido = idFormulario+":"+"msgCaracteresApellido";
    //Fecha de nacimiento
    public static String idFechaNacimiento = idFormulario+":"+"usufechanacimiento_input";
    public static String idMsgFechaNacimiento = idFormulario+":"+"msgUsuFechaNacimiento";
    //Email
    public static String idEmail = idFormulario+":"+"usuemail";
    public static String idMsgEmail = idFormulario+":"+"msgCaracteresEMail";
    //Nombre de usuario
    public static String idNombreUsuario = idFormulario+":"+"usunombreusuario";
    public static String idMsgNombreUsuario = idFormulario+":"+"msgCaracteresNombreUsuario";
    //Contraseña
    public static String idContrasena = idFormulario+":"+"usucontrasena";
    public static String idMsgContrasena = idFormulario+":"+"msgCaracteresContrasena";
}
