package poo.javafit.ui;

import poo.javafit.model.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 * Ventana grafica principal que representa el panel de control del socio en
 * JavaFit. Modula todas las interacciones del cliente autenticado,
 * permitiendole explorar la cartelera de actividades mediante una cuadricula
 * dinamica de tarjetas, realizar reservas de plazas en tiempo real, filtrar
 * clases y consultar sus datos de perfil.
 *
 * @author RAYANs
 */
public class PanelSocios extends javax.swing.JFrame {

    private GymManager gym;
    private Socios socio;
    private javax.swing.JPanel gridActividades;

    /**
     * Registrador de eventos para la monitorizacion y auditoria de la interfaz
     * grafica.
     */
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PanelSocios.class.getName());

    /**
     * Crea una nueva instancia del formulario PanelSocios.
     *
     * @param gym Instancia del gestor del gimnasio (GymManager).
     * @param socio Instancia del socio autenticado (Socios).
     */
    public PanelSocios(GymManager gym, Socios socio) {
        this.gym = gym;
        this.socio = socio;
        initComponents();
        this.setTitle("Panel de control - Socio");
        configurarEncabezado();
        configurarGridActividades();
        configurarTablaReservas();
        configurarPerfil();
        rellenarFiltros();
        rellenarGrid(gym.getActividades());
        configurarEstetica();

        setSize(1200, 800);
        setLocationRelativeTo(null); // centra en pantalla
        setResizable(false);
    }

    /**
     * Hace que el panel de socios no se rompa ante cualquier interaccion
     * inesperada.
     */
    private void configurarEstetica() {
        // Quitar grid de la tabla de reservas
        Tabla1.setShowGrid(false);
        Tabla1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        Tabla1.setRowHeight(28);

        // Cabecera de la tabla más limpia
        Tabla1.getTableHeader().setBackground(new java.awt.Color(245, 245, 245));
        Tabla1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        Tabla1.getTableHeader().setBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(220, 220, 220))
        );

        // Quitar borde del scrollPane
        jScrollPane1.setBorder(null);

        // Selección de fila más suave
        Tabla1.setSelectionBackground(new java.awt.Color(232, 240, 254));
        Tabla1.setSelectionForeground(java.awt.Color.BLACK);

        // Fondo blanco en la tabla
        Tabla1.setBackground(java.awt.Color.WHITE);
    }

    /**
     * Carga el nombre del socio y su rol comercial en las etiquetas del
     * encabezado. Utiliza el operador {@code instanceof} para determinar si la
     * membresia es Vip o Basica.
     */
    private void configurarEncabezado() {
        jLabel1.setText(socio.getNombre());
        jLabel3.setText((socio instanceof Vip) ? "Socio Vip" : "Socio Básico");
    }

    /**
     * Inicializa el contenedor de rejilla para la cartelera de actividades.
     * Crea un {@link java.awt.GridLayout} de tres columnas con desbordamiento
     * vertical, lo introduce en un panel de scroll e incrementa la velocidad de
     * desplazamiento.
     */
    private void configurarGridActividades() {
        gridActividades = new javax.swing.JPanel(new java.awt.GridLayout(0, 3, 20, 20));
        gridActividades.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gridActividades.setBackground(java.awt.Color.WHITE);

        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(gridActividades);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        actividades.removeAll();
        actividades.setLayout(new java.awt.BorderLayout(0, 10));
        actividades.add(jPanel3, java.awt.BorderLayout.NORTH);
        actividades.add(scroll, java.awt.BorderLayout.CENTER);
    }

    /**
     * Sincroniza y rellena la tabla de reservas con el historial del socio
     * actual. Limpia el modelo visual e itera sobre las reservas confirmadas
     * del usuario, calculando los importes finales aplicados y formateando las
     * cadenas de texto.
     */
    private void configurarTablaReservas() {
        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) Tabla1.getModel();
        modelo.setRowCount(0);
        for (Reserva r : gym.getReservasDeSocio(socio)) {
            Actividades a = r.getActividad();
            double precio = r.getPreciofinal();
            modelo.addRow(new Object[]{
                a.getNombre(),
                a.getHorario().getDia().toString(),
                a.getHorario().getInicio() + "–" + a.getHorario().getFin(),
                a.getSala().getNombre(),
                (precio == 0) ? "Gratis" : String.format("%.2f €", precio),
                r.getFecha().toString()
            });
        }
    }

    /**
     * Rellena los campos de texto del formulario de perfil con la informacion
     * del usuario. Bloquea la edicion del correo electronico por seguridad y
     * extrae de forma condicional el numero de la tarjeta bancaria si esta se
     * encuentra vinculada al objeto.
     */
    private void configurarPerfil() {
        jTextField1.setText(socio.getNombre());
        jTextField2.setText(socio.getCorreo());
        jTextField2.setEditable(false);
        jTextField3.setText(socio.getClave());
        jTextField4.setText(socio.getDireccion());
        jTextField5.setText(socio.getTarjeta() != null
                ? String.valueOf(socio.getTarjeta().getNumero()) : "Sin tarjeta");
        jTextField6.setText(String.valueOf(socio.getTelefono()));
    }

    /**
     * Alterna la tarjeta o pestaña visible en la vista principal usando
     * CardLayout.
     *
     * @param nombreTarjeta El identificador String del panel de destino al que
     * se desea conmutar.
     */
    private void cambiarTarjeta(String nombreTarjeta) {
        java.awt.CardLayout cl = (java.awt.CardLayout) contenido.getLayout();
        cl.show(contenido, nombreTarjeta);
    }

    /**
     * Fabrica un panel grafico independiente en formato de tarjeta para una
     * actividad concreta. Configura de forma manual las etiquetas de nombre,
     * horarios, sala y monitor asignado. Calcula de forma dinamica el precio
     * final para el socio segun su tarifa y adjunta un listener al boton para
     * procesar la reserva instantanea, guardar en disco y emitir recibo.
     *
     * @param a La {@link Actividades} sobre la cual se va a construir el
     * componente visual.
     * @return Un objeto {@link javax.swing.JPanel} estructurado listo para ser
     * inyectado en el grid.
     */
    private javax.swing.JPanel tarjetaActividad(Actividades a) {

        javax.swing.JPanel card = new javax.swing.JPanel();
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        card.setLayout(new java.awt.BorderLayout(0, 6));
        card.setPreferredSize(new java.awt.Dimension(260, 140));
        card.setMaximumSize(new java.awt.Dimension(260, 140));
        card.setMinimumSize(new java.awt.Dimension(260, 140));

        // Nombre
        javax.swing.JLabel lblNombre = new javax.swing.JLabel(a.getNombre());
        lblNombre.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 13));

        // Info día/hora/sala
        javax.swing.JLabel lblInfo = new javax.swing.JLabel(
                a.getHorario().getDia() + " " + a.getHorario().getInicio()
                + "–" + a.getHorario().getFin() + "  ·  " + a.getSala().getNombre());
        lblInfo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        lblInfo.setForeground(new java.awt.Color(153, 153, 153));

        // Monitor
        javax.swing.JLabel lblMonitor = new javax.swing.JLabel("Monitor: " + a.getMonitor().getNombre());
        lblMonitor.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        lblMonitor.setForeground(new java.awt.Color(153, 153, 153));

        // Panel textos (BoxLayout Y)
        javax.swing.JPanel textos = new javax.swing.JPanel();
        textos.setOpaque(false);
        textos.setLayout(new javax.swing.BoxLayout(textos, javax.swing.BoxLayout.Y_AXIS));
        textos.add(lblNombre);
        textos.add(javax.swing.Box.createVerticalStrut(2));
        textos.add(lblInfo);
        textos.add(javax.swing.Box.createVerticalStrut(2));
        textos.add(lblMonitor);
        textos.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 12, 6, 12));
        card.add(textos, java.awt.BorderLayout.CENTER);

        // Precio
        double precio = socio.calcularPrecio(a);
        String precioStr = (precio == 0) ? "Gratis" : String.format("%.2f €", precio);
        javax.swing.JLabel lblPrecio = new javax.swing.JLabel(precioStr);
        lblPrecio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblPrecio.setForeground(new java.awt.Color(0, 51, 255));

        // Botón reservar
        javax.swing.JButton btnReservar = new javax.swing.JButton("Reservar");
        btnReservar.setFocusPainted(false);

        btnReservar.addActionListener(e -> {
            boolean ok = gym.reservar(socio, a);

            if (ok) {
                gym.guardarDatos();
                gym.generarRecibo(gym.getUltimaReserva());
                configurarTablaReservas();

                javax.swing.JOptionPane.showMessageDialog(this,
                        "Reserva confirmada: " + a.getNombre(),
                        "Reserva realizada", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Sin aforo o ya tienes esta reserva.",
                        "Sin plazas", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        });

        javax.swing.JPanel bottom = new javax.swing.JPanel(new java.awt.BorderLayout());
        bottom.setOpaque(false);
        bottom.add(lblPrecio, java.awt.BorderLayout.WEST);
        bottom.add(btnReservar, java.awt.BorderLayout.EAST);

        card.add(textos, java.awt.BorderLayout.CENTER);
        card.add(bottom, java.awt.BorderLayout.SOUTH);
        return card;
    }

    /**
     * Limpia la rejilla de visualizacion y genera las tarjetas correspondientes
     * a la lista introducida. Invoca los metodos de invalidacion y repintado de
     * componentes Swing para obligar al entorno grafico a redibujar el layout
     * con las nuevas tarjetas cargadas.
     *
     * @param lista La coleccion ordenada de objetos {@link Actividades} que se
     * desean pintar.
     */
    private void rellenarGrid(java.util.ArrayList<Actividades> lista) {
        gridActividades.removeAll(); // Borra duplicados anteriores si los hubiera
        for (Actividades a : lista) {
            gridActividades.add(tarjetaActividad(a)); // Aquí se duplica la tarjeta por cada actividad
        }
        // Forzamos a Java a redibujar el contenedor con las nuevas tarjetas duplicadas
        gridActividades.revalidate();
        gridActividades.repaint();
    }

    /**
     * Analiza el catalogo de actividades del gimnasio para alimentar los
     * selectores de busqueda. Utiliza colecciones de tipo
     * {@link java.util.LinkedHashSet} para agrupar de forma automatica las
     * modalidades, nombres de monitores y dias sin generar duplicados incomodos
     * en la interfaz.
     */
    private void rellenarFiltros() {
        java.util.Set<String> tipos = new java.util.LinkedHashSet<>();
        tipos.add("Todos");
        for (Actividades a : gym.getActividades()) {
            tipos.add(a.getTipo().toString());  // ← Convierte enum a String
        }
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
                tipos.toArray(new String[0])));

        java.util.Set<String> monitores = new java.util.LinkedHashSet<>();
        monitores.add("Todos");
        for (Actividades a : gym.getActividades()) {
            monitores.add(a.getMonitor().getNombre());
        }
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(
                monitores.toArray(new String[0])));

        java.util.Set<String> dias = new java.util.LinkedHashSet<>();
        dias.add("Todos");
        for (Actividades a : gym.getActividades()) {
            dias.add(a.getHorario().getDia().toString());  // ← Convierte enum a String
        }
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(
                dias.toArray(new String[0])));
    }

    /**
     * Ejecuta una busqueda combinada cruzando los estados de los tres
     * desplegables de seleccion.
     * Evalua de manera simultanea los criterios de tipo, entrenador y dia de la
     * semana, absteniendose de filtrar aquellos parametros que mantengan el
     * comodin "Todos" activo.
     */
    private void aplicarFiltros() {
        String tipo = (String) jComboBox1.getSelectedItem();
        String monitor = (String) jComboBox2.getSelectedItem();
        String dia = (String) jComboBox3.getSelectedItem();

        java.util.ArrayList<Actividades> filtradas = new java.util.ArrayList<>();
        for (Actividades a : gym.getActividades()) {
            boolean okTipo = tipo.equals("Todos") || a.getTipo().toString().equals(tipo);
            boolean okMonitor = monitor.equals("Todos") || a.getMonitor().getNombre().equals(monitor);
            boolean okDia = dia.equals("Todos") || a.getHorario().getDia().toString().equals(dia);
            if (okTipo && okMonitor && okDia) {
                filtradas.add(a);
            }
        }
        rellenarGrid(filtradas);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        contenido = new javax.swing.JPanel();
        actividades = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        Reservas = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(220, 220, 220)));
        jPanel1.setPreferredSize(new java.awt.Dimension(210, 0));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel1.setText("Nombre del socio");

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel3.setText("Socio/Vip");

        jSeparator1.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 1, 1, 50));
        jSeparator1.setMaximumSize(new java.awt.Dimension(32767, 20));

        jButton1.setBackground(new java.awt.Color(245, 245, 245));
        jButton1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jButton1.setText("Actividades");
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.setMaximumSize(new java.awt.Dimension(32767, 38));
        jButton1.setMinimumSize(new java.awt.Dimension(75, 38));
        jButton1.setPreferredSize(new java.awt.Dimension(38, 38));
        jButton1.setRolloverEnabled(false);
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(245, 245, 245));
        jButton2.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jButton2.setText("Mis reservas");
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.setMaximumSize(new java.awt.Dimension(32767, 38));
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setBackground(new java.awt.Color(245, 245, 245));
        jButton3.setText("Mi perfil");
        jButton3.setBorderPainted(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setFocusPainted(false);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.setMaximumSize(new java.awt.Dimension(32767, 38));
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jSeparator2.setMaximumSize(new java.awt.Dimension(32767, 480));

        jButton4.setBackground(new java.awt.Color(245, 245, 245));
        jButton4.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jButton4.setText("Cerrar sesion");
        jButton4.setBorderPainted(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setFocusPainted(false);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.setMaximumSize(new java.awt.Dimension(32767, 38));
        jButton4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jLabel40.setFont(new java.awt.Font("Monotype Corsiva", 1, 36)); // NOI18N
        jLabel40.setText("JavaFit");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel40))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 333, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);
        jPanel1.getAccessibleContext().setAccessibleName("");

        contenido.setLayout(new java.awt.CardLayout());

        actividades.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel2.setText("Tipo:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jLabel4.setText("Monitor:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

        jLabel5.setText("Dia:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addActionListener(this::jComboBox3ActionPerformed);

        jButton5.setText("Filtrar");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jButton6.setText("Limpiar");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addGap(30, 30, 30))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(12, 12, 12))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel6.setText("Yoga Matutino");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("LUNES 09:00–10:00  ·  Sala Yoga");
        jPanel7.add(jLabel7);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Monitor: Carlos García");
        jPanel7.add(jLabel8);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 255));
        jLabel9.setText("Gratis");

        jButton7.setText("Reservar");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                .addComponent(jButton7))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(35, 35, 35)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel10.setText("Actividades Disponibles");

        javax.swing.GroupLayout actividadesLayout = new javax.swing.GroupLayout(actividades);
        actividades.setLayout(actividadesLayout);
        actividadesLayout.setHorizontalGroup(
            actividadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actividadesLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(actividadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(197, Short.MAX_VALUE))
        );
        actividadesLayout.setVerticalGroup(
            actividadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actividadesLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(314, Short.MAX_VALUE))
        );

        contenido.add(actividades, "card2");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Mis reservas");

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Actividades", "Dia", "Horario", "Sala", "Precio", "Fecha"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jButton8.setText("Cancelar reserva seleccionada");
        jButton8.addActionListener(this::jButton8ActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton8)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 878, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addGap(16, 16, 16))
        );

        contenido.add(jPanel4, "card3");

        Reservas.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setText("Nombre Completo:");

        jLabel13.setText("Correo electronico:");

        jLabel14.setText("Contraseña");

        jLabel15.setText("Direccion");

        jLabel16.setText("Tarjeta");

        jLabel17.setText("Telefono");

        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jTextField2.addActionListener(this::jTextField2ActionPerformed);

        jTextField5.addActionListener(this::jTextField5ActionPerformed);

        jButton9.setText("Guardar cambios");
        jButton9.addActionListener(this::jButton9ActionPerformed);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setText("Mi Perfil");

        javax.swing.GroupLayout ReservasLayout = new javax.swing.GroupLayout(Reservas);
        Reservas.setLayout(ReservasLayout);
        ReservasLayout.setHorizontalGroup(
            ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReservasLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addGroup(ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ReservasLayout.createSequentialGroup()
                        .addGroup(ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addGap(51, 51, 51)
                        .addGroup(ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                .addComponent(jTextField6))
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ReservasLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(109, 109, 109)))
                .addGap(0, 435, Short.MAX_VALUE))
        );
        ReservasLayout.setVerticalGroup(
            ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReservasLayout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addGroup(ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addContainerGap(188, Short.MAX_VALUE))
        );

        contenido.add(Reservas, "card4");

        getContentPane().add(contenido, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cambiarTarjeta("card2");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        aplicarFiltros();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        dispose();
        java.awt.EventQueue.invokeLater(() -> new Login2(gym).setVisible(true));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) Tabla1.getModel();

        int fila = Tabla1.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Selecciona una reserva primero.",
                    "Sin selección", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Cancelar la reserva de " + modelo.getValueAt(fila, 0) + "?",
                "Confirmar cancelación", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            Reserva reserva = gym.getReservasDeSocio(socio).get(fila);
            gym.cancelarReserva(reserva);
            gym.guardarDatos();
            modelo.removeRow(fila);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        try {
            // Validar contraseña no vacía
            String clave = jTextField3.getText().trim();
            if (clave.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "La contraseña no puede estar vacía.",
                        "Error", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (clave.length() < 6) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "La contraseña debe tener al menos 6 caracteres.",
                        "Error", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar teléfono
            String telefonoStr = jTextField6.getText().trim();
            int telefono = Integer.parseInt(telefonoStr);
            if (String.valueOf(telefono).length() != 9) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "El teléfono debe tener 9 dígitos.",
                        "Error", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            socio.setClave(clave);
            socio.setDireccion(jTextField4.getText().trim());
            socio.setTelefono(telefono);
            gym.guardarDatos();

            javax.swing.JOptionPane.showMessageDialog(this,
                    "Cambios guardados correctamente.",
                    "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "El teléfono debe contener solo números.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error al guardar: " + e.getMessage(),
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        cambiarTarjeta("card4");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cambiarTarjeta("card3");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        aplicarFiltros();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        aplicarFiltros();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        aplicarFiltros();
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        rellenarGrid(gym.getActividades());
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

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
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Reservas;
    private javax.swing.JTable Tabla1;
    private javax.swing.JPanel actividades;
    private javax.swing.JPanel contenido;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
