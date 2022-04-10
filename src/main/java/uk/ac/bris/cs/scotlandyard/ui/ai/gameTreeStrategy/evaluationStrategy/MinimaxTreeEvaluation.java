package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Objects;

/**
 * Stores information about the evaluation of a node in the game tree for
 * minimax evaluation.
 */
public class MinimaxTreeEvaluation extends MinimaxNodeEvaluation {
    private final Move bestMove;

    public MinimaxTreeEvaluation(boolean maximise) {
        super(maximise);
        bestMove = null;
    }

    private MinimaxTreeEvaluation(
            MinimaxTreeEvaluation previousEvaluation,
            Move bestMove,
            int newEvaluation
    ) {
        super(previousEvaluation, newEvaluation);
        this.bestMove = bestMove;
    }

    /**
     * Creates a new evaluation given a move and its evaluation
     * @param move the move made
     * @param evaluation evaluation of the move
     * @return the new evaluation state
     */
    public MinimaxTreeEvaluation updateEvaluation(Move move, int evaluation) {
        Objects.requireNonNull(move);
        Move newBestMove = bestMove;
        int newBestEvaluation = super.evaluation;
        if (evaluationBetter(evaluation)) {
            newBestMove = move;
            newBestEvaluation = evaluation;
        }
        return new MinimaxTreeEvaluation(
                this, newBestMove, newBestEvaluation
        );
    }



    public Move getBestMove() { return bestMove; }
}
