/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelAdmin extends JFrame {

    private GymManager gym;
    private JPanel contenido;
    private CardLayout cardLayout;

    public PanelAdmin(GymManager gym) {
        this.gym = gym;
        construirUI();
    }

    private void construirUI() {
        setTitle("JavaFit — Panel Administrador");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Sidebar ───────────────────────────────────────────
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(245, 245, 245));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));

        JPanel perfil = new JPanel();
        perfil.setOpaque(false);
        perfil.setLayout(new BoxLayout(perfil, BoxLayout.Y_AXIS));
        perfil.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        perfil.setAlignmentX(Component.LEFT_ALIGNMENT);
        perfil.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel avatar = new JLabel("AD", SwingConstants.CENTER);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 14));
        avatar.setForeground(new Color(27, 80, 11));
        avatar.setBackground(new Color(192, 221, 151));
        avatar.setOpaque(true);
        avatar.setPreferredSize(new Dimension(40, 40));
        avatar.setMaximumSize(new Dimension(40, 40));

        JLabel lblNombre = new JLabel("Administrador");
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTipo = new JLabel("Admin");
        lblTipo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblTipo.setForeground(new Color(59, 109, 17));
        lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);

        perfil.add(avatar);
        perfil.add(Box.createVerticalStrut(8));
        perfil.add(lblNombre);
        perfil.add(Box.createVerticalStrut(2));
        perfil.add(lblTipo);

        sidebar.add(perfil);
        sidebar.add(new JSeparator());
        sidebar.add(Box.createVerticalStrut(8));

        JButton btnDashboard   = btnSidebar("📊  Dashboard");
        JButton btnSocios      = btnSidebar("👥  Socios");
        JButton btnActividades = btnSidebar("🏋  Actividades");
        JButton btnReservas    = btnSidebar("📋  Reservas");
        JButton btnSalir       = btnSidebar("← Cerrar sesión");

        sidebar.add(btnDashboard);
        sidebar.add(btnSocios);
        sidebar.add(btnActividades);
        sidebar.add(btnReservas);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(new JSeparator());
        sidebar.add(btnSalir);
        sidebar.add(Box.createVerticalStrut(8));

        // ── Contenido ─────────────────────────────────────────
        cardLayout = new CardLayout();
        contenido  = new JPanel(cardLayout);
        contenido.add(panelDashboard(),   "dashboard");
        contenido.add(panelSocios(),      "socios");
        contenido.add(panelActividades(), "actividades");
        contenido.add(panelReservas(),    "reservas");

        btnDashboard.addActionListener(e   -> cardLayout.show(contenido, "dashboard"));
        btnSocios.addActionListener(e      -> cardLayout.show(contenido, "socios"));
        btnActividades.addActionListener(e -> cardLayout.show(contenido, "actividades"));
        btnReservas.addActionListener(e    -> cardLayout.show(contenido, "reservas"));
        btnSalir.addActionListener(e -> {
            dispose();
            new Login(gym).setVisible(true);
        });

        add(sidebar,   BorderLayout.WEST);
        add(contenido, BorderLayout.CENTER);
    }

    // ── Dashboard ─────────────────────────────────────────────
    private JPanel panelDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Dashboard");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 16));
        centro.setBackground(Color.WHITE);

        // Tarjetas de estadísticas
        JPanel stats = new JPanel(new GridLayout(1, 3, 12, 0));
        stats.setBackground(Color.WHITE);
        stats.add(statCard("Socios",      String.valueOf(gym.getSocios().size()),      new Color(24, 95, 165)));
        stats.add(statCard("Actividades", String.valueOf(gym.getActividades().size()),  new Color(59, 109, 17)));
        stats.add(statCard("Reservas",    String.valueOf(gym.getReservas().size()),     new Color(153, 60, 29)));
        centro.add(stats, BorderLayout.NORTH);

        // Tabla últimas reservas
        JLabel lblReservas = new JLabel("Últimas reservas");
        lblReservas.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblReservas.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        String[] cols = {"Socio", "Actividad", "Tipo", "Precio final"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Reserva r : gym.getReservasOrdenadasPorFecha()) {
            modelo.addRow(new Object[]{
                r.getSocio().getNombre(),
                r.getActividad().getNombre(),
                r.getActividad().tipoCategoria(),
                String.format("%.2f €", r.getPreciofinal())
            });
        }
        JTable tabla = tablaEstilizada(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JPanel bloque = new JPanel(new BorderLayout());
        bloque.setBackground(Color.WHITE);
        bloque.add(lblReservas, BorderLayout.NORTH);
        bloque.add(scroll, BorderLayout.CENTER);
        centro.add(bloque, BorderLayout.CENTER);

        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    // ── Socios ────────────────────────────────────────────────
    private JPanel panelSocios() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Socios");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panel.add(titulo, BorderLayout.NORTH);

        String[] cols = {"Nombre", "Correo", "Teléfono", "Tipo", "Dirección"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Socios s : gym.getSocios()) {
            modelo.addRow(new Object[]{
                s.getNombre(), s.getCorreo(), s.getTelefono(),
                (s instanceof Vip) ? "VIP" : "Básico",
                s.getDireccion()
            });
        }

        JTable tabla = tablaEstilizada(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scroll, BorderLayout.CENTER);

        // Botón añadir socio
        JButton btnAñadir = new JButton("+ Añadir socio");
        btnAñadir.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnAñadir.setFocusPainted(false);
        btnAñadir.addActionListener(e -> dialogoAñadirSocio(modelo));

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south.setBackground(Color.WHITE);
        south.add(btnAñadir);
        panel.add(south, BorderLayout.SOUTH);

        return panel;
    }

    // ── Actividades ───────────────────────────────────────────
    private JPanel panelActividades() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Actividades");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panel.add(titulo, BorderLayout.NORTH);

        String[] cols = {"Nombre", "Tipo", "Categoría", "Sala", "Día", "Horario", "Monitor", "Precio"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Actividades a : gym.getActividades()) {
            modelo.addRow(new Object[]{
                a.getNombre(), a.getTipo(), a.tipoCategoria(),
                a.getSala().getNombre(),
                a.getHorario().getDia(),
                a.getHorario().getInicio() + "–" + a.getHorario().getFin(),
                a.getMonitor().getNombre(),
                a.getPrecio() == 0 ? "Gratis" : String.format("%.2f €", a.getPrecio())
            });
        }

        JTable tabla = tablaEstilizada(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scroll, BorderLayout.CENTER);

        // Botón eliminar actividad seleccionada
        JButton btnEliminar = new JButton("Eliminar actividad seleccionada");
        btnEliminar.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnEliminar.setForeground(new Color(153, 60, 29));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona una actividad."); return; }
            gym.eliminarActividad(gym.getActividades().get(fila));
            modelo.removeRow(fila);
        });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south.setBackground(Color.WHITE);
        south.add(btnEliminar);
        panel.add(south, BorderLayout.SOUTH);

        return panel;
    }

    // ── Reservas ──────────────────────────────────────────────
    private JPanel panelReservas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Todas las reservas");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panel.add(titulo, BorderLayout.NORTH);

        String[] cols = {"Socio", "Actividad", "Día", "Horario", "Precio", "Fecha"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Reserva r : gym.getReservasOrdenadasPorFecha()) {
            modelo.addRow(new Object[]{
                r.getSocio().getNombre(),
                r.getActividad().getNombre(),
                r.getActividad().getHorario().getDia(),
                r.getActividad().getHorario().getInicio() + "–" + r.getActividad().getHorario().getFin(),
                String.format("%.2f €", r.getPreciofinal()),
                r.getFecha()
            });
        }

        JTable tabla = tablaEstilizada(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ── Diálogo añadir socio ──────────────────────────────────
    private void dialogoAñadirSocio(DefaultTableModel modelo) {
        JDialog dlg = new JDialog(this, "Añadir socio", true);
        dlg.setSize(360, 380);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JTextField fNombre    = new JTextField();
        JTextField fCorreo    = new JTextField();
        JTextField fClave     = new JTextField();
        JTextField fDireccion = new JTextField();
        JTextField fTarjeta   = new JTextField();
        JTextField fTelefono  = new JTextField();
        JComboBox<String> fTipo = new JComboBox<>(new String[]{"Básico", "VIP"});

        form.add(new JLabel("Nombre:"));    form.add(fNombre);
        form.add(new JLabel("Correo:"));    form.add(fCorreo);
        form.add(new JLabel("Clave:"));     form.add(fClave);
        form.add(new JLabel("Dirección:")); form.add(fDireccion);
        form.add(new JLabel("Tarjeta:"));   form.add(fTarjeta);
        form.add(new JLabel("Teléfono:"));  form.add(fTelefono);
        form.add(new JLabel("Tipo:"));      form.add(fTipo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(24, 95, 165));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.addActionListener(e -> {
            try {
                String nombre    = fNombre.getText().trim();
                String correo    = fCorreo.getText().trim();
                String clave     = fClave.getText().trim();
                String direccion = fDireccion.getText().trim();
                String tarjeta   = fTarjeta.getText().trim();
                int telefono     = Integer.parseInt(fTelefono.getText().trim());
                String tipoSel   = (String) fTipo.getSelectedItem();

                Socios nuevo = "VIP".equals(tipoSel)
                    ? new Vip(nombre, direccion, tarjeta, telefono, correo, clave)
                    : new Basico(nombre, direccion, tarjeta, telefono, correo, clave);

                gym.añadirSocio(nuevo);
                modelo.addRow(new Object[]{nombre, correo, telefono, tipoSel, direccion});
                dlg.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dlg, "El teléfono debe ser numérico.");
            }
        });

        JPanel south = new JPanel();
        south.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));
        south.add(btnGuardar);

        dlg.add(form,  BorderLayout.CENTER);
        dlg.add(south, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    // ── Helpers ───────────────────────────────────────────────
    private JPanel statCard(String label, String valor, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblValor.setForeground(color);

        JLabel lblLabel = new JLabel(label, SwingConstants.CENTER);
        lblLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblLabel.setForeground(Color.GRAY);

        card.add(lblValor, BorderLayout.CENTER);
        card.add(lblLabel, BorderLayout.SOUTH);
        return card;
    }

    private JTable tablaEstilizada(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setSelectionBackground(new Color(230, 241, 251));
        return tabla;
    }

    private JButton btnSidebar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBackground(new Color(245, 245, 245));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
