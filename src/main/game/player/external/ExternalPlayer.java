package main.game.player.external;

import com.google.gson.Gson;
import main.Tetris;
import main.game.Game;
import main.game.PieceNotSpawnedException;
import main.game.core.Cell;
import main.game.core.Rotation;
import main.game.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ExternalPlayer extends Player implements Runnable {
    private static final String HOST = "localhost";
    private static final int PORT = 3000;

    private int pieceNo = 0;
    private OpMove dest;
    private boolean errored = false;

    public ExternalPlayer(Game game) {
        super(game);
    }

    private synchronized void askServer() throws IOException {
        Gson gson = new Gson();
        try (
                Socket socket = new Socket(HOST, PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String pureGameJson = gson.toJson(this.game.serialize());
            out.println(pureGameJson);

            String response = in.readLine();
            dest = gson.fromJson(response, OpMove.class);
        } catch (PieceNotSpawnedException e) {
            dest = null;
        } catch (IOException e) {
            Tetris.instance.showErrorBox("External Server Error", "Error communicating with external server. Make sure it is running before starting.");
            errored = true;
        }
    }

    @Override
    public synchronized void run() {
        try {
            while (this.game.isNotFinished() && !errored) {
                if (game.getPiecesSpawned() > this.pieceNo) {
                    askServer();
                    pieceNo = game.getPiecesSpawned();
                }

                if (this.game.inProgress()) { applyMove(); }

                try {
                    //noinspection BusyWait
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void applyMove() {
        if (dest == null) {
            return;
        }

        var active = game.getBoard().getActivePiece();
        if (active.rotation().equals(Rotation.North) && dest.opRotate() == 3) {
            controller.rotateCounterclockwise();
            return;
        }
        if (active.rotation().ordinal() < dest.opRotate()) {
            controller.rotateClockwise();
            return;
        }

        int minX = active.getCells().stream().mapToInt(Cell::x).min().orElseThrow();
        if (minX > dest.opX()) {
            controller.shiftLeft();
            return;
        }
        if (minX < dest.opX()) {
            controller.shiftRight();
            return;
        }

        controller.hardDrop();
    }

    @Override
    public void start() {
        super.start();
        Thread externalThread = new Thread(this);
        externalThread.start();
    }
}
