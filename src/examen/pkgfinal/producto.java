/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen.pkgfinal;



/**
 *
 * @author UCC
 */
public class producto {
    private int codigo;
    private String nombre;
    private int cantidad;
    private double precio;
   
    public producto(){
    }
    
    public producto(int codigo, String nombre, int cantidad, double precio){
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
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
   
  
    public double getSubTotal() {
        return cantidad * precio;
    }
    
   
    
   
    @Override
   public String toString() {
        return "Producto{" + "codigo='" + codigo + '\'' + ", nombre='" + nombre + '\'' + ", cantidad=" + cantidad +
                ", precio=" + precio + ", subtotal=" + getSubTotal() +'}';
    }

    

    
    
    
    
    
}

