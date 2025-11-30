/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package axenda.modal.dao;
import axenda.modal.Contacto;

/**
 *
 * @author Francinelle
 */
public class ContactosDAO {
    // Array privado: solo accesible dentro de esta clase
    private Contacto[] agenda;
    private int contador;

    // Constructor: recibe la capacidad máxima
    public ContactosDAO(int capacidad) {
        agenda = new Contacto[capacidad];
        contador = 0;
    }

    // Constructor vacío con capacidad por defecto (ej. 100)
    public ContactosDAO() {
        this(100);
    }

    // Añadir un contacto
    public void addContacto(Contacto c) throws DuplicateElement, StorageFull {
        // Duplicado: usa equals de Contacto
        for (int i = 0; i < contador; i++) {
            if (agenda[i].equals(c)) {
                throw new DuplicateElement(agenda[i].toString());
            }
        }
        // Sin espacio
        if (contador >= agenda.length) {
            throw new StorageFull();
        }
        // Guardar y asignar número = posición
        agenda[contador] = c;
        c.setNumero(contador);
        contador++;
    }

    // Método que implementa la búsqueda por: NIF, Correo, Teléfono o Descripción
    public Contacto[] buscar(char tipo, String criterio) {
        Contacto[] encontrados = new Contacto[contador];
        int j = 0;
        
        // Normalizar el criterio de búsqueda (mayúsculas)
        String crit = criterio.toUpperCase();

        for (int i = 0; i < contador; i++) {
            Contacto c = agenda[i];
            boolean coincide = false;
            
            switch (Character.toUpperCase(tipo)) {
                case 'N': // Buscar por NIF
                    coincide = c.getPersona().getNif().toUpperCase().equals(crit);
                    break;
                case 'T': // Buscar por Teléfono
                    coincide = c.getTelefono().toUpperCase().equals(crit);
                    break;
                case 'E': // Buscar por Email
                    coincide = c.getCorreo().toUpperCase().equals(crit);
                    break;
                case 'D': // Buscar por Descripción (contiene la palabra)
                    coincide = c.getDescripcion().toUpperCase().contains(crit);
                    break;
            }
            
            if (coincide) {
                encontrados[j++] = c;
            }
        }
        
        // Recorta el array al tamaño exacto de los resultados
        return recortaArray(encontrados);
    }

    // Recuperar por número de contacto (o null si no existe)
    public Contacto buscarPorNumero(int numero) {
        if (numero >= 0 && numero < contador) {
            return agenda[numero];
        }
        return null;
    }

    // Eliminar por número (devuelve eliminado o null si falla)
    public Contacto eliminarPorNumero(int numero) {
        if (numero >= 0 && numero < contador) {
            Contacto eliminado = agenda[numero];
            // Compactar array y reenumerar
            for (int i = numero; i < contador - 1; i++) {
                agenda[i] = agenda[i + 1];
                agenda[i].setNumero(i);
            }
            agenda[contador - 1] = null;
            contador--;
            return eliminado;
        }
        return null;
    }

    // Devolver todos los contactos
    public Contacto[] listarTodos() {
        Contacto[] resultado = new Contacto[contador];
        for (int i = 0; i < contador; i++) {
            resultado[i] = agenda[i];
        }
        return resultado;
    }

    // Eliminar todos
    public void eliminarTodos() {
        for (int i = 0; i < contador; i++) {
            agenda[i] = null;
        }
        contador = 0;
    }

    // Utilidad: recorta un array al tamaño encontrado
    private Contacto[] recortaArray(Contacto[] array) {
        int encontrados = 0;
        for (Contacto c : array) {
            if (c != null) {
                encontrados++;
            }
        }
        
        Contacto[] resultado = new Contacto[encontrados];
        int j = 0;
        for (Contacto c : array) {
            if (c != null) {
                resultado[j++] = c;
            }
        }
        return resultado;
    }
    
    // Método auxiliar para modificación (necesario en Axenda.java)
    public void update(Contacto c) {
        // La actualización se hace directamente sobre la referencia,
        // solo aseguramos que la referencia en el DAO sea la correcta.
        if (c.getNumero() >= 0 && c.getNumero() < contador) {
            agenda[c.getNumero()] = c;
        }
    }
    
    // Alias para buscarPorNumero para que el Axenda.java final funcione
    public Contacto get(int num) {
        return buscarPorNumero(num);
    }
    
    // Alias para eliminarPorNumero para que el Axenda.java final funcione
    public Contacto delete(int num) {
        return eliminarPorNumero(num);
    }
    
    // Alias para buscar para que el Axenda.java final funcione
    public Contacto[] search(char tipo, String criterio) {
        return buscar(tipo, criterio);
    }
}
