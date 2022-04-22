package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.structures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitor.GameTreeVisitor;

import java.util.Optional;

public interface GameTreeNode {
    /**
     * Used to determine the best move, returns the integer evaluation.
     */
    int accept(GameTreeVisitor visitor);

    /**
     * Not all nodes stores MrX move.
     */
    Optional<Move> getMrXMove();
}
