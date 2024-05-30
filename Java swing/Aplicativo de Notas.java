import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AplicativoNotas extends JFrame implements ActionListener {
    private JTextArea areaTexto;
    private JButton botaoSalvar;
    private JButton botaoAbrir;

    public AplicativoNotas() {
        setTitle("Aplicativo de Notas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        JMenuBar menuBar = new JMenuBar();
        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenuItem abrirItem = new JMenuItem("Abrir");
        abrirItem.addActionListener(this);
        JMenuItem salvarItem = new JMenuItem("Salvar");
        salvarItem.addActionListener(this);
        arquivoMenu.add(abrirItem);
        arquivoMenu.add(salvarItem);
        menuBar.add(arquivoMenu);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());

        areaTexto = new JTextArea();
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(this);
        botaoAbrir = new JButton("Abrir");
        botaoAbrir.addActionListener(this);
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoAbrir);
        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Salvar")) {
            salvarArquivo();
        } else if (e.getActionCommand().equals("Abrir")) {
            abrirArquivo();
        }
    }

    private void salvarArquivo() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                String texto = areaTexto.getText();
                String caminhoArquivo = fileChooser.getSelectedFile().getAbsolutePath();
                ArquivoUtil.salvarTexto(texto, caminhoArquivo);
                JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirArquivo() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                String caminhoArquivo = fileChooser.getSelectedFile().getAbsolutePath();
                String texto = ArquivoUtil.abrirTexto(caminhoArquivo);
                areaTexto.setText(texto);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir o arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AplicativoNotas::new);
    }
}

class ArquivoUtil {
    public static void salvarTexto(String texto, String caminhoArquivo) throws Exception {
        java.io.FileWriter writer = new java.io.FileWriter(caminhoArquivo);
        writer.write(texto);
        writer.close();
    }

    public static String abrirTexto(String caminhoArquivo) throws Exception {
        java.io.FileReader reader = new java.io.FileReader(caminhoArquivo);
        StringBuilder sb = new StringBuilder();
        int caractere;
        while ((caractere = reader.read()) != -1) {
            sb.append((char) caractere);
        }
        reader.close();
        return sb.toString();
    }
}
