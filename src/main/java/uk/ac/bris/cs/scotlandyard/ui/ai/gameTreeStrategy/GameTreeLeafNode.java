package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;

import java.util.Objects;
import java.util.Optional;

public class GameTreeLeafNode extends GameTree {
    private final AiBoard board;
    private final Optional<Move> mMrXMoveMade;

    public GameTreeLeafNode(AiBoard board, Optional<Move> mMrXMoveMade) {
        this.board = Objects.requireNonNull(board);
        this.mMrXMoveMade = Objects.requireNonNull(mMrXMoveMade);
    }

    @Override
    public Optional<Move> accept(GameTreeVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<Move> mrXMoveMade() {
        return mMrXMoveMade;
    }

    public AiBoard getBoard() { return board; }
}
