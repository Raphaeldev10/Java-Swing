import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoDaVelha extends JFrame implements ActionListener {
    private JButton[][] botoes;
    private boolean turnoX;
    private JLabel lblStatus;

    public JogoDaVelha() {
        setTitle("Jogo da Velha");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelTabuleiro = new JPanel(new GridLayout(3, 3));
        botoes = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j] = new JButton();
                botoes[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                botoes[i][j].addActionListener(this);
                painelTabuleiro.add(botoes[i][j]);
            }
        }

        lblStatus = new JLabel("Vez do jogador X");
        add(lblStatus, BorderLayout.NORTH);
        add(painelTabuleiro, BorderLayout.CENTER);

        turnoX = true;

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botaoClicado = (JButton) e.getSource();
        if (botaoClicado.getText().isEmpty()) {
            if (turnoX) {
                botaoClicado.setText("X");
                botaoClicado.setForeground(Color.BLUE);
                lblStatus.setText("Vez do jogador O");
            } else {
                botaoClicado.setText("O");
                botaoClicado.setForeground(Color.RED);
                lblStatus.setText("Vez do jogador X");
            }
            turnoX = !turnoX;
            if (verificarVencedor()) {
                lblStatus.setText("Jogador " + (turnoX ? "O" : "X") + " venceu!");
                desabilitarBotoes();
            } else if (verificarEmpate()) {
                lblStatus.setText("Empate!");
                desabilitarBotoes();
            }
        }
    }

    private boolean verificarVencedor() {
        // Lógica para verificar vencedor
    }

    private boolean verificarEmpate() {
        // Lógica para verificar empate
    }

    private void desabilitarBotoes() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j].setEnabled(false);
            }
        }
    }

    private void reiniciarJogo() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j].setText("");
                botoes[i][j].setEnabled(true);
            }
        }
        turnoX = true;
        lblStatus.setText("Vez do jogador X");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JogoDaVelha::new);
    }
}
