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
    private ArrayList<ArrayList<Move>> moves;
    private ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;

    @Override
    public Set<AiBoard> moveDetectives(AiBoard aiBoard) {
        ArrayList<Player> detectives = new ArrayList<>(aiBoard.getDetectives());
        ArrayList<Move> pastMoves = new ArrayList<>(detectives.size());
        graph = aiBoard.getGraph();
        moves.clear();
        generateCombinations(pastMoves, detectives);
        return makeAiBoards(aiBoard);
    }


    private void generateCombinations(ArrayList<Move> pastMoves, List<Player> detectives) {

        if (detectives.isEmpty()) {
            //add final moves
        } else {
            // Generate moves
        }
    }

    private Set<AiBoard> makeAiBoards(AiBoard aiBoard) {
        // Store results
        Set<AiBoard> newBoards = new HashSet<>();
        for (ArrayList<Move> moveSet : moves) {
            // Resets to original board then applies moves
            AiBoard board = aiBoard;
            for (Move move : moveSet) {
                board.applyMove(move);
            }
            newBoards.add(board);
        }
    }
}
