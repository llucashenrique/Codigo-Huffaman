# Documentação da Código Huffman

## Introdução

### Classe NoHuffman

A classe **NoHuffman** representa um nó utilizado na construção da árvore de **Huffman**, um algoritmo ultilizado para compreensão de dados.

 ```bash
    public class NoHuffman implements           Comparable<NoHuffman> {
    Character character;
    int frequency;
    NoHuffman left;
    NoHuffman right;
 ```

Define um nó da árvore de **Huffman** com:

 - **character**: caractere representado (pode ser nulo em nós internos);
 - **frequency**: frequência de ocorrência;
 - **NoHuffamanleft** e **NoHuffamanright**: filhos esquerdo e direito para formar a árvore binária.


 ```bash
   public NoHuffman(Character character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(NoHuffman outro) {
        return Integer.compare(this.frequency, outro.frequency);
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
 ```
Criação do **Construtor**, **compareTo**, **isleaf**

- **Construtor**: inicializa o caractere e sua frequência.
- **compareTo**: permite ordenar nós pela frequência.
- **isLeaf**: verifica se o nó é uma folha (sem filhos).

### Classe Huffaman 

A classe principal do programa é responsável por executar o algoritmo de compressão de dados de **Huffman**. Ela realiza a leitura do texto, mapeia os caracteres e calcula suas frequências, constrói a árvore de **Huffman** e gera os códigos binários correspondentes. Além disso, manipula as funções auxiliares e utiliza as classes **NoHuffman** e **HuffmanDecoder** dentro da função **main()** para codificar e decodificar os dados.

```bash
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
 ```

O método **read_data** realiza a leitura de um arquivo bruto no formato **.txt**, contendo o texto a ser codificado. Ele trata possíveis erros de leitura e converte o conteúdo do arquivo para uma String, permitindo sua manipulação posterior no processo de compressão com Huffman via **return**.


```bash
 public Map<Character, Integer> countFrequency(String texto) {
        Map<Character, Integer> frequency = new HashMap<>();

        for (char c : texto.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        return frequency;
    }
```

    Esta função recebe uma String como entrada e utiliza um HashMap para contar a frequência de cada caractere presente no texto. Ela percorre o conteúdo caractere por caractere, atualiza o mapa com a quantidade de ocorrências e retorna o resultado como um **Map<Character, Integer>**.


```bash
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
```

Esta função recebe um **Map<Character, Integer>** com as frequências dos caracteres e constrói a árvore de **Huffman** utilizando uma **PriorityQueue**, que garante que os nós com menor frequência sejam processados primeiro.

- Cada entrada do mapa é transformada em um objeto **NoHuffman** e inserida na fila de prioridade.
- Em seguida, dois nós com menor frequência são removidos da fila **(poll())**, combinados em um novo nó interno (sem caractere), cuja frequência é a soma dos dois.
- O novo nó é reinserido na fila, e o processo se repete até restar apenas um nó — a raiz da árvore de **Huffman**.
- Os nós combinados são organizados como filhos esquerdo e direito, formando a estrutura binária da árvore.

```bash
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
```

Geração dos Códigos Binários de **Huffman**
Esta função é responsável por gerar os códigos binários de compressão para cada caractere da árvore de **Huffman**. Ela recebe como parâmetros:

- **raiz**: o nó raiz da árvore de **Huffman**;
- **currentcode**: o código binário acumulado até o nó atual;
- **codetable**: um **Map<Character, String>** onde os códigos serão armazenados.

A função funciona de forma recursiva:

- Se o nó atual **(raiz)** for **null**, a função retorna imediatamente.
- Se o nó for uma folha, significa que ele representa um caractere real, então o caractere é associado ao código atual na codetable.

- A função continua a recursão para os filhos esquerdo **(left)** e direito **(right)**, adicionando **"0"** para a esquerda e **"1"** para a direita, conforme a lógica binária da árvore de Huffman.

```bash
    File myObj = new File("Huffman_Coding.txt");
    Huffaman h = new Huffaman();
    String content = h.read_File(myObj);
    System.out.println("Imprimindo texto!\n" + content);

    Map<Character, Integer> freq = h.countFrequency(content);

    char mostFrequent = '\0';
    int maxFrequent = 0;

    for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
        System.out.println("'" + entry.getKey() + "' : " + entry.getValue());
        if (entry.getValue() > maxFrequent) {
            maxFrequent = entry.getValue();
            mostFrequent = entry.getKey();
        }
    }

    int totalCharacters = content.length();
    System.out.println("Total de caracteres: " + totalCharacters);
    System.out.println("Caractere mais frequente: '" + mostFrequent + "' com " + maxFrequent + " ocorrências.");
```
Nesta etapa, o programa realiza a criação dos objetos principais para manipulação dos dados. O arquivo bruto **.txt** é lido e convertido para **String**, permitindo a análise do conteúdo. Em seguida, é feita a contagem da frequência de cada caractere usando um **Map**, e o programa identifica:

- O total de caracteres presentes no texto;
- O caractere mais frequente, com base na maior contagem registrada.

Essas informações são exibidas no console como parte da análise inicial antes da compressão.



```bash
Map<Character, String> codetable = new HashMap<>();
h.generateCodes(raiz, "", codetable);

System.out.println("Codigos de Huffman:");
for (Map.Entry<Character, String> entry : codetable.entrySet()) {
    System.out.println("'" + entry.getKey() + "' : " + entry.getValue());
}
```

- Com base no mapa de frequências gerado anteriormente, o programa constrói a árvore de **Huffman** utilizando a função **buildTreeFrequency**. 
- Em seguida, a função **generateCodes** percorre essa árvore de forma recursiva, gerando os códigos binários únicos para cada caractere e armazenando-os em uma codetable.
- Por fim, os códigos gerados são exibidos no console, permitindo visualizar como cada caractere foi representado na compressão.

```bash
String textEncoded = "";
for (char c : content.toCharArray()) {
    textEncoded += codetable.get(c);
}

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
```

- Nesta etapa, o texto original é codificado utilizando os códigos binários gerados pela árvore de **Huffman**. Cada caractere é substituído por seu código correspondente, formando uma nova **String** compactada.
- O conteúdo codificado é então salvo em um arquivo **.chf**, representando o resultado da compressão. Em seguida, o programa instancia a classe **HuffmanDecoder** e realiza a decodificação do texto, reconstruindo o conteúdo original a partir da árvore de **Huffman** e dos dados comprimidos.
- Por fim, o texto decodificado é exibido no console, confirmando que o processo de compressão e descompressão foi realizado corretamente.

### Classe HuffamanDecode

```bash

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
```

A classe **HuffmanDecoder** é responsável por reconstruir o texto original a partir da sequência binária gerada na compressão. A função decode recebe:

- **textEncoded**: a String contendo o texto codificado em binário;
- **raiz**: o nó raiz da árvore de Huffman usada na codificação.

A lógica percorre cada bit da **textEncoded**:

Se o bit for **'0'**, segue para o filho esquerdo da árvore;
Se for **'1'**, segue para o filho direito.

Quando chega a um nó folha, significa que encontrou um caractere original. Esse caractere é adicionado ao resultado, e o ponteiro volta para a raiz da árvore para continuar o processo. Ao final, a função retorna o texto decodificado como uma **String**.

### 📊 UML (Unified Modeling Language) DO CÓDIGO

![uml](./imagens/Huffaman-diagrama%20(1).png)

## 🚀 Como Usar o Código Huffman

### ✅ Pré-requisitos

Antes de executar o projeto, certifique-se de ter os seguintes itens instalados:

- **Java JDK 17** ou superior
- **IDE** compatível com Java (como IntelliJ IDEA, **Eclipse** ou **VS Code** com extensão **Java**)
Git (para clonar o repositório).
- Arquivo de texto **(.txt)** com o conteúdo que será comprimido (ex: Huffman_Coding.txt)

### 📦 Clonando o Repositório

```bash
git clone https://github.com/llucashenrique/Codigo-Huffaman.git
cd seu-repositorio-huffman
```

### 📁 Estrutura Esperada

```bash
├── src/
    └── Huffaman.java
    └── HuffmanDecoder.java
    └── NoHuffman.java
    └──  
├── pdf/
    └── Projeto Codificação de Huffman.pdf
    └── TheEleganceOfEfficiency-TheFundamentalImportanceOfHuffmanCoding.pdf
└── imagens/
    └── Huffaman-diagrama
└── text/
     └── File_Coding.chf
     └── Huffaman_Coding.txt
 ```

 ### ▶️ Executando o Projeto

 - Abra o projeto na sua IDE Java
- Compile os **arquivos .java**
- Execute a classe **Huffaman**, que contém o método main()

### 🔄 Fluxo de Execução

- Leitura do arquivo .txt com o texto original
- Contagem de frequência de cada caractere
- Construção da árvore de Huffman
- Geração dos códigos binários
- Codificação do texto original
- Salvamento do texto comprimido em .chf
- Decodificação do texto comprimido
- Exibição do texto original reconstruído

### 📂 Arquivos Gerados

- file_coding.chf: contém o texto codificado em binário
- Saída no console: mostra o texto original, frequências, códigos de Huffman e texto decodificado.