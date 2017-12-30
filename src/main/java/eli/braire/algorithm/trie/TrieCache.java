package eli.braire.algorithm.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;

/**
 * Implementation of a Trie (Suffix Tree) data structure. The path traversed through the Trie corresponds to a single word. Common character sequences are consolidated.
 *
 * @author The Architect
 */
public class TrieCache implements Cache
{
    private final Map<Character, Node> root;

    private TrieCache()
    {
        root = new HashMap<>();
    }

    /**
     * @return a new empty {@link TrieCache} that can be used to hold a set of character sequences for quick retrieval. (Cannot be null)
     */
    public static Cache create()
    {
        return new TrieCache();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertWord(final String word)
    {
        // If word is null or empty, nothing to insert, so return
        if (Strings.isNullOrEmpty(word))
        {
            return;
        }
        // Peel off first character of the word
        final char character = word.charAt(0);
        // Create initial node
        final Node node = root.get(character);
        if (node == null)
        {
            // If no match from the root map, then create entire node sequence
            final List<Node> nodes = TrieNode.create(word);
            // Insert new branch of nodes to root
            root.put(character, nodes.get(0));
        }
        else
        {
            // Match from the root map, then follow then follow the path down the branch until word
            // no longer matches sequence, or branch terminates
            final List<Node> nodes = new ArrayList<>(1);
            nodes.add(node);
            // Insert initial node to root
            root.put(character, node);
            if (word.length() > 1)
            {
                // Find longest character sequence match
                nodes.addAll(node.parseSuffix(word.substring(1)));
            }
            final int nodeLength = nodes.size();
            Node lastNode = nodes.get(nodeLength - 1);
            for (int index = nodeLength; index < word.length(); index++)
            {
                // Append new child nodes if only a partial match
                lastNode = lastNode.addChildNode(word.charAt(index));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeWord(final String word)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Node> parseWord(final String word)
    {
        return null;
    }
}