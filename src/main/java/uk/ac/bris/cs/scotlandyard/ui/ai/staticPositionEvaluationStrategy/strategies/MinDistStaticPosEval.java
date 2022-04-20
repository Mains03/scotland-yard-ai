package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.strategies;

import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.MinDistStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Evaluation is minimum distance between MrX and the detectives.
 */
public class MinDistStaticPosEval implements StaticPosEvalStrategy {
    private static final int POSITIVE_INFINITY = 1000000;

    private final MinDistStrategy strategy;

    public MinDistStaticPosEval(MinDistStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public int evaluate(AiBoard board) {
        int minDist = POSITIVE_INFINITY;
        List<Piece> detectives = getDetectivePieces(board);
        for (Piece piece : detectives) {
            int minDistComparison = minimumDistance(board, piece);
            minDist = Math.min(minDist, minDistComparison);
        }
        return minDist;
    }

    private List<Piece> getDetectivePieces(AiBoard board) {
        List<AiPlayer> detectives = board.getDetectives();
        List<Piece> pieces = new ArrayList<>();
        for (AiPlayer aiDetective : detectives) {
            Player detective = aiDetective.asPlayer();
            pieces.add(detective.piece());
        }
        return pieces;
    }

    private int playerLocation(AiPlayer aiPlayer) {
        Player player = aiPlayer.asPlayer();
        return player.location();
    }

    // minimum distance between MrX and piece
    private int minimumDistance(AiBoard board, Piece piece) {
        Piece mrX = Piece.MrX.MRX;
        return strategy.getMinimumDistance(board, mrX, piece);
    }
}
