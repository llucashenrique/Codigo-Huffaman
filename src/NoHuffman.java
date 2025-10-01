
public class NoHuffman implements Comparable<NoHuffman> {
    Character character;
    int frequency;
    NoHuffman left;
    NoHuffman right;

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
}
