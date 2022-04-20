package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;

import java.util.Objects;
import java.util.Optional;

public class GameTreeLeafNode extends GameTreeNode {
    // required to statically evaluate
    private final AiBoard board;

    public GameTreeLeafNode(AiBoard board) {
        this.board = Objects.requireNonNull(board);
    }

    @Override
    public int accept(GameTreeVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<Move> mrXMoveMade() {
        // MrX didn't make a move to get here
        return Optional.empty();
    }

    public AiBoard getBoard() { return board; }
}
