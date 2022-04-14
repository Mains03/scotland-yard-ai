package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Optional;

public abstract class GameTree<T> {
    /**
     * Determines the best move in the tree.
     * @param visitor visitor
     * @return the best move
     */
    public abstract Optional<Move> accept(GameTreeVisitor<T> visitor);

    /**
     * Returns the move MrX made to get to this position. Optional
     * since MrX may not have made a move, the detective(s) may have.
     * @return MrX move made
     */
    public abstract Optional<Move> mrXMoveMade();
}
