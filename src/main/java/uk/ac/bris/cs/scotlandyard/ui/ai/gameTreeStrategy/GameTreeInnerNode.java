package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Optional;
import java.util.Set;

public abstract class GameTreeInnerNode<T> extends GameTree<T> {
    @Override
    public Optional<Move> accept(GameTreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public abstract Set<GameTree<T>> getChildren();
}
