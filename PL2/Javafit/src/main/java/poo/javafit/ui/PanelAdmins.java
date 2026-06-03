/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package poo.javafit.ui;
import poo.javafit.model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Ventana grafica principal del panel de administracion para el sistema
 * JavaFit.
 * Proporciona las herramientas visuales para controlar el cuadro de mando
 * (Dashboard), listar y buscar socios por categorias, filtrar y dar de baja
 * actividades, asi como consultar e inspeccionar los historiales de reservas
 * financieras.
 *
 * @author RAYANs
 */
public class PanelAdmins extends javax.swing.JFrame {

    /**
     * Registrador de eventos e informacion del sistema para auditoria interna.
     */
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PanelAdmins.class.getName());

    /**
     * El motor de logica y persistencia de datos del gimnasio.
     */
    private GymManager gym;

    /**
     * Construye e inicializa el panel de control del administrador del
     * gimnasio.
     * Ajusta las posiciones fijas de los paneles de resumen, enlaza la logica
     * del manager general, carga las vistas de datos en las tablas e inicializa
     * los disparadores.
     *
     * @param parent El marco de la ventana padre que invoca a este panel.
     * @param gym El gestor de datos operativo del gimnasio.
     */
    public PanelAdmins(java.awt.Frame parent, GymManager gym) {
        this.gym = gym;
        initComponents();

        jPanel1.setBounds(24, 81, 200, 120);
        jPanel2.setBounds(242, 81, 200, 120);
        jPanel3.setBounds(460, 81, 200, 120);

        cargarDashboard();
        this.setTitle("Panel de control - Admin");

        if (this.gym != null && this.gym.getActividades() != null) {
            cargarTablaActividades(this.gym.getActividades());
        }

        configurarTablas();
        configurarCampoDescripcion();

    }

    /**
     * Modifica las propiedades visuales por defecto de los componentes de las
     * tablas.

     * Desactiva de forma especifica el renderizado de rejillas internas.
     */
    private void configurarTablas() {
        jTable1.setShowGrid(false);
    }

    /**
     * Enlaza un escuchador de documentos al cuadro de texto del coste
     * economico.
     * Controla en tiempo real mediante callbacks si el campo de descripcion
     * debe bloquearse o desbloquearse segun el estado del precio.
     */
    private void configurarCampoDescripcion() {
        txtDescripcion.setEnabled(false);
        txtDescripcion.setToolTipText("Rellena el precio primero");
        txtPrecio.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                actualizarDescripcion();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                actualizarDescripcion();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                actualizarDescripcion();
            }
        });
    }

    /**
     * Evalua de forma interna el texto del precio para conmutar la edicion de
     * la descripcion.
     * Borra el contenido de la descripcion si el precio se queda en blanco.
     */
    private void actualizarDescripcion() {
        boolean tienePrecio = !txtPrecio.getText().trim().isEmpty();
        txtDescripcion.setEnabled(tienePrecio);
        if (!tienePrecio) {
            txtDescripcion.setText("");
        }
    }

    /**
     * Modifica la tarjeta activa del contenedor principal usando CardLayout.
     *
     * @param card El identificador string de la vista o panel al que se desea
     * cambiar.
     */
    private void cambiarTarjeta(String card) {
        java.awt.CardLayout cl = (java.awt.CardLayout) jPanel7.getLayout();
        cl.show(jPanel7, card);
    }

    /**
     * Recalcula los indicadores numericos del cuadro de mando principal y
     * refresca la tabla inicial.
     * Extrae los totales de socios, actividades y solicitudes de reserva,
     * inyectandolos en etiquetas personalizadas e insertando las filas
     * ordenadas en el modelo visual.
     */
    private void cargarDashboard() {
        jLabel2.setText(String.valueOf(gym.getSocios().size()));
        jLabel2.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 28));
        jLabel2.setForeground(new java.awt.Color(0, 102, 204));
        jLabel3.setText("Socios");
        jLabel3.setForeground(new java.awt.Color(120, 120, 120));

        jLabel4.setText(String.valueOf(gym.getActividades().size()));
        jLabel4.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 28));
        jLabel4.setForeground(new java.awt.Color(0, 153, 76));
        jLabel5.setText("Actividades");
        jLabel5.setForeground(new java.awt.Color(120, 120, 120));

        jLabel8.setText(String.valueOf(gym.getReservas().size()));
        jLabel8.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 28));
        jLabel8.setForeground(new java.awt.Color(204, 102, 0));
        jLabel9.setText("Reservas");
        jLabel9.setForeground(new java.awt.Color(120, 120, 120));

        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        for (Reserva r : gym.getReservas()) {
            modelo.addRow(new Object[]{
                r.getSocio().getNombre(),
                r.getActividad().getNombre(),
                r.getActividad().getTipo().toString(),
                String.format("%.2f €", r.getPreciofinal()),
                r.getFecha().toString()
            });
        }
        jTable1.setFillsViewportHeight(true);
        jTable1.setRowHeight(24);
    }

    /**
     * Rellena la tabla visual especifica de socios con los datos de un conjunto
     * filtrado.
     *
     * @param lista La lista de objetos {@link Socios} que se van a dibujar.
     */
    private void cargarTablaSocios(java.util.List<Socios> lista) {
        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        modelo.setRowCount(0);
        for (Socios s : lista) {
            modelo.addRow(new Object[]{
                s.getNombre(),
                s.getCorreo(),
                s.getTelefono(),
                (s instanceof Vip) ? "Vip" : "Basico",
                s.getDireccion()
            });
        }
        jTable2.setFillsViewportHeight(true);
        jTable2.setRowHeight(24);
    }

    /**
     * Rellena la tabla visual de catalogo de actividades controlando los
     * valores nulos asociados.
     *
     * @param lista La lista de objetos {@link Actividades} que se van a
     * procesar.
     */
    private void cargarTablaActividades(java.util.List<Actividades> lista) {
        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) jTable3.getModel();
        modelo.setRowCount(0);
        for (Actividades a : lista) {
            String horarioStr = (a.getHorario() != null) ? a.getHorario().toString() : "Sin horario";
            String diaStr = a.getHorario() != null ? a.getHorario().getDia().toString() : "";
            String monitorStr = a.getMonitor() != null ? a.getMonitor().getNombre() : "";
            String salaStr = a.getSala() != null ? a.getSala().getNombre() : "";
            modelo.addRow(new Object[]{
                a.getNombre(),
                a.getTipo().toString(),
                a.tipoCategoria(),
                salaStr,
                diaStr,
                horarioStr,
                monitorStr,
                a.getPrecio() == 0 ? "Gratis" : String.format("%.2f €", a.getPrecio())
            });
        }
        jTable3.setFillsViewportHeight(true);
        jTable3.setRowHeight(24);
    }

    /**
     * Carga el conjunto completo de reservas en la tabla correspondiente,
     * habilitando ordenamiento automatico.
     *
     * @param lista La lista de objetos {@link Reserva} que se desea reflejar.
     */
    private void cargarTablaReservas(java.util.List<Reserva> lista) {
        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) jTable4.getModel();
        modelo.setRowCount(0);
        for (Reserva r : lista) {
            String diaStr = r.getActividad().getHorario() != null
                    ? r.getActividad().getHorario().getDia().toString() : "";
            String horarioStr = r.getActividad().getHorario() != null
                    ? r.getActividad().getHorario().toString() : "";
            modelo.addRow(new Object[]{
                r.getSocio().getNombre(),
                r.getActividad().getNombre(),
                diaStr,
                horarioStr,
                String.format("%.2f €", r.getPreciofinal()),
                r.getFecha().toString()
            });
        }
        jTable4.setRowHeight(24);
        jTable4.setAutoCreateRowSorter(true);
    }

    /**
     * Filtra la coleccion total de socios cruzando las entradas de busqueda
     * alfabeticas y el selector combo.
     * Compara ignorando mayusculas y evalua si la instancia hereda de VIP o de
     * Basico para actualizar el modelo.
     */
    private void buscarSocios() {
        String texto = jTextField1.getText().trim().toLowerCase();
        String tipo = (String) jComboBox1.getSelectedItem();

        java.util.List<Socios> resultado = new java.util.ArrayList<>();
        for (Socios s : gym.getSocios()) {
            boolean coincideNombre = texto.isEmpty()
                    || s.getNombre().toLowerCase().contains(texto);
            boolean coincideTipo = "Todos".equals(tipo)
                    || (tipo.equalsIgnoreCase("Vip") && s instanceof Vip)
                    || (tipo.equalsIgnoreCase("Basico") && s instanceof Basico);
            if (coincideNombre && coincideTipo) {
                resultado.add(s);
            }
        }
        cargarTablaSocios(resultado);
    }

    /**
     * Inicializa los desplegables de busqueda de actividades analizando el
     * estado actual de los datos.
     * Inserta las constantes globales de los enums y agrupa mediante un Set los
     * nombres de los monitores asignados para evitar repeticiones visuales en
     * el filtro.
     */
    private void cargarFiltrosActividades() {
        // Tipo
        javax.swing.DefaultComboBoxModel<String> modelTipo
                = new javax.swing.DefaultComboBoxModel<>();
        modelTipo.addElement("Todos");
        for (TipoActividad t : TipoActividad.values()) {
            modelTipo.addElement(t.toString());
        }
        jComboBox2.setModel(modelTipo);

        // Monitor
        javax.swing.DefaultComboBoxModel<String> modelMonitor
                = new javax.swing.DefaultComboBoxModel<>();
        modelMonitor.addElement("Todos");
        java.util.Set<String> monitoresSet = new java.util.LinkedHashSet<>();
        for (Actividades a : gym.getActividades()) {
            if (a.getMonitor() != null) {
                monitoresSet.add(a.getMonitor().getNombre());
            }
        }
        for (String m : monitoresSet) {
            modelMonitor.addElement(m);
        }
        jComboBox3.setModel(modelMonitor);

        // Día
        javax.swing.DefaultComboBoxModel<String> modelDia
                = new javax.swing.DefaultComboBoxModel<>();
        modelDia.addElement("Todos");
        for (Horario.Dia d : Horario.Dia.values()) {
            modelDia.addElement(d.toString());
        }
        jComboBox4.setModel(modelDia);
    }

    /**
     * Ejecuta una busqueda multidimensional cruzando los tres selectores de
     * actividades elegidos.
     * Revisa de forma segura los valores ignorando las restricciones si esta
     * seleccionada la opcion por defecto.
     */
    private void filtrarActividades() {
        String tipo = (String) jComboBox2.getSelectedItem();
        String monitor = (String) jComboBox3.getSelectedItem();
        String dia = (String) jComboBox4.getSelectedItem();

        java.util.List<Actividades> resultado = new java.util.ArrayList<>();
        for (Actividades a : gym.getActividades()) {
            boolean okTipo = "Todos".equals(tipo)
                    || a.getTipo().toString().equals(tipo);
            boolean okMonitor = "Todos".equals(monitor)
                    || (a.getMonitor() != null && a.getMonitor().getNombre().equals(monitor));
            boolean okDia = "Todos".equals(dia)
                    || (a.getHorario() != null && a.getHorario().getDia().toString().equals(dia));
            if (okTipo && okMonitor && okDia) {
                resultado.add(a);
            }
        }
        cargarTablaActividades(resultado);
    }

    /**
     * Recupera la fila seleccionada por el usuario para eliminar de forma
     * permanente una actividad.
     * Lanza un dialogo de confirmacion y, si se acepta, limpia las referencias
     * internas de la coleccion, refrescando tanto las tablas de catalogo como
     * los numeros del dashboard.
     */
    private void eliminarActividadSeleccionada() {
        int fila = jTable3.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Selecciona una actividad para eliminar.",
                    "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nombre = (String) jTable3.getValueAt(fila, 0);
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Eliminar la actividad \"" + nombre + "\"?",
                "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            gym.getActividades().removeIf(a -> a.getNombre().equals(nombre));
            cargarTablaActividades(gym.getActividades());
            cargarDashboard();
        }
    }

    /**
     * Filtra el listado de reservas descartando los registros anteriores a una
     * fecha limite.
     * <p>
     * Intenta convertir el texto a objeto cronologico; si el formato es
     * invalido, captura la excepcion y notifica al administrador mediante una
     * ventana emergente de error.
     */
    private void filtrarReservasPorFecha() {
        String texto = jTextField2.getText().trim();
        try {
            java.time.LocalDate desde = java.time.LocalDate.parse(texto);
            java.util.List<Reserva> resultado = new java.util.ArrayList<>();
            for (Reserva r : gym.getReservas()) {
                if (!r.getFecha().isBefore(desde)) {
                    resultado.add(r);
                }
            }
            cargarTablaReservas(resultado);
        } catch (java.time.format.DateTimeParseException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido. Usa AAAA-MM-DD",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        cbTipo = new javax.swing.JComboBox<>();
        cbDia = new javax.swing.JComboBox<>();
        spinnerHoraInicio = new javax.swing.JSpinner();
        spinnerHoraFinal = new javax.swing.JSpinner();
        txtImagen = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        txtSala = new javax.swing.JTextField();
        txtMonitor = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jDialog2 = new javax.swing.JDialog();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        Nombretxt = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        Correotxt = new javax.swing.JTextField();
        Clavetxt = new javax.swing.JTextField();
        Direcciontxt = new javax.swing.JTextField();
        tarjetatxt = new javax.swing.JTextField();
        telefonotxt = new javax.swing.JTextField();
        tiposociotxt = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        Tipotxt = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        Caducidadtxt = new javax.swing.JSpinner();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jButton9 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 7;
            }
        };
        jButton3 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();

        jLabel20.setText("Nombre:");

        jLabel21.setText("Tipo:");

        jLabel22.setText("Sala:");

        jLabel23.setText("Monitor");

        jLabel24.setText("Dia:");

        jLabel25.setText("Hora inicio:");

        jLabel26.setText("Hora final:");

        jLabel27.setText("Imagen:");

        jLabel29.setText("Precio:");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setText("Nueva Actividad");

        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yoga", "Natacion", "Musculacion", "Cardio" }));
        cbTipo.addActionListener(this::cbTipoActionPerformed);

        cbDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo", " " }));

        spinnerHoraInicio.setModel(new javax.swing.SpinnerDateModel());
        spinnerHoraInicio.setEditor(new javax.swing.JSpinner.DateEditor(spinnerHoraInicio, "HH:mm"));

        spinnerHoraFinal.setModel(new javax.swing.SpinnerDateModel());
        spinnerHoraFinal.setEditor(new javax.swing.JSpinner.DateEditor(spinnerHoraFinal, "HH:mm"));
        spinnerHoraFinal.addPropertyChangeListener(this::spinnerHoraFinalPropertyChange);

        jButton15.setText("Guardar");
        jButton15.addActionListener(this::jButton15ActionPerformed);

        jButton16.setText("Cancelar");
        jButton16.addActionListener(this::jButton16ActionPerformed);

        jLabel28.setText("Descripcion:");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel28)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20))
                        .addGap(25, 25, 25)
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDialog1Layout.createSequentialGroup()
                                .addComponent(jButton15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton16))
                            .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPrecio)
                                .addGroup(jDialog1Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cbTipo, 0, 151, Short.MAX_VALUE)
                                        .addComponent(txtNombre)
                                        .addComponent(txtSala)))
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtMonitor, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtImagen, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerHoraFinal, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinnerHoraInicio, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbDia, javax.swing.GroupLayout.Alignment.LEADING, 0, 151, Short.MAX_VALUE))
                                .addComponent(txtDescripcion)))))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel30)
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtMonitor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(cbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(spinnerHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(spinnerHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15)
                    .addComponent(jButton16))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jLabel31.setText("Nombre:");

        jLabel32.setText("Correo:");

        jLabel33.setText("Clave:");

        jLabel34.setText("Direccion:");

        jLabel35.setText("Numero:");

        jLabel36.setText("Telefono:");

        jLabel37.setText("Tipo Socio:");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel41.setText("Nueva Socio");

        jButton17.setText("Guardar");
        jButton17.addActionListener(this::jButton17ActionPerformed);

        jButton18.setText("Cancelar");
        jButton18.addActionListener(this::jButton18ActionPerformed);

        tiposociotxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Basico", "Vip" }));

        jLabel38.setText("Tarjeta:");

        Tipotxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mastercard", "Paypal", "Visa" }));

        jLabel39.setText("Caducidad:");

        Caducidadtxt.setModel(new javax.swing.SpinnerDateModel());
        Caducidadtxt.setEditor(new javax.swing.JSpinner.DateEditor(Caducidadtxt, "MM/yyyy"));

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addGroup(jDialog2Layout.createSequentialGroup()
                        .addComponent(jButton17)
                        .addGap(48, 48, 48)
                        .addComponent(jButton18))
                    .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jDialog2Layout.createSequentialGroup()
                            .addComponent(jLabel37)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                            .addComponent(tiposociotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jDialog2Layout.createSequentialGroup()
                            .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel35)
                                .addComponent(jLabel34)
                                .addComponent(jLabel33)
                                .addComponent(jLabel32)
                                .addComponent(jLabel31)
                                .addComponent(jLabel38))
                            .addGap(32, 32, 32)
                            .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Nombretxt, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                .addComponent(Correotxt)
                                .addComponent(Clavetxt)
                                .addComponent(Direcciontxt)
                                .addComponent(tarjetatxt)
                                .addComponent(Tipotxt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jDialog2Layout.createSequentialGroup()
                            .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel36)
                                .addComponent(jLabel39))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(telefonotxt)
                                .addComponent(Caducidadtxt, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel41)
                .addGap(18, 18, 18)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(Nombretxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(Correotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(Clavetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(Direcciontxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tipotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(18, 18, 18)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tarjetatxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(18, 18, 18)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(Caducidadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(telefonotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(tiposociotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton18))
                .addGap(26, 26, 26))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(245, 245, 245));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(220, 220, 220)));
        jPanel6.setPreferredSize(new java.awt.Dimension(210, 0));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel6.setText("Administrador");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Admin");

        jButton10.setBackground(new java.awt.Color(245, 245, 245));
        jButton10.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jButton10.setText("Reservas");
        jButton10.setBorderPainted(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.setFocusPainted(false);
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton10.setMaximumSize(new java.awt.Dimension(32767, 38));
        jButton10.addActionListener(this::jButton10ActionPerformed);

        jSeparator3.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 1, 1, 50));
        jSeparator3.setMaximumSize(new java.awt.Dimension(32767, 20));

        jButton6.setBackground(new java.awt.Color(245, 245, 245));
        jButton6.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jButton6.setText("Dashboard");
        jButton6.setBorderPainted(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.setFocusPainted(false);
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.setMaximumSize(new java.awt.Dimension(32767, 38));
        jButton6.setMinimumSize(new java.awt.Dimension(75, 38));
        jButton6.setPreferredSize(new java.awt.Dimension(38, 38));
        jButton6.setRolloverEnabled(false);
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jButton7.setBackground(new java.awt.Color(245, 245, 245));
        jButton7.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jButton7.setText("Socios");
        jButton7.setBorderPainted(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.setFocusPainted(false);
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.setMaximumSize(new java.awt.Dimension(32767, 38));
        jButton7.addActionListener(this::jButton7ActionPerformed);

        jButton8.setBackground(new java.awt.Color(245, 245, 245));
        jButton8.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jButton8.setText("Actividades");
        jButton8.setBorderPainted(false);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.setFocusPainted(false);
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton8.setMaximumSize(new java.awt.Dimension(32767, 38));
        jButton8.addActionListener(this::jButton8ActionPerformed);

        jLabel40.setFont(new java.awt.Font("Monotype Corsiva", 1, 36)); // NOI18N
        jLabel40.setText("JavaFit");

        jSeparator4.setMaximumSize(new java.awt.Dimension(32767, 480));

        jButton9.setBackground(new java.awt.Color(245, 245, 245));
        jButton9.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jButton9.setText("Cerrar sesion");
        jButton9.setBorderPainted(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.setFocusPainted(false);
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton9.setMaximumSize(new java.awt.Dimension(32767, 38));
        jButton9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(this::jButton9ActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)))
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 274, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 622));

        jPanel7.setLayout(new java.awt.CardLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(null);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Dashboard");
        jPanel9.add(jLabel1);
        jLabel1.setBounds(24, 26, 143, 49);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jLabel2.setText("1");

        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel3)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel1);
        jPanel1.setBounds(24, 81, 0, 0);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jLabel4.setText("2");

        jLabel5.setText("Actividades");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel5)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel2);
        jPanel2.setBounds(242, 81, 0, 0);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jLabel8.setText("3");

        jLabel9.setText("Reservas");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel8))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel9)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel3);
        jPanel3.setBounds(460, 81, 0, 0);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Ultimas reservas");
        jPanel9.add(jLabel10);
        jLabel10.setBounds(24, 219, 110, 20);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Socio", "Actividad", "Tipo", "Precio final", "Fecha"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jTable1.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jTable1);

        jPanel9.add(jScrollPane1);
        jScrollPane1.setBounds(24, 245, 636, 350);

        jPanel7.add(jPanel9, "card3");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Socios");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Buscar:");

        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Tipo:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Basico", "Vip" }));

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Buscar");
        jButton1.setBorderPainted(false);
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Limpiar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Correo", "Telefono", "Tipo", "Direccion"
            }
        ));
        jTable2.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(jTable2);
        javax.swing.table.DefaultTableModel modeloSocios = (javax.swing.table.DefaultTableModel) jTable2.getModel();

        modeloSocios.addTableModelListener(new javax.swing.event.TableModelListener() {
            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                    int fila = e.getFirstRow();
                    int columna = e.getColumn();

                    if (columna == -1) return;

                    Object nuevoValorObj = modeloSocios.getValueAt(fila, columna);
                    String nuevoValor = (nuevoValorObj != null) ? nuevoValorObj.toString().trim() : "";

                    try {
                        // Buscar el socio por nombre en la fila
                        String nombreSocio = (String) modeloSocios.getValueAt(fila, 0);
                        Socios socio = null;
                        for (Socios s : gym.getSocios()) {
                            if (s.getNombre().equals(nombreSocio)) {
                                socio = s;
                                break;
                            }
                        }

                        if (socio == null) return;

                        String nombreColumna = modeloSocios.getColumnName(columna);

                        switch (nombreColumna) {
                            case "Nombre":
                            if (nuevoValor.isEmpty()) {
                                throw new IllegalArgumentException("El nombre no puede estar vacío.");
                            }
                            socio.setNombre(nuevoValor);
                            break;

                            case "Direccion":
                            if (nuevoValor.isEmpty()) {
                                throw new IllegalArgumentException("La dirección no puede estar vacía.");
                            }
                            socio.setDireccion(nuevoValor);
                            break;

                            case "Correo":
                            if (nuevoValor.isEmpty()) {
                                throw new IllegalArgumentException("El correo no puede estar vacío.");
                            }
                            if (!nuevoValor.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {
                                throw new IllegalArgumentException("El correo no tiene un formato válido.");
                            }
                            socio.setCorreo(nuevoValor);
                            break;

                            case "Telefono":
                            if (nuevoValor.isEmpty()) {
                                throw new IllegalArgumentException("El teléfono no puede estar vacío.");
                            }
                            try {
                                int telefono = Integer.parseInt(nuevoValor);
                                if (String.valueOf(telefono).length() != 9) {
                                    throw new IllegalArgumentException("El teléfono debe tener exactamente 9 dígitos.");
                                }
                                socio.setTelefono(telefono);
                            } catch (NumberFormatException ex) {
                                throw new IllegalArgumentException("El teléfono debe contener solo números.");
                            }
                            break;

                            case "Tipo":
                            if (!nuevoValor.equalsIgnoreCase("Basico") && !nuevoValor.equalsIgnoreCase("Vip")) {
                                throw new IllegalArgumentException("Tipo debe ser 'Basico' o 'Vip'.");
                            }
                            // Reemplazar el objeto entero porque Vip y Basico son clases distintas
                            int index = gym.getSocios().indexOf(socio);
                            Socios nuevoSocio;
                            if (nuevoValor.equalsIgnoreCase("Vip") && !(socio instanceof Vip)) {
                                nuevoSocio = new Vip(socio.getDireccion(), socio.getTarjeta(),
                                    socio.getTelefono(), socio.getNombre(),
                                    socio.getCorreo(), socio.getClave());
                                gym.getSocios().set(index, nuevoSocio);
                            } else if (nuevoValor.equalsIgnoreCase("Basico") && !(socio instanceof Basico)) {
                                nuevoSocio = new Basico(socio.getDireccion(), socio.getTarjeta(),
                                    socio.getTelefono(), socio.getNombre(),
                                    socio.getCorreo(), socio.getClave());
                                gym.getSocios().set(index, nuevoSocio);
                            }
                            break;
                        }

                        gym.guardarDatos();

                    } catch (IllegalArgumentException ex) {
                        javax.swing.JOptionPane.showMessageDialog(null,
                            "Dato incorrecto:\n" + ex.getMessage(),
                            "Error de validación",
                            javax.swing.JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        jButton4.setText("+Añadir Socio");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8, "card2");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(null);

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel14.setText("Actividades");
        jPanel5.add(jLabel14);
        jLabel14.setBounds(21, 40, 103, 24);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel11.setPreferredSize(new java.awt.Dimension(636, 48));

        jLabel15.setText("Tipo:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

        jLabel16.setText("Monitor:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel17.setText("Dia:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton5.setText("Filtrar");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jButton11.setText("Limpiar");
        jButton11.addActionListener(this::jButton11ActionPerformed);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(jButton11))
                .addGap(12, 12, 12))
        );

        jPanel5.add(jPanel11);
        jPanel11.setBounds(10, 80, 660, 54);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Tipo", "Categoria", "Sala", "Dia", "Horario", "Monitor", "Precio"
            }
        ));
        jTable3.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(jTable3);
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTable3.getModel();

        modelo.addTableModelListener(new javax.swing.event.TableModelListener() {
            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                    int fila = e.getFirstRow();
                    int columna = e.getColumn();

                    if (columna == -1) return;

                    Object nuevoValorObj = modelo.getValueAt(fila, columna);
                    String nuevoValor = (nuevoValorObj != null) ? nuevoValorObj.toString().trim() : "";

                    try {
                        String nombreActividad = (String) modelo.getValueAt(fila, 0);
                        Actividades actividad = null;
                        for (Actividades a : gym.getActividades()) {
                            if (a.getNombre().equals(nombreActividad)) {
                                actividad = a;
                                break;
                            }
                        }

                        if (actividad == null) return;

                        String nombreColumna = modelo.getColumnName(columna);

                        switch (nombreColumna) {
                            case "Nombre":
                            if (nuevoValor.isEmpty()) {
                                throw new IllegalArgumentException("El campo 'Nombre' no puede estar vacío.");
                            }
                            actividad.setNombre(nuevoValor);
                            break;

                            case "Sala":
                            if (nuevoValor.isEmpty()) {
                                throw new IllegalArgumentException("El campo 'Sala' no puede estar vacío.");
                            }
                            actividad.getSala().setNombre(nuevoValor);
                            break;

                            case "Monitor":
                            if (nuevoValor.isEmpty()) {
                                throw new IllegalArgumentException("El campo 'Monitor' no puede estar vacío.");
                            }
                            actividad.getMonitor().setNombre(nuevoValor);
                            break;

                            case "Tipo":
                            try {
                                TipoActividad tipo = TipoActividad.valueOf(nuevoValor.toUpperCase());
                                actividad.setTipo(tipo);
                            } catch (IllegalArgumentException ex) {
                                throw new IllegalArgumentException("El Tipo '" + nuevoValor + "' no es válido.");
                            }
                            break;

                            case "Dia":
                            try {
                                String diaTexto = nuevoValor.toUpperCase()
                                .replace("IÉ", "IE").replace("Á", "A");
                                Horario.Dia dia = Horario.Dia.valueOf(diaTexto);
                                actividad.getHorario().setDia(dia);
                            } catch (IllegalArgumentException ex) {
                                throw new IllegalArgumentException("El día '" + nuevoValor + "' no es válido.");
                            }
                            break;

                            case "Horario":
                            try {
                                String[] partes = nuevoValor.split("-");
                                if (partes.length != 2) {
                                    throw new IllegalArgumentException("El horario debe tener formato HH:mm-HH:mm (ej: 09:00-10:00).");
                                }
                                java.time.LocalTime inicio = java.time.LocalTime.parse(partes[0].trim());
                                java.time.LocalTime fin = java.time.LocalTime.parse(partes[1].trim());
                                if (fin.equals(inicio) || fin.isBefore(inicio)) {
                                    throw new IllegalArgumentException("La hora final debe ser posterior a la de inicio.");
                                }
                                actividad.getHorario().setInicio(inicio);
                                actividad.getHorario().setFin(fin);
                            } catch (java.time.format.DateTimeParseException ex) {
                                throw new IllegalArgumentException("Formato de hora no válido. Usa HH:mm (ej: 09:00-10:00).");
                            }
                            break;
                        }

                        gym.guardarDatos();

                    } catch (IllegalArgumentException ex) {
                        javax.swing.JOptionPane.showMessageDialog(null,
                            "Error al modificar la celda:\n" + ex.getMessage(),
                            "Error de validación",
                            javax.swing.JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        jScrollPane3.setViewportView(jTable3);

        jPanel5.add(jScrollPane3);
        jScrollPane3.setBounds(10, 150, 660, 402);

        jButton3.setBackground(new java.awt.Color(0, 102, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("+Nueva Actividad");
        jButton3.setBorderPainted(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jPanel5.add(jButton3);
        jButton3.setBounds(20, 570, 152, 23);

        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton12.setForeground(new java.awt.Color(204, 0, 0));
        jButton12.setText("Eliminar seleccionada");
        jButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton12.addActionListener(this::jButton12ActionPerformed);
        jPanel5.add(jButton12);
        jButton12.setBounds(200, 570, 153, 23);

        jPanel7.add(jPanel5, "card5");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(null);

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel18.setText("Reservas");
        jPanel10.add(jLabel18);
        jLabel18.setBounds(22, 35, 83, 24);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel12.setPreferredSize(new java.awt.Dimension(636, 48));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel19.setText("Desde:");

        jButton13.setBackground(new java.awt.Color(0, 102, 204));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Filtrar");
        jButton13.setBorderPainted(false);
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton13.setFocusPainted(false);
        jButton13.addActionListener(this::jButton13ActionPerformed);

        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton14.setText("Todos");
        jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton14.addActionListener(this::jButton14ActionPerformed);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13)
                    .addComponent(jButton14))
                .addContainerGap())
        );

        jPanel10.add(jPanel12);
        jPanel12.setBounds(22, 77, 636, 48);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Socio", "Actividad", "Dia", "Horario", "Precio", "Fecha"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jTable4.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane4.setViewportView(jTable4);

        jPanel10.add(jScrollPane4);
        jScrollPane4.setBounds(22, 143, 636, 465);

        jPanel7.add(jPanel10, "card4");

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 690, 622));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        cambiarTarjeta("card3");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        dispose();
        java.awt.EventQueue.invokeLater(() -> new Login2(gym).setVisible(true));

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        cargarTablaReservas(gym.getReservas());
        cambiarTarjeta("card4");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        filtrarReservasPorFecha();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        try {
            String nombre = txtNombre.getText().trim();
            String salaText = txtSala.getText().trim();
            String monitorText = txtMonitor.getText().trim();
            String imagen = txtImagen.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String precioText = txtPrecio.getText().trim();

            // Validar campos obligatorios individualmente
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre de la actividad no puede estar vacío.");
            }
            if (salaText.isEmpty()) {
                throw new IllegalArgumentException("El campo 'Sala' no puede estar vacío.");
            }
            if (monitorText.isEmpty()) {
                throw new IllegalArgumentException("El campo 'Monitor' no puede estar vacío.");
            }

            // Tipo con mensaje claro
            TipoActividad tipo;
            try {
                tipo = TipoActividad.valueOf(cbTipo.getSelectedItem().toString().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El tipo de actividad seleccionado no es válido.");
            }

            // Día con normalización y mensaje claro
            Horario.Dia dia;
            try {
                String diaTexto = cbDia.getSelectedItem().toString().toUpperCase()
                        .replace("IÉ", "IE").replace("Á", "A");
                dia = Horario.Dia.valueOf(diaTexto);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El día seleccionado no es válido.");
            }

            // Horas con control de null
            if (spinnerHoraInicio.getValue() == null) {
                throw new IllegalArgumentException("Debes establecer una hora de inicio.");
            }
            if (spinnerHoraFinal.getValue() == null) {
                throw new IllegalArgumentException("Debes establecer una hora final.");
            }

            LocalTime horaInicio = ((java.util.Date) spinnerHoraInicio.getValue())
                    .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
            LocalTime horaFinal = ((java.util.Date) spinnerHoraFinal.getValue())
                    .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();

            if (horaFinal.equals(horaInicio)) {
                throw new IllegalArgumentException("La hora de inicio y final no pueden ser iguales.");
            }
            if (horaFinal.isBefore(horaInicio)) {
                throw new IllegalArgumentException("La hora final debe ser posterior a la de inicio.");
            }

            Horario horario = new Horario(dia, horaInicio, horaFinal);
            Sala salas = new Sala(1, salaText);
            Monitor monitores = new Monitor(monitorText, tipo, new ArrayList<>());

            if (!precioText.isEmpty()) {
                double precio;
                try {
                    precio = Double.parseDouble(precioText);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("El precio no es válido. Usa punto decimal (ej: 9.99).");
                }
                if (precio < 0) {
                    throw new IllegalArgumentException("El precio no puede ser negativo.");
                }
                if (descripcion.isEmpty()) {
                    throw new IllegalArgumentException("Las actividades de pago requieren descripción.");
                }

                gym.añadirActividad(new Especial(nombre, tipo, salas, horario, monitores, imagen, precio, descripcion));
            } else {
                gym.añadirActividad(new Normal(nombre, tipo, salas, horario, imagen, monitores));
            }
            gym.guardarDatos();
            javax.swing.JOptionPane.showMessageDialog(this, "¡Actividad guardada con éxito!");
            jDialog1.dispose();

        } catch (IllegalArgumentException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Dato incorrecto:\n" + e.getMessage(),
                    "Error de validación",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: algún campo está vacío o sin seleccionar.\n" + e.getMessage(),
                    "Error interno",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error inesperado:\n" + e.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jDialog1.setLocationRelativeTo(this);
        jDialog1.setSize(550, 650);
        jDialog1.setTitle("Añadir Actividad");
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        try {
            // Validaciones previas
            String nombre = Nombretxt.getText().trim(); // ← BUG: usas getName() en vez de getText()
            String correo = Correotxt.getText().trim();
            String clave = Clavetxt.getText().trim();
            String direccion = Direcciontxt.getText().trim();
            String tarjeta = Tipotxt.getSelectedItem().toString().trim();
            String socio = tiposociotxt.getSelectedItem().toString().trim();

            // Validar campos vacíos ANTES de parsear números
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }
            if (correo.isEmpty()) {
                throw new IllegalArgumentException("El correo no puede estar vacío.");
            }
            if (clave.isEmpty()) {
                throw new IllegalArgumentException("La clave no puede estar vacía.");
            }
            if (direccion.isEmpty()) {
                throw new IllegalArgumentException("La dirección no puede estar vacía.");
            }

            // Validar formato correo
            if (!correo.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {
                throw new IllegalArgumentException("El correo no tiene un formato válido (ej: usuario@gmail.com).");
            }

            // Validar clave mínima
            if (clave.length() < 6) {
                throw new IllegalArgumentException("La clave debe tener al menos 6 caracteres.");
            }

            // Parsear número de tarjeta con control de error
            long numero;
            try {
                numero = Long.parseLong(tarjetatxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El número de tarjeta debe ser numérico y sin espacios.");
            }

            // Parsear teléfono con control de error
            int telefono;
            try {
                telefono = Integer.parseInt(telefonotxt.getText().trim());
                if (String.valueOf(telefono).length() != 9) {
                    throw new IllegalArgumentException("El teléfono debe tener 9 dígitos.");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El teléfono debe contener solo números.");
            }

            // Parsear fecha con control de null
            if (Caducidadtxt.getValue() == null) {
                throw new IllegalArgumentException("Debes establecer una fecha de caducidad.");
            }
            LocalDate caducidad = ((java.util.Date) Caducidadtxt.getValue())
                    .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            // Validar que la tarjeta no esté caducada
            if (caducidad.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("La tarjeta bancaria ya está caducada.");
            }

            TarjetaBancaria tarjetas = new TarjetaBancaria(tarjeta, numero, caducidad);

            if (socio.equals("Basico")) {
                gym.añadirSocio(new Basico(direccion, tarjetas, telefono, nombre, correo, clave));
            } else if (socio.equals("Vip")) {
                gym.añadirSocio(new Vip(direccion, tarjetas, telefono, nombre, correo, clave));
            } else {
                throw new IllegalArgumentException("Tipo de socio no reconocido: " + socio);
            }
            gym.guardarDatos();
            javax.swing.JOptionPane.showMessageDialog(this, "¡Socio añadido con éxito!");
            jDialog2.dispose();

        } catch (IllegalArgumentException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Dato incorrecto:\n" + e.getMessage(),
                    "Error de validación",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        } catch (NullPointerException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: algún campo está vacío o sin seleccionar.\n" + e.getMessage(),
                    "Error interno",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error inesperado:\n" + e.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jDialog2.setLocationRelativeTo(this);
        jDialog2.setTitle("Agregar socios");
        jDialog2.setSize(450, 550);
        jDialog2.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void spinnerHoraFinalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_spinnerHoraFinalPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_spinnerHoraFinalPropertyChange

    private void cbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        jDialog1.dispose();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        jDialog2.dispose();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        eliminarActividadSeleccionada();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        filtrarActividades();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        cargarTablaActividades(gym.getActividades());
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        buscarSocios();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jTextField1.setText("");
        jComboBox1.setSelectedIndex(0);
        cargarTablaSocios(gym.getSocios());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        cargarTablaReservas(gym.getReservas());
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        cargarTablaSocios(gym.getSocios());
        cambiarTarjeta("card2");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        cargarFiltrosActividades();
        cargarTablaActividades(gym.getActividades());
        cambiarTarjeta("card5");
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        new PanelAdmins(null, new GymManager()).setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner Caducidadtxt;
    private javax.swing.JTextField Clavetxt;
    private javax.swing.JTextField Correotxt;
    private javax.swing.JTextField Direcciontxt;
    private javax.swing.JTextField Nombretxt;
    private javax.swing.JComboBox<String> Tipotxt;
    private javax.swing.JComboBox<String> cbDia;
    private javax.swing.JComboBox<String> cbTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JSpinner spinnerHoraFinal;
    private javax.swing.JSpinner spinnerHoraInicio;
    private javax.swing.JTextField tarjetatxt;
    private javax.swing.JTextField telefonotxt;
    private javax.swing.JComboBox<String> tiposociotxt;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtImagen;
    private javax.swing.JTextField txtMonitor;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSala;
    // End of variables declaration//GEN-END:variables

}
