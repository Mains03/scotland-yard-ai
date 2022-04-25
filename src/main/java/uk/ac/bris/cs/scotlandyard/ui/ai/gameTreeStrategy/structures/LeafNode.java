package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.GameTreeVisitor;

import java.util.Objects;
import java.util.Optional;

/**
 * Node without children.
 */
public class LeafNode implements GameTreeNode {
    // used in static evaluation
    private final StandardAiBoard board;

    public LeafNode(StandardAiBoard board) {
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

    public StandardAiBoard getBoard() { return board; }
}
