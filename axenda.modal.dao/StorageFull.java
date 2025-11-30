/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package axenda.modal.dao;

/**
 *
 * @author Francinelle
 */
// Excepción: se lanza cuando el array de contactos está lleno
public class StorageFull extends Exception {
    public StorageFull() {
        super("No hay espacio para más contactos");
    }
}
