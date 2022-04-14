package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Optional;

public abstract class GameTreeVisitor {
    public abstract Optional<Move> visit(GameTreeInnerNode innerNode);
    public abstract Optional<Move> visit(GameTreeLeafNode leafNode);
}
