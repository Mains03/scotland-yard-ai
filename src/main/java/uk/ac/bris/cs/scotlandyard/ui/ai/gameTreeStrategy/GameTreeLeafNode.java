package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2;

import java.util.Objects;
import java.util.Optional;

public class GameTreeLeafNode extends GameTreeNode {
    // required to statically evaluate
    private final AiBoardV2 board;

    public GameTreeLeafNode(AiBoardV2 board) {
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

    public AiBoardV2 getBoard() { return board; }
}
