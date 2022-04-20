package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard.AiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AiGameStateAdapter implements AiGameState {
    private final Player mrX;
    private final List<Player> detectives;

    public AiGameStateAdapter(Player mrX, List<Player> detectives) {
        this.mrX = Objects.requireNonNull(mrX);
        this.detectives = Objects.requireNonNull(detectives);
    }

    public AiGameStateAdapter(AiBoard board) {
        mrX = createMrX(board);
        detectives = createDetectives(board);
    }

    private Player createMrX(AiBoard board) {
        AiPlayer aiPlayerV2 = board.getMrX();
        return aiPlayerV2.asPlayer();
    }

    private List<Player> createDetectives(AiBoard board) {
        List<AiPlayer> aiDetectives = board.getDetectives();
        List<Player> detectives = new ArrayList<>();
        for (AiPlayer aiDetective : aiDetectives) {
            Player detective = aiDetective.asPlayer();
            detectives.add(detective);
        }
        return detectives;
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
