package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.GameTree;

/**
 * A node in {@link GameTree}.
 */
public interface Node {
    interface Visitor<T> {
        T visit(InnerNodeWithMove node);

        T visit(InnerNode node);

        T visit(LeafNodeWithMove node);

        T visit(LeafNode node);
    }

    <T> T accept(Visitor<T> visitor);
}
