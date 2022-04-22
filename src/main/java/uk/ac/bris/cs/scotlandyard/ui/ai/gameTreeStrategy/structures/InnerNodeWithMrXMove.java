package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

import java.util.Optional;

/**
 * To determine MrX's best move, we need to know what move was made to get to this position.
 */
public class InnerNodeWithMrXMove extends InnerNode {
    private Move move;

    public InnerNodeWithMrXMove(AiBoard board, int depth, Move mrXMove) {
        super(board, depth);
        move = mrXMove;
    }

    @Override
    public Optional<Move> getMrXMove() {
        return Optional.of(move);
    }
}
