package main.game.external;

import com.google.gson.Gson;
import main.game.Game;
import main.game.GameController;
import main.game.PieceNotSpawnedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ExternalPlayer implements Runnable {
    private static final String HOST = "localhost";
    private static final int PORT = 3000;

    private final Game game;
    private final GameController controller;

    private int pieceNo = 0;
    private OpMove dest;
    private int rotationsDone = 0;

    public ExternalPlayer(Game game, GameController controller) {
        this.game = game;
        this.controller = controller;
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
            System.out.println(pureGameJson);

            String response = in.readLine();
            OpMove move = gson.fromJson(response, OpMove.class);
            System.out.println(response);

            dest = move;
            rotationsDone = 0;
        } catch (PieceNotSpawnedException e) {
            dest = null;
        }
    }

    @Override
    public synchronized void run() {
        try {
            System.out.println(game.getState());
            while (!this.game.isFinished()) {
                if (game.getPiecesSpawned() > this.pieceNo) {
                    askServer();
                    pieceNo = game.getPiecesSpawned();
                }

                applyMove();

                try {
                    //noinspection BusyWait
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void applyMove() {
        if (dest != null) {
            var ap = game.getBoard().getActivePiece();
            int x = ap.x();
            if (dest.opX() >= 0) {
                if (x > dest.opX()) {
                    controller.shiftLeft();
                } else if (x < dest.opX()) {
                    controller.shiftRight();
                } else {
                    controller.hardDrop();
                }
            }

            if (rotationsDone < dest.opRotate()) {
                controller.rotateClockwise();
                rotationsDone++;
            }
        }
    }

}
