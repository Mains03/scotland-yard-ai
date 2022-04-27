package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.winnerLimited;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

/**
 * {@link Node} which stores the move made.
 */
public class NodeWithMove extends Node {
    public final Move move;

    public NodeWithMove(AiBoard board, Move move) {
        super(board);
        this.move = move;
    }
}
