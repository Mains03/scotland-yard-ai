package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

public class MinimaxTreeEvaluation extends MinimaxNodeEvaluation {
    private final boolean maximise;
    private final Move bestMove;

    public MinimaxTreeEvaluation(boolean maximise) {
        super(maximise);
        this.maximise = maximise;
        bestMove = null;
    }

    private MinimaxTreeEvaluation(
            MinimaxTreeEvaluation previousEvaluation,
            boolean maximise,
            Move bestMove,
            int newEvaluation
    ) {
        super(previousEvaluation, newEvaluation);
        this.maximise = maximise;
        this.bestMove = bestMove;
    }

    public MinimaxTreeEvaluation updateEvaluation(Move move, int evaluation) {
        Move newBestMove = bestMove;
        if (evaluationBetter(evaluation))
            newBestMove = move;
        int newBestEvaluation = bestEvaluation(super.evaluation, evaluation);
        return new MinimaxTreeEvaluation(
                this, maximise, newBestMove, newBestEvaluation
        );
    }

    private boolean evaluationBetter(int evaluation) {
        if (maximise)
            return evaluation > super.evaluation;
        else
            return evaluation < super.evaluation;
    }

    public Move getBestMove() { return bestMove; }
}
