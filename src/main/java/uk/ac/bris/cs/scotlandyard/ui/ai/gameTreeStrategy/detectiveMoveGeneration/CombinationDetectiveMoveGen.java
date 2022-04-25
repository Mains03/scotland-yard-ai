package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.StandardAiBoard;
import uk.ac.bris.cs.scotlandyard.ui.ai.aiBoard.PlayerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CombinationDetectiveMoveGen implements DetectiveMoveGeneration {
    private ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;

    @Override
    public Set<StandardAiBoard> moveDetectives(StandardAiBoard board) {
        List<Player> detectives = createDetectives(board);
        ArrayList<Move> pastMoves = new ArrayList<>(detectives.size());
        graph = board.getSetup().graph;
        return generateBoards(pastMoves, detectives, board);
    }

    private List<Player> createDetectives(StandardAiBoard board) {
        return PlayerFactory.getInstance().createDetectives(board);
    }

    private Set<StandardAiBoard> generateBoards(List<Move> pastMoves, List<Player> detectives, StandardAiBoard aiBoard) {
        // If nobody else to move, make board
        if (detectives.isEmpty()) {
            // Resets to original board then applies moves
            for (Move move : pastMoves) {
                aiBoard = (StandardAiBoard) aiBoard.advance(move);
            }
            HashSet<StandardAiBoard> container = new HashSet<>();
            container.add(aiBoard);
            return container;

        } else {
            // Get detective and available destinations
            Player detective = detectives.get(0);
            // Create new detectives and remove first one
            ArrayList<Player> newDetectives = new ArrayList<>(detectives);
            newDetectives.remove(0);

            // Setup boards to return and get nodes detective can reach
            int source = detective.location();
            Set<StandardAiBoard> boards = new HashSet<>();
            Set<Integer> adjacent = graph.adjacentNodes(source).stream()
                    .filter(
                            dest -> graph.edgeValue(source, dest).get().stream()
                                    .anyMatch(transport -> detective.has(transport.requiredTicket()))
                    ).collect(Collectors.toSet());

            // For each node, ignores method of transport
            // Currently using minDistStrategy that ignores tickets, so we only need final locations
            for (Integer dest : adjacent) {
                Move currentMove = new Move.SingleMove(detective.piece(), source,
                        graph.edgeValue(source, dest).get().stream()
                                .filter(transport -> detective.has(transport.requiredTicket()))
                                .findFirst().get().requiredTicket(),
                        dest);
                // If our destination is another move's source, they must move first
                // Essentially an insertion sort
                int i = pastMoves.size() - 1;
                boolean added = false;
                ArrayList<Move> newMoves = new ArrayList<>(pastMoves);
                while (i >= 0 && ! added) {
                    if (newMoves.get(i).source() == dest) {
                        newMoves.add(i + 1, currentMove);
                        added = true;
                    }
                    i -= 1;
                }
                // If not needed to move after anyone, go first
                if (!added) newMoves.add(0,currentMove);

                // Add all boards from this move
                boards.addAll(generateBoards(newMoves, newDetectives , aiBoard));
            }
            return boards;
        }
    }
}
