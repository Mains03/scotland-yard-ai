package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;

import java.util.Objects;
import java.util.Optional;

/**
 * Node without children.
 */
public class LeafNode implements GameTreeNode {
    // used in static evaluation
    private final AiBoard board;

    public LeafNode(AiBoard board) {
        this.board = Objects.requireNonNull(board);
    }

    @Override
    public int accept(GameTreeVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<Move> getMrXMove() {
        // MrX didn't make a move to get here
        return Optional.empty();
    }

    public AiBoard getBoard() { return board; }
}
