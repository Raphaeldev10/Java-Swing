import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraSwing extends JFrame implements ActionListener {
    private JTextField display;
    private JButton[] botoesNumeros;
    private JButton[] botoesOperadores;
    private JButton botaoLimpar;
    private JButton botaoResultado;
    private double primeiroNumero;
    private String operador;

    public CalculadoraSwing() {
        configurarInterface();
        inicializarComponentes();
    }

    private void configurarInterface() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        criarDisplay();
        criarBotoes();
        setVisible(true);
    }

   
    private void criarDisplay() {
        display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);
    }

    private void criarBotoes() {
        criarBotoesNumeros();
        criarBotoesOperadores();
        criarBotaoLimpar();
        criarBotaoResultado();
    }

    private void criarBotoesNumeros() {
        JPanel painelBotoes = new JPanel(new GridLayout(4, 3));
        botoesNumeros = new JButton[10];
        for (int i = 0; i < 10; i++) {
            botoesNumeros[i] = new JButton(Integer.toString(i));
            botoesNumeros[i].addActionListener(this);
            painelBotoes.add(botoesNumeros[i]);
        }
        add(painelBotoes, BorderLayout.CENTER);
    }

    private void criarBotoesOperadores() {
        JPanel painelOperadores = new JPanel(new GridLayout(4, 1));
        botoesOperadores = new JButton[4];
        String[] operadores = {"+", "-", "*", "/"};
        for (int i = 0; i < 4; i++) {
            botoesOperadores[i] = new JButton(operadores[i]);
            botoesOperadores[i].addActionListener(this);
            painelOperadores.add(botoesOperadores[i]);
        }
        add(painelOperadores, BorderLayout.EAST);
    }

    private void criarBotaoLimpar() {
        botaoLimpar = new JButton("C");
        botaoLimpar.addActionListener(this);
        add(botaoLimpar, BorderLayout.WEST);
    }

    private void criarBotaoResultado() {
        botaoResultado = new JButton("=");
        botaoResultado.addActionListener(this);
        add(botaoResultado, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("C")) {
            limparDisplay();
        } else if (comando.equals("=")) {
            calcularResultado();
        } else {
            adicionarAoDisplay(comando);
        }
    }

    private void limparDisplay() {
        display.setText("");
    }

    private void adicionarAoDisplay(String texto) {
        display.setText(display.getText() + texto);
    }

    private void calcularResultado() {
        try {
            double segundoNumero = Double.parseDouble(display.getText());
            double resultado = 0;
            switch (operador) {
                case "+":
                    resultado = primeiroNumero + segundoNumero;
                    break;
                case "-":
                    resultado = primeiroNumero - segundoNumero;
                    break;
                case "*":
                    resultado = primeiroNumero * segundoNumero;
                    break;
                case "/":
                    if (segundoNumero != 0) {
                        resultado = primeiroNumero / segundoNumero;
                    } else {
                        throw new ArithmeticException("Divisão por zero!");
                    }
                    break;
            }
            display.setText(Double.toString(resultado));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Entrada inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(this, "Não é possível dividir por zero!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraSwing::new);
    }
}
