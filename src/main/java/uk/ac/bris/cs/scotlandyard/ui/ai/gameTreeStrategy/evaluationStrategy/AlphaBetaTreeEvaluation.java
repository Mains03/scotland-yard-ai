package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.evaluationStrategy;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Objects;

/**
 * Stores information about the evaluation of a node in the game tree for
 * alpha-beta pruning
 */
public class AlphaBetaTreeEvaluation extends AlphaBetaNodeEvaluation {
    private final Move bestMove;

    public AlphaBetaTreeEvaluation(boolean maximise) {
        super(maximise);
        bestMove = null;
    }

    private AlphaBetaTreeEvaluation(
            AlphaBetaTreeEvaluation previousEvaluation,
            Move bestMove,
            int newEvaluation
    ) {
        super(previousEvaluation, newEvaluation);
        this.bestMove = bestMove;
    }

    public AlphaBetaTreeEvaluation updateEvaluation(Move move, int evaluation) {
        Objects.requireNonNull(move);
        Move newBestMove = bestMove;
        int newEvaluation = super.evaluation;
        if (evaluationBetter(evaluation)) {
            newBestMove = move;
            newEvaluation = evaluation;
        }
        return new AlphaBetaTreeEvaluation(
                this, newBestMove, newEvaluation
        );
    }
}
