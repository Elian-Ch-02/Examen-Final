/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package examen.pkgfinal;

import java.awt.HeadlessException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;



/**
 *
 * @author Elian
 */
public class Inventario extends javax.swing.JFrame {

    /**
     * Creates new form Inventario
     */ 
    
private DefaultTableModel modeloTabla;// Modelo de datos para la tabla que muestra los productos
    private DecimalFormat formatoMoneda;// Formato para mostrar precios con símbolo de dólar y dos decimales
   

    private final ArrayList<producto> listainventario; // Lista que almacena todos los productos del inventario

    public Inventario() {
        initComponents();
       listainventario = new ArrayList<>(); // Inicializa la lista de productos
        formatoMoneda = new DecimalFormat("$#,##0.00"); // Configura el formato de moneda  
        configurarTabla();// Configura el modelo de la tabla
         
    }
    
    
    
   public void agregarProducto(producto producto) {
       // Método para agregar un producto a la lista de inventario
        listainventario.add(producto);
    }

    public ArrayList<producto> getProductos() {
        // Devuelve la lista completa de productos para su uso externo
        return listainventario;
    }

   public double calcularValorTotal() {
       // Calcula el valor total del inventario 
        double total = 0;
        for (producto producto : listainventario) {
            total += producto.getSubTotal();
        }
        return total;
    }

    public void limpiarInventario() {
        // Limpia todos los productos de la lista de inventari
        listainventario.clear();
    }

    public String listarProductos() {
   // Genera una cadena con la información de todos los productos usando toString()
        StringBuilder lista = new StringBuilder();
        for (producto producto : listainventario) {
            lista.append(producto.toString()).append("\n");
        }
        return lista.toString();
    }
       
       private void configurarTabla() {
   // Configura el modelo de la tabla con columnas fijas y hace las celdas no editables
        String[] columnas = {"Código", "Nombre", "Cantidad", "Precio", "SubTotal"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        tblInventario.setModel(modeloTabla);
    }
       
     public void agregarProductoAlInventario() {
   // Método para agregar un nuevo producto al inventario desde la interfaz gráfica
        try {
          // Valida que todos los campos estén llenos
            if (txtCodigo.getText().trim().isEmpty() || 
                txtNombre.getText().trim().isEmpty() ||
                txtCantidad.getText().trim().isEmpty() || 
                txtPrecio.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios.\nPor favor complete toda la información del producto.",
                    "Error de Validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
         // Convierte los valores de los campos a los tipos adecuados
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String nombre = txtNombre.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            
            // Valida que los valores sean positivos
            if (cantidad < 0 || precio < 0) {
                JOptionPane.showMessageDialog(this, 
                    "La cantidad y el precio deben ser valores positivos.",
                    "Error de Validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
                // Crear y agregar producto
            producto nuevoProducto = new producto(codigo, nombre, cantidad, precio);
            agregarProducto(nuevoProducto); 

            // Actualizar tabla y limpiar campos
           actualizarTablaInventario();
            limpiarCamposDeTexto();

            // Muestra mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "Producto agregado exitosamente al inventario.",
                "Producto Agregado", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Error en el formato de los números:\n" +
                "- La cantidad debe ser un número entero\n" +
                "- El precio debe ser un número decimal",
                "Error de Formato", 
                JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

   private void actualizarTablaInventario() {
  // Actualiza la tabla con los productos actuales del inventario
        modeloTabla.setRowCount(0); // Limpiar tabla
        double valorTotalGeneral = 0.0;

        for (producto producto : getProductos()) {
            double subtotal = producto.getSubTotal();
            valorTotalGeneral += subtotal;

            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCantidad(),
                    formatoMoneda.format(producto.getPrecio()),
                    formatoMoneda.format(subtotal)
            };  // Solo 5 elementos, sin total por fila
            modeloTabla.addRow(fila);
        }
        // Actualizar etiqueta de total
        labTotalInventario.setText(formatoMoneda.format(valorTotalGeneral)); // Update the label
    }
    
   
    

private void limpiarTodosLosDatos() {
    // Limpia todos los datos del inventario tras confirmación del usuario
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea limpiar todos los datos?\n" +
            "Esta acción eliminará todos los productos del inventario.",
            "Confirmar Limpieza",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            limpiarCamposDeTexto();
            limpiarInventario();
            actualizarTablaInventario();
           

            JOptionPane.showMessageDialog(this,
                "Todos los datos han sido limpiados exitosamente.",
                "Datos Limpiados",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }    

private void limpiarCamposDeTexto() {
    // Limpia los campos de entrada y enfoca el campo de código
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        txtCodigo.requestFocus();
    }
  private void salirDeLaAplicacion() {
      // Muestra estadísticas y solicita confirmación para salir
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea salir de la aplicación?\n" +
            "Se perderán todos los datos no guardados.",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0); // Cierra la aplicación
        }
        StringBuilder estadisticas = new StringBuilder();
        estadisticas.append("=== ESTADÍSTICAS DEL INVENTARIO ===\n\n");
        estadisticas.append("Total de productos: ").append(getCantidadProductos()).append("\n");
        estadisticas.append("Valor total: ").append(formatoMoneda.format(calcularValorTotal())).append("\n");

        JOptionPane.showMessageDialog(this,
            estadisticas.toString(),
            "Estadísticas del Inventario",
            JOptionPane.INFORMATION_MESSAGE);
    }
     
   private int getCantidadProductos() {
       // Devuelve el número total de productos en el inventario
        return listainventario.size();
    }

    private String listarTodosLosProductos() {
        // Devuelve una cadena con todos los productos listados
        return listarProductos();
    }

    private double calcularValorTotalInventario() {
        // Calcula y devuelve el valor total del inventario
        return calcularValorTotal();
    }
   
         
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInventario = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        labTotalInventario = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel4.setText("jLabel4");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(51, 255, 204))); // NOI18N

        jLabel6.setText("Codigo");

        jLabel7.setText("Nombre Producto");

        jLabel8.setText("Cantidad");

        jLabel9.setText("Precio");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNombre)
                    .addComponent(txtCantidad)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(txtCodigo))
                .addContainerGap(225, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Inventario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 255, 51))); // NOI18N

        tblInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Cantidad", "Precio", "SubTotal"
            }
        ));
        jScrollPane1.setViewportView(tblInventario);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        btnGuardar.setText("Agregar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel10.setText("Valor Total del Inventario");

        labTotalInventario.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(btnGuardar)
                        .addGap(30, 30, 30)
                        .addComponent(btnLimpiar)
                        .addGap(41, 41, 41)
                        .addComponent(btnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(304, 304, 304)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labTotalInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(labTotalInventario))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        agregarProductoAlInventario();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
         limpiarTodosLosDatos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
         salirDeLaAplicacion();
    }//GEN-LAST:event_btnSalirActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labTotalInventario;
    private javax.swing.JTable tblInventario;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables

  

   

   
}
