package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.depthLimited;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

/**
 * Stores the move made
 */
public class InnerNodeWithMove extends InnerNode implements Node {
    public final Move move;

    public InnerNodeWithMove(AiBoard board, int depth, Move move) {
        super(board, depth);
        this.move = move;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
