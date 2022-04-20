package uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiBoard;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayerV2;
import uk.ac.bris.cs.scotlandyard.ui.ai.minimumDistanceStrategy.aiPlayer.AiPlayerV2Adapter;
import uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory.PlayerFactory;
import uk.ac.bris.cs.scotlandyard.ui.ai.playerFactory.PlayerFactoryV2Adapter;

import java.util.*;

public class AiBoardV2Adapter implements AiBoardV2 {
    private final AiPlayerV2 mrX;
    private final List<AiPlayerV2> detectives;
    private final List<Piece> remaining;

    public AiBoardV2Adapter(Board board) {
        mrX = createMrX(board);
        detectives = createDetectives(board);
        remaining = createRemaining();
    }

    private AiPlayerV2 createMrX(Board board) {
        var graph = getGraph(board);
        PlayerFactory playerFactory = createPlayerFactory();
        Player mrX = playerFactory.createMrX(board);
        return new AiPlayerV2Adapter(graph, mrX);
    }

    private List<AiPlayerV2> createDetectives(Board board) {
        List<Player> detectivePlayers = createDetectivePlayers(board);
        List<AiPlayerV2> detectives = new ArrayList<>();
        for (Player player : detectivePlayers) {
            AiPlayerV2 aiDetective = createDetective(board, player);
            detectives.add(aiDetective);
        }
        return detectives;
    }

    private List<Player> createDetectivePlayers(Board board) {
        PlayerFactory playerFactory = createPlayerFactory();
        return playerFactory.createDetectives(board);
    }

    private AiPlayerV2 createDetective(Board board, Player detective) {
        var graph = getGraph(board);
        return new AiPlayerV2Adapter(graph, detective);
    }

    private ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph(Board board) {
        return board.getSetup().graph;
    }

    private PlayerFactory createPlayerFactory() {
        return new PlayerFactoryV2Adapter();
    }

    private List<Piece> createRemaining() {
        // called when the class is instantiated by the Ai and so MrX is to move
        return List.of(Piece.MrX.MRX);
    }

    // used to apply a move to the board
    private AiBoardV2Adapter(AiPlayerV2 mrX, List<AiPlayerV2> detectives, List<Piece> remaining) {
        this.mrX = mrX;
        this.detectives = detectives;
        this.remaining = remaining;
    }

    @Override
    public AiPlayerV2 getMrX() {
        return mrX;
    }

    @Override
    public List<AiPlayerV2> getDetectives() {
        return List.copyOf(detectives);
    }

    @Override
    public Set<Move> getAvailableMoves() {
        Set<Move> moves = new HashSet<>();
        for (Piece piece : this.remaining) {
            Set<Move> pieceMoves = createMoves(piece);
            moves.addAll(pieceMoves);
        }
        return moves;
    }

    private Set<Move> createMoves(Piece piece) {
        Set<Move> moves;
        if (piece.isMrX())
            moves = createMrXMoves();
        else {
            AiPlayerV2 player = null;
            for (AiPlayerV2 detective : detectives) {
                if (isPiece(detective, piece))
                    player = detective;
            }
            if (player == null)
                throw new NoSuchElementException("Player " + piece + " not found");
            moves = createMoves(player);
        }
        return moves;
    }

    private Set<Move> createMrXMoves() {
        return createMoves(mrX);
    }

    private boolean isPiece(AiPlayerV2 aiPlayer, Piece piece) {
        Player player = aiPlayer.asPlayer();
        Piece playerPiece = player.piece();
        return playerPiece.equals(piece);
    }

    private Set<Move> createMoves(AiPlayerV2 player) {
        List<Integer> detectiveLocations = createDetectiveLocations();
        return player.getAvailableMoves(detectiveLocations);
    }

    private List<Integer> createDetectiveLocations() {
        List<Integer> locations = new ArrayList<>();
        for (AiPlayerV2 player : detectives) {
            int location = getPlayerLocation(player);
            locations.add(location);
        }
        return locations;
    }

    private int getPlayerLocation(AiPlayerV2 aiPlayer) {
        Player player = aiPlayer.asPlayer();
        return player.location();
    }

    @Override
    public AiBoardV2 applyMove(Move move) {
        AiPlayerV2 mrX = updateMrX(move);
        List<AiPlayerV2> detectives = updateDetectives(move);
        List<Piece> remaining = updateRemaining(move);
        return new AiBoardV2Adapter(mrX, detectives, remaining);
    }

    private AiPlayerV2 updateMrX(Move move) {
        AiPlayerV2 mrX;
        if (isMrXMove(move))
            mrX = movePlayer(this.mrX, move);
        else
            mrX = this.mrX;
        return mrX;
    }

    private boolean isMrXMove(Move move) {
        return move.commencedBy().isMrX();
    }

    private List<AiPlayerV2> updateDetectives(Move move) {
        List<AiPlayerV2> detectives = new ArrayList<>();
        for (AiPlayerV2 player : this.detectives) {
            AiPlayerV2 detective;
            if (moveMadeByPlayer(player, move))
                detective = movePlayer(player, move);
            else
                detective = player;
            detectives.add(detective);
        }
        return detectives;
    }

    private boolean moveMadeByPlayer(AiPlayerV2 player, Move move) {
        Piece piece = getPlayerPiece(player);
        Piece movedPiece = move.commencedBy();
        return piece.equals(movedPiece);
    }

    private AiPlayerV2 movePlayer(AiPlayerV2 player, Move move) {
        return player.applyMove(move);
    }

    private List<Piece> updateRemaining(Move move) {
        List<Piece> remaining = removeMovedRemainingPiece(move);
        if (remaining.size() == 0)
            remaining = recreateRemaining(move);
        return remaining;
    }

    private List<Piece> removeMovedRemainingPiece(Move move) {
        Piece movedPiece = move.commencedBy();
        List<Piece> remaining = new ArrayList<>();
        for (Piece piece : this.remaining) {
            if (!movedPiece.equals(piece))
                remaining.add(piece);
        }
        return remaining;
    }

    private List<Piece> recreateRemaining(Move move) {
        List<Piece> remaining;
        if (isMrXMove(move))
            remaining = List.of(Piece.MrX.MRX);
        else
            remaining = createDetectivesRemaining();
        return remaining;
    }

    private List<Piece> createDetectivesRemaining() {
        List<Piece> remaining = new ArrayList<>();
        for (AiPlayerV2 player : detectives) {
            Piece piece = getPlayerPiece(player);
            remaining.add(piece);
        }
        return remaining;
    }

    private Piece getPlayerPiece(AiPlayerV2 aiPlayer) {
        Player player = aiPlayer.asPlayer();
        return player.piece();
    }
}
