package ludoparty.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ludoparty.model.api.Board;
import ludoparty.model.api.Cell;
import ludoparty.model.api.Cell.CellType;
import ludoparty.model.api.Game;
import ludoparty.model.api.Item;
import ludoparty.model.api.Pawn;
import ludoparty.model.api.Player;
import ludoparty.model.api.Player.PlayerType;
import ludoparty.utils.BColor;
import ludoparty.utils.Constants;
import ludoparty.model.api.Shop;
import ludoparty.model.api.Turn;

/**
 * Game Implementation class.
 */
public final class GameImpl implements Game {

    private final Board board;
    private final Player humanPlayer;
    private final List<Player> players;
    private final Turn turn;
    private final Shop shop;
    private Result gameStatus;
    private Integer[] pawnsNumber;
    private int previousPawnsNumber;

    /**
     * Constructor.
     * 
     * @param playerName    the player name
     * @param playersNumber the number of players
     */
    public GameImpl(final String playerName, final int playersNumber) {

        this.board = new BoardImpl();

        // add players
        this.humanPlayer = new PlayerImpl(playerName, PlayerType.HUMAN,
                BColor.BLUE, CellType.BOTTOM_LEFT_HOUSE, board.getBottomLeftPawnsStartPos());
        final Player p1 = new PlayerImpl("Player2", PlayerType.COMPUTER,
                BColor.GREEN, CellType.TOP_RIGHT_HOUSE, board.getTopRightPawnsStartPos());
        this.players = new ArrayList<>(List.of(this.humanPlayer, p1));

        if (playersNumber > players.size()) {
            final Player p2 = new PlayerImpl("Player3", PlayerType.COMPUTER,
                    BColor.RED, CellType.TOP_LEFT_HOUSE, board.getTopLeftPawnsStartPos());
            final Player p3 = new PlayerImpl("Player4", PlayerType.COMPUTER,
                    BColor.YELLOW, CellType.BOTTOM_RIGHT_HOUSE, board.getBottomRightPawnsStartPos());
            this.players.add(p2);
            this.players.add(p3);
        }

        // create turn
        turn = new TurnImpl(this.humanPlayer);
        shop = new ShopImpl();
        this.gameStatus = Result.PLAY;
        this.pawnsNumber = new Integer[getPlayers().size()]; // BLUE, RED [GREEN, YELLOW]
        this.previousPawnsNumber = Constants.PLAYER_PAWNS - 1;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Set<Cell> getBoardCells() {
        return this.getBoard().getCells();
    }

    @Override
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    @Override
    public Player getHumanPlayer() {
        return this.humanPlayer;
    }

    @Override
    public Turn getTurn() {
        return this.turn;
    }

    @Override
    public Map<Integer, Item> getShowcase() {
        return this.shop.getShowcase();
    }

    @Override
    public void buyItem(final Player player, final Item item) {
       this.shop.sellItem((PlayerImpl) player, item);
    }

    @Override
    public Result getResult() {
        if (isOver()) {
            this.gameStatus = Result.WIN;
        }
        return this.gameStatus;
    }

    @Override
    public boolean isOver() {
        // check if in cell (7,7) there are all pawns of any player
        final List<Pawn> pawns = this.getBoard().getEndCell().getPawns();

        if (pawns.size() >= Constants.PLAYER_PAWNS && this.previousPawnsNumber <= pawns.size()) {
            this.previousPawnsNumber = pawns.size();
            // empty the array
            for (int i = 0; i < this.pawnsNumber.length; i++) {
                this.pawnsNumber[i] = 0;
            }

            for (final var pawn : pawns) {
                if (pawn.getColor() == BColor.BLUE) {
                    this.pawnsNumber[0]++;
                } else if (pawn.getColor() == BColor.GREEN) {
                    this.pawnsNumber[1]++;
                }
                if (getPlayers().size() > Constants.PLAYERS_NUM_2) {
                    if (pawn.getColor() == BColor.RED) {
                        this.pawnsNumber[2]++;
                    } else if (pawn.getColor() == BColor.YELLOW) {
                        this.pawnsNumber[3]++;
                    }
                }
            }
            if (List.of(pawnsNumber).stream().anyMatch(n -> n == Constants.PLAYER_PAWNS)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Shop getShop() {
        return this.shop;
    }

}
