package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.gameTreeStructures;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeVisitor;

import java.util.Optional;

public interface GameTreeNode {
    /**
     * Used to determine the best move, returns the integer evaluation.
     */
    int accept(GameTreeVisitor visitor);

    /**
     * Not all nodes stores MrX move.
     * If not MrX's move, returns empty
     */
    Optional<Move> getMrXMove();
}
