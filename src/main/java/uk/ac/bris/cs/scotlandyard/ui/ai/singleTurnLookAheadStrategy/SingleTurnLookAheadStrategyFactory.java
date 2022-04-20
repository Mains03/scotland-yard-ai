package uk.ac.bris.cs.scotlandyard.ui.ai.singleTurnLookAheadStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardV2;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameState;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameStateAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.strategies.MinDistStaticPosEval;

/**
 * Simplifies single turn look ahead strategy instantiations.
 */
public class SingleTurnLookAheadStrategyFactory {
    public SingleTurnLookAheadStrategy createMinDistStrategy(Board board, MinDistStaticPosEval strategy) {
        return new SingleTurnLookAheadStrategy(board) {
            @Override
            public int staticEvaluation(AiBoardV2 board) {
                AiGameState gameState = new AiGameStateAdapter(board);
                return strategy.evaluate(gameState);
            }
        };
    }
}
