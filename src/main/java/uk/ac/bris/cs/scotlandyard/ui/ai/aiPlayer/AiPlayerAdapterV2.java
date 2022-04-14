package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiMove.AiMove;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.MoveGenerationBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.MoveGenerationBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.MoveGenerationFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.moveGeneration.StandardMoveGenerationFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.PlayerFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy.PlayerFactoryAdapterV2;

import java.util.List;
import java.util.Set;

public class AiPlayerAdapterV2 implements AiPlayer {
    private final Player player;

    public AiPlayerAdapterV2(Board board, Piece piece) {
        PlayerFactory playerFactory = new PlayerFactoryAdapterV2();
        player = playerFactory.createFromPiece(board, piece);
    }

    private AiPlayerAdapterV2(Player player) {
        this.player = player;
    }

    @Override
    public int getLocation() {
        return player.location();
    }

    /**
     * Throws UnsupportedOperationException since the method is deprecated.
     */
    @Override
    public ImmutableSet<AiMove> getAvailableMoves() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Move> getAvailableMoves(Board board) {
        MoveGenerationBoard moveGenerationBoard = new MoveGenerationBoardAdapter(board);
        MoveGenerationFactory moveGenerationFactory = StandardMoveGenerationFactory.getInstance();
        return moveGenerationFactory.generateMoves(moveGenerationBoard, player);
    }

    /**
     * Throws UnsupportedOperationException since the method is deprecated.
     */
    @Override
    public AiPlayer applyMove(AiMove move) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AiPlayer applyMove(Move move) {
        Player newPlayer = MoveApplyFactory.getInstance().applyMove(player, move);
        return new AiPlayerAdapterV2(newPlayer);
    }

    @Override
    public Player asPlayer() {
        return player;
    }
}
