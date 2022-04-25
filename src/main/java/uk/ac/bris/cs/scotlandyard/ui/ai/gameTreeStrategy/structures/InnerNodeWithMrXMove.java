package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;

import java.util.Optional;

/**
 * To determine MrX's best move, we need to know what move was made to get to this position.
 * Identical to parent {@link InnerNode} but also stores mrX's move
 */
public class InnerNodeWithMrXMove extends InnerNode {
    private Move move;

    public InnerNodeWithMrXMove(StandardAiBoard board, int depth, Move mrXMove) {
        super(board, depth);
        move = mrXMove;
    }

    @Override
    public Optional<Move> getMrXMove() {
        return Optional.of(move);
    }
}
