package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

/**
 * Stores the move made.
 */
public class LeafNodeWithMove extends LeafNode implements Node {
    public final Move move;

    public LeafNodeWithMove(AiBoard board, Move move) {
        super(board);
        this.move = move;
    }

    @Override
    public <T> T accept(Node.Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
