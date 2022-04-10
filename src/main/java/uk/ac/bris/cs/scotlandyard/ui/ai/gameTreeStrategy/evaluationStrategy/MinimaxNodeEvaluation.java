package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

/**
 * Used to generate an integer evaluation of a node in the game tree for
 * minimax algorithm.
 */
public class MinimaxNodeEvaluation {
    protected static final int POSITIVE_INFINITY = 1000000;
    protected static final int NEGATIVE_INFINITY = -1000000;

    protected final boolean maximise;
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

    /**
     * Creates a new evaluation depending on whether the new is better
     * than the current.
     * @param evaluation new evaluation
     * @return new evaluation state
     */
    public MinimaxNodeEvaluation updateEvaluation(int evaluation) {
        int newEvaluation = determineBetterEvaluation(this.evaluation, evaluation);
        return new MinimaxNodeEvaluation(this, newEvaluation);
    }

    /**
     * Returns the best evaluation depending on whether maximising or minimising
     * @param eval1 first evaluation
     * @param eval2 second evaluation
     * @return the better evaluation
     */
    protected int determineBetterEvaluation(int eval1, int eval2) {
        if (maximise)
            return Math.max(eval1, eval2);
        else
            return Math.min(eval1, eval2);
    }

    /**
     * Determines whether the new evaluation is better than the current
     * depending on whether maximising or minimising.
     * @param evaluation the new evaluation
     * @return whether the new evaluation is better
     */
    protected boolean evaluationBetter(int evaluation) {
        if (maximise)
            return evaluation > this.evaluation;
        else
            return evaluation < this.evaluation;
    }

    public int getEvaluation() { return evaluation; }
}
