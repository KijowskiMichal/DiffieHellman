package MainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class Windows
{
    private JFrame frame;

    public void initialize()
    {
        frame = new JFrame("DiffiHellman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setLayout(new GridLayout(2, 2));

        frame.setVisible(true);
    }

    public void firstScreen()
    {
        frame.setLayout(new GridLayout(2, 2));
        this.clear();
        JButton button1 = new JButton("Server");
        JButton button2 = new JButton("Klient");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setServerKlient(1);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setServerKlient(2);
            }
        });

        frame.add(button1);
        frame.add(button2);
        frame.setVisible(true);
    }

    public void secondScreen()
    {
        frame.setLayout(new GridLayout(2, 2));
        this.clear();
        JButton button1 = new JButton("AES");
        JButton button2 = new JButton("DES");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setAesDes(1);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setAesDes(2);
            }
        });

        frame.add(button1);
        frame.add(button2);
        frame.setVisible(true);
    }

    public void thirdScreen(String message)
    {
        frame.setLayout(new GridLayout(1, 1));
        this.clear();
        JTextArea jTextArea = new JTextArea(message);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);

        frame.add(jTextArea);
        frame.setVisible(true);
    }

    public void fourthScreen()
    {
        frame.setLayout(new GridLayout(3, 3));
        this.clear();
        JTextArea jTextArea = new JTextArea("Podaj port na którym ma nasłuchiwać serwer:");
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);

        JTextField jTextField = new JTextField();

        JButton jButton = new JButton("Zatwierdź");

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setPort(Integer.valueOf(jTextField.getText()));
            }
        });

        frame.add(jTextArea);
        frame.add(jTextField);
        frame.add(jButton);
        frame.setVisible(true);
    }

    public void fivethScreen(String message, String status)
    {
        frame.setLayout(new GridLayout(2, 2));
        this.clear();
        JTextArea jTextArea = new JTextArea(message);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        JScrollBar vertical = jScrollPane.getVerticalScrollBar();
        vertical.setValue( vertical.getMaximum() );
        frame.add(jScrollPane);
        frame.add(new JLabel("<html>"+status+"</html>", SwingConstants.CENTER));
        frame.setVisible(true);
    }

    public void sixthScreen()
    {
        frame.setLayout(new GridLayout(4, 4));
        this.clear();
        JTextArea jTextArea = new JTextArea("Podaj adres (pierwsze pole) oraz port (drugie pole) na którym oczekuje serwer:");
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);

        JTextField jTextField = new JTextField();
        JTextField jTextField2 = new JTextField();

        JButton jButton = new JButton("Zatwierdź");

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setAdres(jTextField.getText());
                Main.setPort(Integer.valueOf(jTextField2.getText()));
            }
        });

        frame.add(jTextArea);
        frame.add(jTextField);
        frame.add(jTextField2);
        frame.add(jButton);
        frame.setVisible(true);
    }

    public void seventhScreen()
    {
        frame.setLayout(new GridLayout(2, 2));
        this.clear();
        JTextArea jTextArea = new JTextArea("Wybierz plik do przesłania:");
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setPath(jFileChooser.getSelectedFile().getAbsolutePath());
                Main.setFile(jFileChooser.getSelectedFile().getName());
            }
        });

        frame.add(jTextArea);
        frame.add(jFileChooser);
        frame.setVisible(true);
    }

    public void eighthScreen()
    {
        frame.setLayout(new GridLayout(3, 3));
        this.clear();
        JTextArea jTextArea = new JTextArea("Klient chce przesłać Ci plik: "+Main.getFile()+"\nZaakceptuj aby odebrać lub odrzuć aby zakończyć program.");
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);

        JButton jButton1 = new JButton("Zaakceptuj");
        JButton jButton2 = new JButton("Odrzuć");

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setAcceptReject(1);
            }
        });

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setAcceptReject(2);
            }
        });

        frame.add(jTextArea);
        frame.add(jButton1);
        frame.add(jButton2);
        frame.setVisible(true);
    }

    public void clear()
    {
        frame.getContentPane().removeAll();
        frame.repaint();
    }
}
