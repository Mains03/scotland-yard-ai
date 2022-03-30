package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoardAdapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class AiGameStateAdapter implements AiGameState {
    private final Player mrX;
    private final List<Player> detectives;

    public AiGameStateAdapter(Board board) {
        AiBoard aiBoard = new AiBoardAdapter(board);
        mrX = aiBoard.getMrX().asPlayer();
        detectives = aiBoard.getDetectives().stream()
                .map(AiPlayer::asPlayer)
                .collect(Collectors.toList());
    }

    @Override
    public Player getMrX() {
        return mrX;
    }

    @Override
    public List<Player> getDetectives() {
        return ImmutableList.copyOf(detectives);
    }
}
