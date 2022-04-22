package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;

import java.util.Optional;

/**
 * To determine MrX's best move, we need to know what move was made to get to this position.
 * Identical to parent {@link LeafNode} but also stores mrX's move
 */
public class LeafNodeWithMrXMove extends LeafNode {
    private Move move;

    public LeafNodeWithMrXMove(AiBoard board, Move mrXMove) {
        super(board);
        move = mrXMove;
    }

    @Override
    public Optional<Move> getMrXMove() { return Optional.of(move); }
}
