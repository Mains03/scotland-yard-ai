package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.ui.ai.BestMoveStrategy;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceAlgorithm.MinimumDistanceWithTickets;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance.DijkstraWithTickets;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.minimumDistance.MinimumDistance;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.MinimumDistanceStaticPositionEvaluation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of best move strategy which simply considers the minimum distance
 * between the detectives and MrX for each move MrX can make. The best move is the one
 * which maximises this distance.
 */
public class MinimumDistanceStrategy extends SingleTurnLookAheadStrategy {
    public MinimumDistanceStrategy(Board board) {
        super(
                board,
                new MinimumDistanceStaticPositionEvaluation(
                        new MinimumDistanceWithTickets(board.getSetup().graph)
                )
        );
        Objects.requireNonNull(board);
    }

    @Override
    public Move determineBestMove() {
        return super.determineBestMove();
    }
}
