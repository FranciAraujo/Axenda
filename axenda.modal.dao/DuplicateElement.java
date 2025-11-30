/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package axenda.modal.dao; 

/**
 *
 * @author Francinelle
 */

    // Excepción: se lanza cuando intentamos guardar un contacto duplicado
public class DuplicateElement extends Exception {
    public DuplicateElement(String mensaje) {
        super(mensaje);
    }
}
