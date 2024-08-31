public class Pagamento {

    public enum MetodoPagamento {
        CARTAO_CREDITO,
        DEBITO,
        PIX,
        BOLETO
    }

    public static String processarPagamento(double valor, MetodoPagamento metodo) {
        String recibo = "Pagamento de R$ " + String.format("%.2f", valor) + " realizado com ";

        switch (metodo) {
            case CARTAO_CREDITO:
                recibo += "Cartão de Crédito.";
                break;
            case DEBITO:
                recibo += "Débito.";
                break;
            case PIX:
                recibo += "PIX.";
                break;
            case BOLETO:
                recibo += "Boleto.";
                break;
        }

        return recibo;
    }
}
