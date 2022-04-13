package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Optional;
import java.util.Set;

public abstract class GameTreeInnerNode extends GameTree {
    @Override
    public void accept(GameTreeVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns the move MrX made to get to this position. Optional
     * since MrX may not have made a move, the detective(s) may have.
     * @return MrX move made
     */
    public abstract Optional<Move> mrXMoveMade();

    public abstract Set<GameTree> getChildren();
}
