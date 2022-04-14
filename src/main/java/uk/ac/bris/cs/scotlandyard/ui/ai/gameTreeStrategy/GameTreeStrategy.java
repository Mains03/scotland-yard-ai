package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class GameTreeStrategy<T> implements BestMoveStrategy {
    private final GameTree<T> gameTree;
    private final GameTreeVisitor<T> gameTreeVisitor;

    public GameTreeStrategy(GameTree<T> gameTree, GameTreeVisitor<T> gameTreeVisitor) {
        this.gameTree = Objects.requireNonNull(gameTree);
        this.gameTreeVisitor = Objects.requireNonNull(gameTreeVisitor);
    }

    @Override
    public Move determineBestMove() {
        Optional<Move> mMove = gameTree.accept(gameTreeVisitor);
        if (mMove.isEmpty())
            throw new NoSuchElementException("No MrX move");
        else
            return mMove.get();
    }
}
