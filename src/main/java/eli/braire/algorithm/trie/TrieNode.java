package eli.braire.algorithm.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Implementation of a {@link Node} from a {@link TrieCache}.
 *
 * @author The Architect
 */
public class TrieNode implements Node
{
    private final char           character;
    private Map<Character, Node> children = null;

    private TrieNode(final char character)
    {
        this.character = character;
    }

    /**
     * @param character a UTF-16 encoded character value.
     * @return a new {@link TrieNode} corresponding to the <code>character</code> input. (Cannot be null)
     */
    public static Node create(final char character)
    {
        return new TrieNode(character);
    }

    /**
     * @param suffix a string to initialize a character sequence of {@link TrieNode}s. (Possibly null)
     * @return a new {@link List} of {@link TrieNode}s corresponding to the character sequence of <code>suffix</code> and are ancestrally related. (Possibly null if suffix is null, and possibly empty
     *         if suffix is an empty string)
     */
    public static List<Node> create(final String suffix)
    {
        // Nothing to create if suffix is null
        if (suffix == null)
        {
            return null;
        }
        final List<Node> nodes = new ArrayList<>(suffix.length());
        if (suffix.length() > 0)
        {
            // Peel off first character of the suffix
            Node node = TrieNode.create(suffix.charAt(0));
            // Add first node to list
            nodes.add(node);
            // Iterate over each sequential character in the suffix after the first
            for (int index = 1; index < suffix.length(); index++)
            {
                node = node.addChildNode(suffix.charAt(index));
                nodes.add(node);
            }
        }

        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char getCharacter()
    {
        return character;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Character, Node> getChildNodes()
    {
        // If no children return an empty immutable map
        if (children == null)
        {
            return ImmutableMap.of();
        }

        // Otherwise return a populated immutable map
        return ImmutableMap.copyOf(children);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getChildNode(final char character)
    {
        // If no children return null Node
        if (children == null)
        {
            return null;
        }

        // Otherwise return the corresponding node, this could return null if character does not
        // match any child node
        return children.get(character);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Node> parseSuffix(final String suffix)
    {
        // If null suffix return null
        if (suffix == null)
        {
            return null;
        }
        final List<Node> nodes = new ArrayList<>(suffix.length());
        if (suffix.length() > 0)
        {
            // [Base Case] Peel off first character in the suffix, and match to the next node
            final Node child = getChildNode(suffix.charAt(0));
            if (child != null)
            {
                // Add first node to the list
                nodes.add(child);
                // [Recursive Case] Parse remaining part of the suffix, and add to the list
                if (suffix.length() > 1)
                {
                    nodes.addAll(child.parseSuffix(suffix.substring(1)));
                }
            }
            // If suffix is only a partial match then return match so far. This requires no
            // additional action
        }

        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node addChildNode(final char character)
    {
        // If no children, then initialize the map of child nodes
        if (children == null)
        {
            children = new HashMap<>(1);
        }
        // Create new child node and add it to the children
        final TrieNode node = new TrieNode(character);
        children.put(character, node);

        return node;
    }
}