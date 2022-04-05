package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

public class MinimaxNodeEvaluation {
    protected static final int POSITIVE_INFINITY = 1000000;
    protected static final int NEGATIVE_INFINITY = -1000000;

    private final boolean maximise;
    protected final int evaluation;

    public MinimaxNodeEvaluation(boolean maximise) {
        this.maximise = maximise;
        evaluation = initialEvaluation(maximise);
    }

    protected MinimaxNodeEvaluation(MinimaxNodeEvaluation previousEvaluation, int newEvaluation) {
        maximise = previousEvaluation.maximise;
        evaluation = newEvaluation;
    }

    private int initialEvaluation(boolean maximise) {
        if (maximise)
            return NEGATIVE_INFINITY;
        else
            return POSITIVE_INFINITY;
    }

    public MinimaxNodeEvaluation updateEvaluation(int evaluation) {
        int newEvaluation = bestEvaluation(this.evaluation, evaluation);
        return new MinimaxNodeEvaluation(this, newEvaluation);
    }

    protected int bestEvaluation(int eval1, int eval2) {
        if (maximise)
            return Math.max(eval1, eval2);
        else
            return Math.min(eval1, eval2);
    }

    public int getEvaluation() { return evaluation; }
}
