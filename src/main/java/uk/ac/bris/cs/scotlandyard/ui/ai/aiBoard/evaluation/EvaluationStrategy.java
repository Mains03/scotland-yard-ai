package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.evaluation;

import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.AiBoard;

public abstract class EvaluationStrategy {
    public static EvaluationStrategy getInstance() {
        return MinimumDistanceEvaluation.getInstance();
    }

    public abstract int evaluate(AiBoard board);
}
