package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;

import java.util.Objects;

/**
 * Creates a game tree and evaluates it to find the best move. This is a
 * BestMoveStrategy.
 */
public class GameTreeStrategy implements BestMoveStrategy {
    private final Board board;
    private final GameTreeDataStructureFactory factory;
    private final GameTreeEvaluationStrategy evaluationStrategy;

    /**
     * GameTreeStrategy constructor.
     * @param board the board
     * @param factory factory to create a game tree
     * @param evaluationStrategy strategy to evaluate the tree with
     */
    public GameTreeStrategy(
            Board board,
            GameTreeDataStructureFactory factory,
            GameTreeEvaluationStrategy evaluationStrategy
    ) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(factory);
        Objects.requireNonNull(evaluationStrategy);
        this.board = board;
        this.factory = factory;
        this.evaluationStrategy = evaluationStrategy;
    }

    @Override
    public Move determineBestMove() {
        GameTreeDataStructure gameTree = factory.createGameTree(board);
        return evaluationStrategy.evaluateGameTree(gameTree);
    }
}
