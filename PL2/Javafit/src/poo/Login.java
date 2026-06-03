/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

import java.awt.*;
import javax.swing.*;

public class Login extends JFrame {

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(Login.class.getName());

    private JTextField campCorreo;
    private JPasswordField campClave;
    private JButton btnEntrar;
    private JLabel lblError;
    private GymManager gym;

    public Login(GymManager gym) {
        this.gym = gym;
        construirUI();
    }

    public Login() {
        this(new GymManager());
    }

    private void construirUI() {
        setTitle("JavaFit — Acceso");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelLogo = new JPanel(new GridBagLayout());
        panelLogo.setBackground(new Color(24, 95, 165));
        panelLogo.setPreferredSize(new Dimension(250, 0));

        JPanel bloqueTexto = new JPanel();
        bloqueTexto.setOpaque(false);
        bloqueTexto.setLayout(new BoxLayout(bloqueTexto, BoxLayout.Y_AXIS));

        JLabel icono = new JLabel("💪", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("JavaFit");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(new Color(230, 241, 251));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Gestión de gimnasio");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(181, 212, 244));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        bloqueTexto.add(icono);
        bloqueTexto.add(Box.createVerticalStrut(10));
        bloqueTexto.add(titulo);
        bloqueTexto.add(Box.createVerticalStrut(4));
        bloqueTexto.add(subtitulo);
        panelLogo.add(bloqueTexto);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 32, 20, 32));

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("Iniciar sesión");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Introduce tus credenciales");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        campCorreo = new JTextField();
        campCorreo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        campCorreo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        campClave = new JPasswordField();
        campClave.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        campClave.setFont(new Font("SansSerif", Font.PLAIN, 14));

        btnEntrar = new JButton("Entrar");
        btnEntrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnEntrar.setBackground(new Color(24, 95, 165));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblError = new JLabel(" ");
        lblError.setForeground(new Color(153, 60, 29));
        lblError.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblAdmin = new JLabel("Admin: admin@javafit.com / admin");
        lblAdmin.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblAdmin.setForeground(Color.LIGHT_GRAY);
        lblAdmin.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(lblTitulo);
        form.add(Box.createVerticalStrut(4));
        form.add(lblSub);
        form.add(Box.createVerticalStrut(18));
        form.add(new JLabel("Correo electrónico"));
        form.add(Box.createVerticalStrut(4));
        form.add(campCorreo);
        form.add(Box.createVerticalStrut(12));
        form.add(new JLabel("Contraseña"));
        form.add(Box.createVerticalStrut(4));
        form.add(campClave);
        form.add(Box.createVerticalStrut(16));
        form.add(btnEntrar);
        form.add(Box.createVerticalStrut(8));
        form.add(lblError);
        form.add(Box.createVerticalStrut(12));
        form.add(lblAdmin);

        panelForm.add(form);

        btnEntrar.addActionListener(e -> intentarLogin());
        campClave.addActionListener(e -> intentarLogin());

        add(panelLogo, BorderLayout.WEST);
        add(panelForm, BorderLayout.CENTER);
    }

    private void intentarLogin() {
        String correo = campCorreo.getText().trim();
        String clave  = new String(campClave.getPassword());

        Usuarios usuario = gym.login(correo, clave);

        if (usuario == null) {
            lblError.setText("Correo o contraseña incorrectos.");
            campClave.setText("");
            return;
        }

        lblError.setText(" ");
        dispose();

        if (usuario instanceof Administradores) {
            new PanelAdmin(gym).setVisible(true);
        } else {
            new PanelSocio(gym, (Socios) usuario).setVisible(true);
        }
    }

    public static void main(String[] args) {
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
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }
}