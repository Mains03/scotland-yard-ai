package uk.ac.bris.cs.scotlandyard.ui.ai.singleTurnLookAheadStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.StaticPosEvalStrategy;

import java.util.NoSuchElementException;
import java.util.Objects;

public class SingleTurnLookAheadStrategy implements BestMoveStrategy {
    private static final int NEGATIVE_INFINITY = -10000000;

    private final StaticPosEvalStrategy strategy;

    public SingleTurnLookAheadStrategy(StaticPosEvalStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public Move determineBestMove(Board board) {
        Move bestMove = null;
        int bestMoveEvaluation = NEGATIVE_INFINITY;
        for (Move move : board.getAvailableMoves()) {
            int evaluation = evaluateMove(board, move);
            if (evaluation > bestMoveEvaluation) {
                bestMove = move;
                bestMoveEvaluation = evaluation;
            }
        }
        if (bestMove == null)
            throw new NoSuchElementException("No MrX moves");
        return bestMove;
    }

    private int evaluateMove(Board board, Move move) {
        AiBoard aiBoard = new AiBoardAdapter(board);
        AiBoard newAiBoard = aiBoard.applyMove(move);
        return staticEvaluation(newAiBoard);
    }

    private int staticEvaluation(AiBoard board) {
        return strategy.evaluate(board);
    }
}
