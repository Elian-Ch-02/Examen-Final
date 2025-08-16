/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen.pkgfinal;

import java.util.ArrayList;

/**
 *
 * @author UCC
 */
public class producto {

    static void setSelectionMode(int SINGLE_SELECTION) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    private int codigo;
    private String nombre;
    private int cantidad;
    private double precio;
    private double total;
    private double totalinventario;
    private double subtotal;
    
    public producto(int codigo, String nombre, int cantidad, double precio, double total, double subtotal, double totalinventario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.subtotal = subtotal;
        this.totalinventario = totalinventario;
         
    }
    
    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    public double getTotalInventario() {
        return totalinventario;
    }
    
    public void setTotalInventario(double totalinventario) {
        this.totalinventario = totalinventario;
    }
    
    public double getSubTotal() {
        return subtotal;
    }
    
    public void setsubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
   
    
    public producto(){
    }
    
    
}

