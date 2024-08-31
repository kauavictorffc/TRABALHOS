import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPrincipal extends JFrame {
    private Carrinho carrinho = new Carrinho();

    public JanelaPrincipal() {
        setTitle("Supermercado - Escolha de Itens");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelItens = new JPanel();
        painelItens.setLayout(new GridLayout(0, 3)); // 3 colunas: checkbox, nome do item, spinner para quantidade

        Item[] items = {
            new Item("Arroz", 20.00),
            new Item("Feijão", 10.00),
            new Item("Macarrão", 5.00),
            new Item("Açúcar", 4.50),
            new Item("Óleo", 7.00),
            new Item("Leite", 3.50),
            new Item("Pão", 2.00),
            new Item("Queijo", 15.00),
            new Item("Frutas", 8.00),
            new Item("Vegetais", 6.00),
            new Item("Carnes", 25.00),
            new Item("Peixes", 30.00),
            new Item("Biscoitos", 4.00),
            new Item("Molhos", 7.50),
            new Item("Tempero", 3.00)
        };

        JCheckBox[] checkBoxes = new JCheckBox[items.length];
        JSpinner[] spinners = new JSpinner[items.length];

        for (int i = 0; i < items.length; i++) {
            Item item = items[i];

            // Checkbox para selecionar o item
            checkBoxes[i] = new JCheckBox();
            painelItens.add(checkBoxes[i]);

            // Label para mostrar o nome e o preço do item
            JLabel nomePrecoItem = new JLabel(item.toString(), JLabel.CENTER);
            painelItens.add(nomePrecoItem);

            // Spinner para escolher a quantidade
            spinners[i] = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            painelItens.add(spinners[i]);
        }

        JButton botaoAdicionarCarrinho = new JButton("Adicionar ao Carrinho");
        botaoAdicionarCarrinho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean itemAdicionado = false;

                for (int i = 0; i < items.length; i++) {
                    if (checkBoxes[i].isSelected()) {
                        int quantidade = (int) spinners[i].getValue();
                        for (int j = 0; j < quantidade; j++) {
                            carrinho.adicionarItem(items[i]);
                        }
                        itemAdicionado = true;
                    }
                }

                if (itemAdicionado) {
                    JOptionPane.showMessageDialog(null, "Itens adicionados ao carrinho.");
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum item foi selecionado.");
                }
            }
        });

        JButton botaoVerCarrinho = new JButton("Ver Carrinho");
        botaoVerCarrinho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (carrinho.getItens().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O carrinho está vazio.");
                } else {
                    StringBuilder listaCarrinho = new StringBuilder("Itens no Carrinho:\n");
                    for (Item item : carrinho.getItens()) {
                        listaCarrinho.append(item.getNome()).append(" - R$ ")
                            .append(String.format("%.2f", item.getPreco())).append("\n");
                    }
                    listaCarrinho.append("Total: R$ ").append(String.format("%.2f", carrinho.calcularTotal()));

                    JOptionPane.showMessageDialog(null, listaCarrinho.toString());
                }
            }
        });

        JButton botaoEsvaziarCarrinho = new JButton("Esvaziar Carrinho");
        botaoEsvaziarCarrinho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (carrinho.getItens().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O carrinho já está vazio.");
                } else {
                    carrinho.limparCarrinho();
                    JOptionPane.showMessageDialog(null, "Carrinho esvaziado.");
                }
            }
        });

        JButton botaoFinalizar = new JButton("Finalizar Compra");
        botaoFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JanelaPagamento(carrinho);
                dispose();
            }
        });

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(4, 1));
        painelBotoes.add(botaoAdicionarCarrinho);
        painelBotoes.add(botaoVerCarrinho);
        painelBotoes.add(botaoEsvaziarCarrinho);
        painelBotoes.add(botaoFinalizar);

        add(painelItens, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaPrincipal());
    }
}
