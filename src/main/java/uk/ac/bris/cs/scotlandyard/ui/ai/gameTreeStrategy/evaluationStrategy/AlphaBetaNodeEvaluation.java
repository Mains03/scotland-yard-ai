package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

public class AlphaBetaNodeEvaluation extends MinimaxNodeEvaluation {
    private final int alpha;
    private final int beta;

    public AlphaBetaNodeEvaluation(boolean maximise) {
        super(maximise);
        alpha = initialAlpha();
        beta = initialBeta();
    }

    private int initialAlpha() {
        return NEGATIVE_INFINITY;
    }

    private int initialBeta() {
        return POSITIVE_INFINITY;
    }

    public AlphaBetaNodeEvaluation(AlphaBetaNodeEvaluation previousEvaluation, int newEvaluation) {
        super(previousEvaluation, newEvaluation);
        alpha = updateAlpha(previousEvaluation, newEvaluation);
        beta = updateBeta(previousEvaluation, newEvaluation);
    }

    private int updateAlpha(AlphaBetaNodeEvaluation previousEvaluation, int newEvaluation) {
        if (maximise)
            return Math.max(previousEvaluation.alpha, newEvaluation);
        else
            return previousEvaluation.alpha;
    }

    private int updateBeta(AlphaBetaNodeEvaluation previousEvaluation, int newEvaluation) {
        if (maximise)
            return previousEvaluation.beta;
        else
            return Math.min(previousEvaluation.beta, newEvaluation);
    }

    @Override
    public AlphaBetaNodeEvaluation updateEvaluation(int evaluation) {
        int newEvaluation = determineBetterEvaluation(this.evaluation, evaluation);
        return new AlphaBetaNodeEvaluation(this, newEvaluation);
    }

    public boolean shouldPrune() {
        if (maximise)
            return evaluation >= beta;
        else
            return evaluation <= alpha;
    }
}
