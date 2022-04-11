package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.GameTreeStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

/**
 * Minimax algorithm.
 */
public class MinimaxGameTree extends GameTreeStrategy implements BestMoveStrategy {
    /**
     * GameTreeStrategy constructor.
     *
     * @param board              the board
     * @param evaluationStrategy
     */
    public MinimaxGameTree(Board board, StaticPositionEvaluationStrategy evaluationStrategy) {
        super(board, evaluationStrategy);
    }
}
