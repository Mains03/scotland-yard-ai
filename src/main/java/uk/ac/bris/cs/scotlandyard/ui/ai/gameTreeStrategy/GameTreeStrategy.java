package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;

import java.util.Objects;

public class GameTreeStrategy implements BestMoveStrategy {
    private final Board board;
    private final GameTreeFactory gameTreeFactory;
    private final GameTreeEvaluationStrategy evaluationStrategy;

    public GameTreeStrategy(
            Board board,
            GameTreeFactory gameTreeFactory,
            GameTreeEvaluationStrategy evaluationStrategy
    ) {
        this.board = Objects.requireNonNull(board);
        this.gameTreeFactory = Objects.requireNonNull(gameTreeFactory);
        this.evaluationStrategy = Objects.requireNonNull(evaluationStrategy);
    }

    @Override
    public Move determineBestMove() {
        GameTree gameTree = gameTreeFactory.createGameTree(board);
        return evaluationStrategy.determineBestMove(gameTree);
    }
}
