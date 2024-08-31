import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<Item> itens = new ArrayList<>();

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public List<Item> getItens() {
        return itens;
    }

    public void limparCarrinho() {
        itens.clear();
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Item item : itens) {
            total += item.getPreco();
        }
        return total;
    }
}
