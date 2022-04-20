package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Optional;

public abstract class GameTreeNode {
    /**
     * Used to determine the best move, returns the integer evaluation.
     */
    public abstract int accept(GameTreeVisitor visitor);

    /**
     * MrX may not have made a move at this node.
     */
    public abstract Optional<Move> mrXMoveMade();
}
