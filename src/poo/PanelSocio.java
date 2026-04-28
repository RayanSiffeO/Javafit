/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelSocio extends JFrame {

    private GymManager gym;
    private Socios socio;
    private JPanel contenido;
    private CardLayout cardLayout;

    public PanelSocio(GymManager gym, Socios socio) {
        this.gym = gym;
        this.socio = socio;
        construirUI();
    }

    private void construirUI() {
        setTitle("JavaFit — " + socio.getNombre());
        setSize(900, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Sidebar ───────────────────────────────────────────
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(245, 245, 245));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));

        // Perfil
        JPanel perfil = new JPanel();
        perfil.setOpaque(false);
        perfil.setLayout(new BoxLayout(perfil, BoxLayout.Y_AXIS));
        perfil.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        perfil.setAlignmentX(Component.LEFT_ALIGNMENT);
        perfil.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        String iniciales = iniciales(socio.getNombre());
        JLabel avatar = new JLabel(iniciales, SwingConstants.CENTER);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 14));
        avatar.setForeground(new Color(12, 68, 124));
        avatar.setBackground(new Color(181, 212, 244));
        avatar.setOpaque(true);
        avatar.setPreferredSize(new Dimension(40, 40));
        avatar.setMaximumSize(new Dimension(40, 40));
        avatar.setBorder(BorderFactory.createEmptyBorder());

        JLabel lblNombre = new JLabel(socio.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        String tipo = (socio instanceof Vip) ? "VIP" : "Básico";
        JLabel lblTipo = new JLabel(tipo);
        lblTipo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblTipo.setForeground(new Color(24, 95, 165));
        lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);

        perfil.add(avatar);
        perfil.add(Box.createVerticalStrut(8));
        perfil.add(lblNombre);
        perfil.add(Box.createVerticalStrut(2));
        perfil.add(lblTipo);

        sidebar.add(perfil);
        sidebar.add(new JSeparator());
        sidebar.add(Box.createVerticalStrut(8));

        JButton btnActividades = btnSidebar("🏋  Actividades");
        JButton btnReservas    = btnSidebar("📅  Mis reservas");
        JButton btnSalir       = btnSidebar("← Cerrar sesión");

        sidebar.add(btnActividades);
        sidebar.add(btnReservas);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(new JSeparator());
        sidebar.add(btnSalir);
        sidebar.add(Box.createVerticalStrut(8));

        // ── Contenido con CardLayout ──────────────────────────
        cardLayout = new CardLayout();
        contenido  = new JPanel(cardLayout);
        contenido.add(panelActividades(), "actividades");
        contenido.add(panelReservas(),    "reservas");

        btnActividades.addActionListener(e -> cardLayout.show(contenido, "actividades"));
        btnReservas.addActionListener(e -> {
            contenido.remove(1);
            contenido.add(panelReservas(), "reservas", 1);
            cardLayout.show(contenido, "reservas");
        });
        btnSalir.addActionListener(e -> {
            dispose();
            new Login(gym).setVisible(true);
        });

        add(sidebar,   BorderLayout.WEST);
        add(contenido, BorderLayout.CENTER);
    }

    // ── Panel de actividades ──────────────────────────────────
    private JPanel panelActividades() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Actividades disponibles");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 2, 10, 10));
        grid.setBackground(Color.WHITE);

        for (Actividades a : gym.getActividades()) {
            grid.add(tarjetaActividad(a));
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel tarjetaActividad(Actividades a) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        card.setBackground(Color.WHITE);

        JLabel nombre = new JLabel(a.getNombre());
        nombre.setFont(new Font("SansSerif", Font.BOLD, 13));

        String info = a.getHorario().getDia() + "  " +
                      a.getHorario().getInicio() + "–" + a.getHorario().getFin() +
                      "  ·  " + a.getSala().getNombre();
        JLabel detalles = new JLabel(info);
        detalles.setFont(new Font("SansSerif", Font.PLAIN, 11));
        detalles.setForeground(Color.GRAY);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(nombre);
        textos.add(Box.createVerticalStrut(3));
        textos.add(detalles);

        double precio = socio.calcularPrecio(a);
        String precioStr = (precio == 0) ? "Gratis" : String.format("%.2f €", precio);
        JLabel lblPrecio = new JLabel(precioStr);
        lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblPrecio.setForeground(new Color(24, 95, 165));

        JButton btnReservar = new JButton("Reservar");
        btnReservar.setFont(new Font("SansSerif", Font.PLAIN, 11));
        btnReservar.setFocusPainted(false);
        btnReservar.addActionListener(e -> {
            boolean ok = gym.reservar(socio, a);
            if (ok) {
                JOptionPane.showMessageDialog(this,
                    "Reserva confirmada: " + a.getNombre() + "\nPrecio: " + precioStr,
                    "Reserva realizada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "No hay aforo disponible para esta actividad.",
                    "Sin plazas", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.add(lblPrecio,  BorderLayout.WEST);
        bottom.add(btnReservar, BorderLayout.EAST);

        card.add(textos, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);
        return card;
    }

    // ── Panel de reservas ─────────────────────────────────────
    private JPanel panelReservas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Mis reservas");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panel.add(titulo, BorderLayout.NORTH);

        String[] cols = {"Actividad", "Día", "Horario", "Sala", "Precio"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        for (Reserva r : gym.getReservasDeSocio(socio)) {
            Actividades a = r.getActividad();
            modelo.addRow(new Object[]{
                a.getNombre(),
                a.getHorario().getDia(),
                a.getHorario().getInicio() + "–" + a.getHorario().getFin(),
                a.getSala().getNombre(),
                String.format("%.2f €", r.getPreciofinal())
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setSelectionBackground(new Color(230, 241, 251));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scroll, BorderLayout.CENTER);

        // Botón cancelar reserva seleccionada
        JButton btnCancelar = new JButton("Cancelar reserva seleccionada");
        btnCancelar.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnCancelar.setForeground(new Color(153, 60, 29));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una reserva primero.");
                return;
            }
            ArrayList<Reserva> misReservas = gym.getReservasDeSocio(socio);
            gym.cancelarReserva(misReservas.get(fila));
            modelo.removeRow(fila);
        });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(Color.WHITE);
        south.add(btnCancelar);
        panel.add(south, BorderLayout.SOUTH);

        return panel;
    }

    // ── Helpers ───────────────────────────────────────────────
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

    private String iniciales(String nombre) {
        String[] partes = nombre.trim().split(" ");
        if (partes.length >= 2) return ("" + partes[0].charAt(0) + partes[1].charAt(0)).toUpperCase();
        return nombre.substring(0, Math.min(2, nombre.length())).toUpperCase();
    }
}
