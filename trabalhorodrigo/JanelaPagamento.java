import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class JanelaPagamento extends JFrame {
    private JTextField campoCartaoNumero;
    private JPasswordField campoCartaoCVV;
    private JTextField campoCartaoValidade;
    private JTextArea campoPix;
    private JTextArea campoBoleto;
    private JPanel painelDados;
    private JComboBox<String> comboPagamento;

    public JanelaPagamento(Carrinho carrinho) {
        setTitle("Pagamento");
        setSize(400, 350); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPagamento = new JPanel();
        painelPagamento.setLayout(new GridLayout(6, 1));

        String[] opcoes = {"Cartão de Crédito", "Cartão de Débito", "Pix", "Boleto"};
        comboPagamento = new JComboBox<>(opcoes);
        painelPagamento.add(new JLabel("Escolha o método de pagamento:"));
        painelPagamento.add(comboPagamento);

        painelDados = new JPanel();
        painelDados.setLayout(new GridLayout(6, 1));
        campoCartaoNumero = new JTextField(16);
        campoCartaoCVV = new JPasswordField(3);
        campoCartaoValidade = new JTextField(5);
        campoPix = new JTextArea(1, 20);
        campoBoleto = new JTextArea(1, 20);

        comboPagamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarPainelDados();
            }
        });

        atualizarPainelDados(); 

        JButton botaoEfetuarPagamento = new JButton("Efetuar Pagamento");
        botaoEfetuarPagamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarDados()) {
                    String metodo = (String) comboPagamento.getSelectedItem();
                    String mensagem = "Compra realizada com sucesso!\n";
                    mensagem += "Itens no carrinho:\n";

                    for (Item item : carrinho.getItens()) {
                        mensagem += item.getNome() + " - R$ " + String.format("%.2f", item.getPreco()) + "\n";
                    }

                    mensagem += "Total: R$ " + String.format("%.2f", carrinho.calcularTotal()) + "\n";
                    mensagem += "Método de Pagamento: " + metodo + "\n";

                    switch (metodo) {
                        case "Cartão de Crédito":
                        case "Cartão de Débito":
                            mensagem += "Número do Cartão: " + campoCartaoNumero.getText() + "\n";
                            mensagem += "CVV: " + new String(campoCartaoCVV.getPassword()) + "\n";
                            mensagem += "Data de Validade: " + campoCartaoValidade.getText() + "\n";
                            break;
                        case "Pix":
                            mensagem += "Chave Pix: " + campoPix.getText() + "\n";
                            break;
                        case "Boleto":
                            mensagem += "Código de Boleto: " + campoBoleto.getText() + "\n";
                            break;
                    }

                    JOptionPane.showMessageDialog(null, mensagem);
                    dispose(); 
                }
            }
        });

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(painelPagamento, BorderLayout.NORTH);
        painelPrincipal.add(painelDados, BorderLayout.CENTER);
        painelPrincipal.add(botaoEfetuarPagamento, BorderLayout.SOUTH);

        JPanel rodape = new JPanel(new BorderLayout());
        rodape.add(new JLabel("NENSE MARKET - Contato: (64) 99242-7386", SwingConstants.CENTER), BorderLayout.CENTER);
        rodape.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); 

        add(painelPrincipal, BorderLayout.CENTER);
        add(rodape, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void atualizarPainelDados() {
        painelDados.removeAll();
        String metodo = (String) comboPagamento.getSelectedItem();

        switch (metodo) {
            case "Cartão de Crédito":
            case "Cartão de Débito":
                painelDados.add(new JLabel("Número do Cartão (16 dígitos):"));
                painelDados.add(campoCartaoNumero);
                painelDados.add(new JLabel("CVV (3 dígitos):"));
                painelDados.add(campoCartaoCVV);
                painelDados.add(new JLabel("Data de Validade (MM/AA):"));
                painelDados.add(campoCartaoValidade);
                break;
            case "Pix":
                campoPix.setText(gerarChavePix());
                painelDados.add(new JLabel("Chave Pix:"));
                painelDados.add(campoPix);
                break;
            case "Boleto":
                campoBoleto.setText(gerarCodigoBoleto());
                painelDados.add(new JLabel("Código de Boleto:"));
                painelDados.add(campoBoleto);
                break;
        }

        painelDados.revalidate();
        painelDados.repaint();
    }

    private boolean validarDados() {
        String metodo = (String) comboPagamento.getSelectedItem();
        switch (metodo) {
            case "Cartão de Crédito":
            case "Cartão de Débito":
                String numeroCartao = campoCartaoNumero.getText();
                String cvv = new String(campoCartaoCVV.getPassword());
                String validade = campoCartaoValidade.getText();

                if (numeroCartao.length() != 16 || !numeroCartao.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Número do cartão deve ter 16 dígitos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (cvv.length() != 3 || !cvv.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "CVV deve ter 3 dígitos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (!validade.matches("(0[1-9]|1[0-2])/\\d{2}")) {
                    JOptionPane.showMessageDialog(this, "Data de validade deve estar no formato MM/AA.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            case "Pix":
                break;
            case "Boleto":
                break;
        }
        return true;
    }

    private String gerarChavePix() {
        Random random = new Random();
        long chave = 1000000000000000L + (long) (random.nextDouble() * 9000000000000000L);
        return String.valueOf(chave);
    }

    private String gerarCodigoBoleto() {
        Random random = new Random();
        long codigo = 1000000000000000L + (long) (random.nextDouble() * 9000000000000000L);
        return String.valueOf(codigo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaPagamento(new Carrinho())); 
    }
}
