/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iesrodeira.utils; //indica q esta clase esta dentro de este package

/**
 *
 * @author Francinelle
 */
public class CancelException extends Exception {

    // Constructor sin mensaje
    public CancelException() {
        super();
    }

    // Constructor con mensaje personalizado
    public CancelException(String mensaje) {
        super(mensaje);
    }
}
