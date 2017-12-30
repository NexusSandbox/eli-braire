package eli.braire.algorithm.trie;

import java.util.List;

/**
 * Interface of a cache used to implement a Trie data structure.
 * 
 * @author The Architect
 */
public interface Cache
{
    /**
     * @param word
     */
    public void insertWord(String word);

    /**
     * @param word
     */
    public void removeWord(String word);

    /**
     * @param word
     * @return
     */
    public List<Node> parseWord(String word);
}
