
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Huffaman {

    // Function to read file and return its content as a String
    public String read_File(File file) {
        StringBuilder data = new StringBuilder();
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: File not found.");
            e.printStackTrace();
        }
        return data.toString();
    }

    // Getting the frequency of characters
    public Map<Character, Integer> countFrequency(String texto) {
        Map<Character, Integer> frequency = new HashMap<>();

        for (char c : texto.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        return frequency;
    }

    // Queuing the values
    public NoHuffman buildTreeFrequency(Map<Character, Integer> frequency) {
        PriorityQueue<NoHuffman> fila = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            fila.add(new NoHuffman(entry.getKey(), entry.getValue()));
        }

        while (fila.size() > 1) {
            NoHuffman no1 = fila.poll();
            NoHuffman no2 = fila.poll();

            NoHuffman newNo = new NoHuffman(null, no1.frequency + no2.frequency);
            newNo.left = no1;
            newNo.right = no2;

            fila.add(newNo);
        }

        return fila.poll();
    }

    // Coding the code
    public void generateCodes(NoHuffman raiz, String currentcode, Map<Character, String> codetable) {
        if (raiz == null) {
            return;
        }

        if (raiz.isLeaf()) {
            codetable.put(raiz.character, currentcode);
        }

        generateCodes(raiz.left, currentcode + "0", codetable);
        generateCodes(raiz.right, currentcode + "1", codetable);
    }

    public static void main(String[] args) {

        File myObj = new File("Huffman_Coding.txt");
        Huffaman h = new Huffaman();
        String content = h.read_File(myObj);
        System.out.println("Imprimindo texto!");
        System.out.println();
        System.out.println(content);

        Map<Character, Integer> freq = h.countFrequency(content);

        char mostFrequent = '\0';
        int maxFrequent = 0;

        // For each para calcular o caractere mais frequente
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            System.out.println("'" + entry.getKey() + "' : " + entry.getValue());

            if (entry.getValue() > maxFrequent) {
                maxFrequent = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }

        // Total de caracteres
        int totalCharacters = content.length();
        System.out.println("Total de caracteres: " + totalCharacters);
        System.out.println("Caractere mais frequente: '" + mostFrequent + "' com " + maxFrequent + " ocorrências.");
        
        //Construção da Árvore de Huffman
        NoHuffman raiz = h.buildTreeFrequency(freq);

        // Geração da Tabela de Códigos
        Map<Character, String> codetable = new HashMap<>();
        h.generateCodes(raiz, "", codetable);

        System.out.println("Codigos de Huffman:");
        for (Map.Entry<Character, String> entry : codetable.entrySet()) {
            System.out.println("'" + entry.getKey() + "' : " + entry.getValue());
        }

        // Codificação do Texto
        String textEncoded = "";
        for (char c : content.toCharArray()) {
            textEncoded += codetable.get(c);
        }

        // Salva o texto codificado em um arquivo .chf
        try (FileWriter writer = new FileWriter("file_coding.chf")) {
            writer.write(textEncoded);
            System.out.println("Arquivo .chf salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo .chf.");
            e.printStackTrace();
        }

        HuffmanDecoder decoder = new HuffmanDecoder();
        String textoOriginal = decoder.decode(textEncoded, raiz);

        System.out.println("Texto decodificado:");
        System.out.println(textoOriginal);

    }
}
