package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.List;
import java.util.Objects;


public class AiGameStateAdapter implements AiGameState {
    private final Player mrX;
    private final List<Player> detectives;

    public AiGameStateAdapter(Player mrX, List<Player> detectives) {
        Objects.requireNonNull(mrX);
        Objects.requireNonNull(detectives);
        this.mrX = mrX;
        this.detectives = detectives;
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
