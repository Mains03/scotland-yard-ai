package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;

import java.util.stream.Collectors;

public class AiGameStateAiBoardAdapter extends AiGameStateAdapter implements AiGameState {
    public AiGameStateAiBoardAdapter(AiBoard aiBoard) {
        super(
                aiBoard.getMrX().asPlayer(),
                aiBoard.getDetectives().stream()
                        .map(AiPlayer::asPlayer)
                        .collect(Collectors.toList()));
    }
}
