package org.example.interfaz;

import entidades.Usuario;

public class ControladorJuez {

    private Usuario usuarioIniciado;

    public void setUsuarioJuez(Usuario usuario) {
        this.usuarioIniciado = usuario;
        System.out.println("El juez " + usuario.getNombre() + " ha iniciado sesión.");
    }
}
