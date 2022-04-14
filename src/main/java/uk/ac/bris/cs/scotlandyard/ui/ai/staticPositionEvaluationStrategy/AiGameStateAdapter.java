package uk.ac.bris.cs.scotlandyard.ui.ai.staticPositionEvaluationStrategy;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory.PlayerFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory.PlayerFactoryAdapter;

import java.util.List;
import java.util.Objects;

/**
 * @deprecated Deprecated since uses deprecated classes.
 */
@Deprecated
public class AiGameStateAdapter implements AiGameState {
    private final Player mrX;
    private final List<Player> detectives;

    public AiGameStateAdapter(Player mrX, List<Player> detectives) {
        this.mrX = Objects.requireNonNull(mrX);
        this.detectives = Objects.requireNonNull(detectives);
    }

    public AiGameStateAdapter(Board board) {
        PlayerFactory playerFactory = new PlayerFactoryAdapter(board);
        mrX = playerFactory.createMrX();
        detectives = playerFactory.createDetectives();
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
