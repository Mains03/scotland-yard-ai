package uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.model.Board;

import java.util.List;
import java.util.Set;

/**
 * Adapter for {@link Board} providing more functionality.
 */
public interface AiBoard {
    ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph();

    AiPlayer getMrX();

    List<AiPlayer> getDetectives();

    Set<Move> getAvailableMoves();

    AiBoard applyMove(Move move);
}
