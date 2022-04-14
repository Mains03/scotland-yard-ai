package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMoveAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.AiGameStateAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPositionEvaluationStrategy;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Looks ahead one move and finds the best move.
 */
public class SingleTurnLookAheadStrategy implements BestMoveStrategy {
    private static final int NEGATIVE_INFINITY = -10000000;

    private final Board board;
    private final StaticPositionEvaluationStrategy evaluationStrategy;

    public SingleTurnLookAheadStrategy(
            Board board,
            StaticPositionEvaluationStrategy evaluationStrategy
    ) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(evaluationStrategy);
        this.board = board;
        this.evaluationStrategy = evaluationStrategy;
    }

    @Override
    public Move determineBestMove() {
        AiBoard aiBoard = new AiBoardAdapter(board);
        AiPlayer mrX = aiBoard.getMrX();
        List<Player> detectives = aiBoard.getDetectives().stream()
                .map(AiPlayer::asPlayer)
                .collect(Collectors.toList());
        Move bestMove = null;
        int bestMoveEvaluation = NEGATIVE_INFINITY;
        for (Move move : board.getAvailableMoves()) {
            Player mrXPlayer = mrX.applyMove(
                    new AiMoveAdapter(move)
            ).asPlayer();
            int evaluation = evaluationStrategy.evaluate(new AiGameStateAdapter(
                    mrXPlayer,
                    detectives
            ));
            if (evaluation > bestMoveEvaluation) {
                bestMoveEvaluation = evaluation;
                bestMove = move;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No MrX moves");
        return bestMove;
    }
}
