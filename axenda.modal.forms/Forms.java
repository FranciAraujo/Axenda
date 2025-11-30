/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package axenda.modal.forms;

import axenda.modal.Persona;
import axenda.modal.Contacto;
import com.iesrodeira.utils.Input;
import com.iesrodeira.utils.CancelException;

public class Forms {

    // 1. Crear una Persona nueva
    public static Persona personForm() throws CancelException {
        System.out.println("\n--- FORMULARIO DE PERSONA ---");
        
        // NIF (se valida en el constructor de Persona)
        String nif;
        boolean nifOk = false;
        do {
            try {
                nif = Input.readText("Introduce el NIF");
                if (nif.isEmpty()) throw new CancelException(); // NIF es obligatorio
                Input.testNif(nif); // Validar formato NIF
                nifOk = true;
            } catch (IllegalArgumentException e) {
                System.out.println("\t[ERROR]: " + e.getMessage());
                nif = null; // Para seguir en el bucle
            }
        } while (!nifOk);
        
        // Apellidos
        String apellidos = Input.readText("Introduce los apellidos");
        if (apellidos.isEmpty()) throw new CancelException();

        // Nombre
        String nombre = Input.readText("Introduce el nombre");
        if (nombre.isEmpty()) throw new CancelException();

        // Crea y devuelve el objeto Persona
        return new Persona(nif, apellidos, nombre);
    }

    // 2. Modificar un Contacto existente
    public static Contacto contactForm(Contacto c) throws CancelException {
        System.out.println("\n--- MODIFICAR CONTACTO ---");
        System.out.println("Persona: " + c.getPersona());
        System.out.println("Datos actuales: " + c);

        // Los métodos de Input con dos argumentos permiten dejar el campo vacío para usar el valor actual
        String telefono = Input.readPhone("Nuevo teléfono", c.getTelefono());
        String correo = Input.readEmail("Nuevo correo", c.getCorreo());
        String descripcion = Input.readText("Nueva descripción", c.getDescripcion());
        
        // Actualiza el objeto Contacto (los setters validan)
        try {
            c.setTelefono(telefono);
            c.setCorreo(correo);
            c.setDescripcion(descripcion);
        } catch (IllegalArgumentException e) {
            // Este caso es poco probable si se usa Input.readPhone/readEmail, pero es una buena práctica
            System.out.println("\t[ERROR]: Error de validación al asignar: " + e.getMessage());
            throw new CancelException("Error de validación: Contacto no modificado.");
        }

        return c;
    }

    // 3. Crear un Contacto a partir de una Persona ya existente
    public static Contacto contactForm(Persona p) throws CancelException {
        System.out.println("\n--- NUEVO CONTACTO PARA: " + p.toString() + " ---");
        
        // Leer datos específicos del contacto
        String telefono = Input.readPhone("Introduce el teléfono");
        String correo = Input.readEmail("Introduce el correo");
        String descripcion = Input.readText("Introduce la descripción");

        // Crea y devuelve el objeto Contacto
        return new Contacto(p, telefono, correo, descripcion);
    }

    // 4. Crear un Contacto completo (Persona + Contacto)
    public static Contacto contactForm() throws CancelException {
        Persona p = personForm(); // Primero pide los datos de la Persona
        return contactForm(p); // Luego pide los datos del Contacto
    }
}
