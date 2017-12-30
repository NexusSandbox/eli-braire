package eli.braire.algorithm.trie;

import java.util.List;
import java.util.Map;

/**
 * Interface of a node used to implement a Trie data structure.
 * 
 * @author The Architect
 */
public interface Node
{
    /**
     * @return the corresponding {@link Character} contained by the node.
     */
    public char getCharacter();

    /**
     * @return a {@link Map} of {@link Node}s by {@link Character}. This Map corresponds to a set of
     *         possible next characters relative to the current node. (Cannot be null, but possibly
     *         empty if there are no child nodes)
     */
    public Map<Character, Node> getChildNodes();

    /**
     * @param character a UTF-16 encoded character value.
     * @return the matching {@link Node} for the corresponding <code>character</code>. (Possibly
     *         null if there are no child nodes, or if there is no match to any child nodes)
     */
    public Node getChildNode(char character);

    /**
     * @param suffix a string search to compare against sequential {@link Node}s in the Trie.
     *            (Possibly null)
     * @return a {@link List} of {@link Node}s corresponding to the sequence of characters matched
     *         in the Trie. This may return a partial match with the last node corresponding to the
     *         last matched character in the <code>suffix</code>. (Possibly null if
     *         <code>suffix</code> is null, and possibly empty if the node sequence does not match
     *         <code>suffix</code>)
     */
    public List<Node> parseSuffix(String suffix);

    /**
     * @param character a UTF-16 encoded character value.
     * @return a corresponding {@link Node} for the <code>character</code> and it is appended to the
     *         current node as a child node. (Cannot be null)
     */
    public Node addChildNode(char character);
}