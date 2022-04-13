package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.aiPlayerData;

import uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.visitors.StaticEvalStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.Objects;

public class AiPlayerDataStaticEval implements StaticEvalStrategy<AiPlayerData> {
    private final StaticPositionEvaluationStrategy evaluationStrategy;

    public AiPlayerDataStaticEval(StaticPositionEvaluationStrategy evaluationStrategy) {
        this.evaluationStrategy = Objects.requireNonNull(evaluationStrategy);
    }

    @Override
    public int staticEvaluation(AiPlayerData data) {
        return evaluationStrategy.evaluate(data);
    }
}
