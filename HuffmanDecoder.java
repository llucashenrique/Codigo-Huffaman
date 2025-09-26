public class HuffmanDecoder {

    public String decode(String textEncoded, NoHuffman raiz) {
        StringBuilder result = new StringBuilder();
        NoHuffman current = raiz;

        for (char bit : textEncoded.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            }

            // Se chegou em uma folha, adiciona o caractere
            if (current.isLeaf()) {
                result.append(current.character);
                current = raiz; // volta para o topo da árvore
            }
        }

        return result.toString();
    }
}
