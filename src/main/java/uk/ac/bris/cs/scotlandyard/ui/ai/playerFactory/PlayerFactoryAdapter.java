package uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.deprecated.minimumDistanceStrategy.aiBoard.AiBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiPlayer.AiPlayerAdapter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @deprecated Uses {@link AiPlayerAdapter}
 * which is deprecated, use {@link PlayerFactoryAdapterV2}.
 */
@Deprecated
public class PlayerFactoryAdapter implements PlayerFactory {
    private final Player mrX;
    private final List<Player> detectives;

    public PlayerFactoryAdapter(Board board) {
        AiBoard aiBoard = new AiBoardAdapter(board);
        mrX = aiBoard.getMrX().asPlayer();
        detectives = aiBoard.getDetectives().stream()
                .map(AiPlayer::asPlayer)
                .collect(Collectors.toList());
    }

    @Override
    public Player createMrX() {
        return mrX;
    }

    @Override
    public List<Player> createDetectives() {
        return List.copyOf(detectives);
    }

    @Override
    public Player createFromPiece(Piece piece) {
        return null;
    }
}
