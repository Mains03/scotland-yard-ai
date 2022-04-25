package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

import java.util.Objects;

/**
 * Node without children.
 */
public class LeafNode implements Node {
    public final AiBoard board;

    public LeafNode(AiBoard board) {
        this.board = Objects.requireNonNull(board);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
