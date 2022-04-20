package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2;

import java.util.Optional;

/**
 * To determine MrX's best move, we need to know what move was made to get to this position.
 */
public class GameTreeNodeWithMrXMove extends GameTreeInnerNode {
    private Move move;

    public GameTreeNodeWithMrXMove(AiBoardV2 board, int depth, Move mrXMove) {
        super(board, depth);
        move = mrXMove;
    }

    @Override
    public Optional<Move> mrXMoveMade() {
        return Optional.of(move);
    }
}
