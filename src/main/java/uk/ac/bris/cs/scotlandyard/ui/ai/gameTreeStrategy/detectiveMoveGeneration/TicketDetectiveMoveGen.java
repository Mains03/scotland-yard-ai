package uk.ac.bris.cs.scotlandyard.ui.ai.gameTreeStrategy.detectiveMoveGeneration;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard.AiBoard;

import java.util.*;

public class TicketDetectiveMoveGen implements DetectiveMoveGeneration{
    private ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private Set<AiBoard> finalBoards;

    public TicketDetectiveMoveGen() {
        finalBoards = new HashSet<>();
    }

    @Override
    public Set<AiBoard> moveDetectives(AiBoard aiBoard) {
        ArrayList<Player> detectives = new ArrayList<>(aiBoard.getDetectives());
        ArrayList<Move> pastMoves = new ArrayList<>(detectives.size());
        graph = aiBoard.getGraph();
        finalBoards.clear();
        generateCombinations(pastMoves, detectives, aiBoard);
        return finalBoards;
    }


    private void generateCombinations(ArrayList<Move> pastMoves, List<Player> detectives, AiBoard aiBoard) {

        // Generate moves

        if (detectives.isEmpty()) {
            // Resets to original board then applies moves
            AiBoard board = aiBoard;
            for (Move move : pastMoves) {
                board.applyMove(move);
            }
            finalBoards.add(board);
        }
    }
}
