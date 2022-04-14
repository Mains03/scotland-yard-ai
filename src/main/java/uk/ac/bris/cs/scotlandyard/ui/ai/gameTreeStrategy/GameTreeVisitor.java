package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Optional;

public abstract class GameTreeVisitor<T> {
    public abstract Optional<Move> visit(GameTreeInnerNode<T> innerNode);
    public abstract Optional<Move> visit(GameTreeLeafNode<T> leafNode);
}
