package ludoparty.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Score manager (Singleton).
 */
@SuppressWarnings("all")
public final class ScoreManager {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String SCORES_FILE_PATH = System.getProperty("user.home") + System.getProperty("file.separator") 
        + "ludo-score.txt";

    private static ScoreManager instance;

    private List<Pair<String, Integer>> scores = new ArrayList<>();

    private ScoreManager() { }

    /**
     * Gets the instance of the score manager.
     * 
     * @return the instance of the score manager
     */
    public static ScoreManager getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ScoreManager();
        }
        return instance;
    }

    /**
     * Adds a score to the specified player.
     * 
     * @param name  the player name
     * @param score the player score
     * @throws IOException the IO exception
     * @throws FileNotFoundException the file not found exception
     */
    public void saveScore(final String name, final Integer score) throws FileNotFoundException, IOException {
        this.loadScore();
        this.scores.add(new Pair<>(name, score));
        Collections.sort(this.scores, (s1, s2) -> s2.getY() - s1.getY());

        try (FileOutputStream outputStream = new FileOutputStream(SCORES_FILE_PATH)) {
            try {
                final StringBuilder sb = new StringBuilder();
                for (final var sc : this.scores) {
                    sb.append(sc.getX()).append(' ').append(sc.getY()).append('\n');
                }
                final byte[] strToBytes = sb.toString().getBytes(Charset.forName("UTF-8"));
                outputStream.write(strToBytes);
            } catch (final FileNotFoundException e) {
                LOGGER.error("Error on FileNotFound: " + e.getMessage());
            } catch (final IOException e) {
                LOGGER.error("Error on: " + e.getMessage());
            }
        }
    }

    /**
     * Load scores from file.
     * @throws IOException the IO exception
     */
    private void loadScore() throws IOException {

        final File file = new File(SCORES_FILE_PATH);
        if (file.exists()) {
            try {
                final InputStream inputStream = new FileInputStream(file);
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        final String[] nameScore = line.split(" ");
                        this.scores.add(new Pair<String, Integer>(nameScore[0], Integer.valueOf(nameScore[1])));
                    }
                }
            } catch (FileNotFoundException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("The file not exists.");
        }
    }


    /**
     * A standard generic Pair<X,Y>, with getters, hashCode, equals, and toString.
     * 
     * @param <X> the x value
     * @param <Y> the y value
     */
    private static class Pair<X, Y> implements Serializable {

        private static final long serialVersionUID = 1234567L;

        private final X x;
        private final Y y;

        /**
         * Constructor.
         * 
         * @param x the x value
         * @param y the y value
         */
        Pair(final X x, final Y y) {
            super();
            this.x = x;
            this.y = y;
        }

        /**
         * Gets the x value.
         * 
         * @return the x value
         */
        @SuppressWarnings("unused")
        public X getX() {
            return x;
        }

        /**
         * Gets the y value.
         * 
         * @return the y value
         */
        public Y getY() {
            return y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((x == null) ? 0 : x.hashCode());
            result = prime * result + ((y == null) ? 0 : y.hashCode());
            return result;
        }

        @Override
        @SuppressWarnings("rawtypes")
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pair other = (Pair) obj;
            if (x == null) {
                if (other.x != null) {
                    return false;
                }
            } else if (!x.equals(other.x)) {
                return false;
            }
            if (y == null) {
                if (other.y != null) {
                    return false;
                }
            } else if (!y.equals(other.y)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Pair [x=" + x + ", y=" + y + "]";
        }

    }

}
