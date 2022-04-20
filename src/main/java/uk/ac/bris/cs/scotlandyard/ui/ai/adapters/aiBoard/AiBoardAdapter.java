package uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiBoard;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.AiPlayer;
import uk.ac.bris.cs.scotlandyard.ui.ai.adapters.aiPlayer.AiPlayerAdapter;

import java.util.*;
import java.util.stream.Collectors;

public class AiBoardAdapter implements AiBoard {
    private final ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    private final AiPlayer mrX;
    private final List<AiPlayer> detectives;
    private final List<Piece> remaining;

    public AiBoardAdapter(Board board) {
        graph = board.getSetup().graph;
        mrX = createMrX(board);
        detectives = createDetectives(board);
        remaining = createRemaining();
    }

    private AiPlayer createMrX(Board board) {
        Piece piece = Piece.MrX.MRX;
        return new AiPlayerAdapter(board, piece);
    }

    private List<AiPlayer> createDetectives(Board board) {
        List<Piece> pieces = getDetectivePieces(board);
        List<AiPlayer> detectives = new ArrayList<>();
        for (Piece piece : pieces) {
            AiPlayer player = new AiPlayerAdapter(board, piece);
            detectives.add(player);
        }
        return detectives;
    }

    private List<Piece> getDetectivePieces(Board board) {
        return board.getPlayers().stream()
                .filter(Piece::isDetective)
                .collect(Collectors.toList());
    }

    private List<Piece> createRemaining() {
        // called when the class is instantiated by the Ai and so MrX is to move
        return List.of(Piece.MrX.MRX);
    }

    // used when applying a move to the board
    private AiBoardAdapter(
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph,
            AiPlayer mrX, List<AiPlayer> detectives, List<Piece> remaining
    ) {
        this.graph = graph;
        this.mrX = mrX;
        this.detectives = detectives;
        this.remaining = remaining;
    }

    @Override
    public ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> getGraph() {
        return graph;
    }

    @Override
    public AiPlayer getMrX() {
        return mrX;
    }

    @Override
    public List<AiPlayer> getAiDetectives() {
        return List.copyOf(detectives);
    }

    @Override
    public List<Player> getDetectives() {
        return getAiDetectives().stream().map(AiPlayer::asPlayer).toList();
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
            AiPlayer player = null;
            for (AiPlayer detective : detectives) {
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

    private boolean isPiece(AiPlayer aiPlayer, Piece piece) {
        Player player = aiPlayer.asPlayer();
        Piece playerPiece = player.piece();
        return playerPiece.equals(piece);
    }

    private Set<Move> createMoves(AiPlayer player) {
        List<Integer> detectiveLocations = createDetectiveLocations();
        return player.getAvailableMoves(detectiveLocations);
    }

    private List<Integer> createDetectiveLocations() {
        List<Integer> locations = new ArrayList<>();
        for (AiPlayer player : detectives) {
            int location = getPlayerLocation(player);
            locations.add(location);
        }
        return locations;
    }

    private int getPlayerLocation(AiPlayer aiPlayer) {
        Player player = aiPlayer.asPlayer();
        return player.location();
    }

    @Override
    public AiBoard applyMove(Move move) {
        AiPlayer mrX = updateMrX(move);
        List<AiPlayer> detectives = updateDetectives(move);
        List<Piece> remaining = updateRemaining(move);
        return new AiBoardAdapter(graph, mrX, detectives, remaining);
    }

    private AiPlayer updateMrX(Move move) {
        AiPlayer mrX;
        if (isMrXMove(move))
            mrX = movePlayer(this.mrX, move);
        else
            mrX = this.mrX;
        return mrX;
    }

    private boolean isMrXMove(Move move) {
        return move.commencedBy().isMrX();
    }

    private List<AiPlayer> updateDetectives(Move move) {
        List<AiPlayer> detectives = new ArrayList<>();
        for (AiPlayer player : this.detectives) {
            AiPlayer detective;
            if (moveMadeByPlayer(player, move))
                detective = movePlayer(player, move);
            else
                detective = player;
            detectives.add(detective);
        }
        return detectives;
    }

    private boolean moveMadeByPlayer(AiPlayer player, Move move) {
        Piece piece = getPlayerPiece(player);
        Piece movedPiece = move.commencedBy();
        return piece.equals(movedPiece);
    }

    private AiPlayer movePlayer(AiPlayer player, Move move) {
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

    // used when the last remaining player moves
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
        for (AiPlayer player : detectives) {
            Piece piece = getPlayerPiece(player);
            remaining.add(piece);
        }
        return remaining;
    }

    private Piece getPlayerPiece(AiPlayer aiPlayer) {
        Player player = aiPlayer.asPlayer();
        return player.piece();
    }
}
