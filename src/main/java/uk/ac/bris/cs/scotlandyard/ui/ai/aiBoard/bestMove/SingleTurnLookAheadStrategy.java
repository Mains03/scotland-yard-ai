package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.bestMove;

import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.SingleTurnLookAheadEvaluation;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation.EvaluationStrategy;

import java.util.Objects;

/**
 * {@link BestMoveStrategy} which looks one move ahead.
 */
public class SingleTurnLookAheadStrategy implements BestMoveStrategy {
    private final EvaluationStrategy strategy;

    public SingleTurnLookAheadStrategy(EvaluationStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public Move bestMove(AiBoard board) {
        SingleTurnLookAheadEvaluation visitor = new SingleTurnLookAheadEvaluation(strategy, true);
        board.accept(visitor);
        return visitor.getBestMove();
    }
}
