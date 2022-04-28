package uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.gameTree.evaluation.alphaBeta;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.Optional;

public class AlphaBetaData {
    private final Optional<Move> mMove;

    private final int evaluation;

    private final int alpha;

    private final int beta;

    public AlphaBetaData(Optional<Move> mMove, int evaluation, int alpha, int beta) {
        this.mMove = mMove;
        this.evaluation = evaluation;
        this.alpha = alpha;
        this.beta = beta;
    }

    public AlphaBetaData(AlphaBetaData data) {
        mMove = data.mMove;
        evaluation = data.evaluation;
        alpha = data.alpha;
        beta = data.beta;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public Optional<Move> getMove() {
        return mMove;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getBeta() {
        return beta;
    }
}
