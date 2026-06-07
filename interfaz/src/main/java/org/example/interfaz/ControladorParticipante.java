package org.example.interfaz;

import entidades.Usuario;

public class ControladorParticipante {

    private Usuario usuarioIniciado;

    public void setUsuarioParticipante(Usuario usuario) {
        this.usuarioIniciado = usuario;
        System.out.println("Bienvenido: " + usuario.getNombre());
    }


}
