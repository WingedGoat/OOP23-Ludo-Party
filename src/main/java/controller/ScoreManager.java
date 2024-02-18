package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Score manager (Singleton).
 */
public final class ScoreManager {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String SCORES_FILE_PATH = System.getProperty("user.home") + System.getProperty("file.separator") 
        + "ludo-score.txt";

    private static ScoreManager instance;

    private final List<Pair<String, Integer>> scores;

    private ScoreManager() {
        this.scores = loadScore();
    }

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
     * Load scores from file.
     * 
     * @return a {@link Pair} list of String, Integer (name, score)
     */
    @SuppressWarnings("unchecked")
    private List<Pair<String, Integer>> loadScore() {
        List<Pair<String, Integer>> load = new ArrayList<>();

        final File f = new File(SCORES_FILE_PATH);

        try {
            if (f.createNewFile()) {
                try (ObjectInputStream ostream = 
                        new ObjectInputStream(new BufferedInputStream(new FileInputStream(SCORES_FILE_PATH)))) {
                    load = (List<Pair<String, Integer>>) ostream.readObject();
                } catch (final IOException e) {
                    LOGGER.error("Error on IO: " + e.getMessage());
                } catch (final ClassNotFoundException e) {
                    LOGGER.error("Error on: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error: " + e.getMessage());
        }

        return load;
    }

    /**
     * Adds a score to the specified player.
     * 
     * @param name  the player name
     * @param score the player score
     */
    public void saveScore(final String name, final Integer score) {
        this.scores.add(new Pair<>(name, score));

        try (ObjectOutputStream ostream = 
                new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(SCORES_FILE_PATH)))) {
            ostream.writeObject(this.scores);
        } catch (final FileNotFoundException e) {
            LOGGER.error("Error on FileNotFound: " + e.getMessage());
        } catch (final IOException e) {
            LOGGER.error("Error on: " + e.getMessage());
        }
    }

    /**
     * Gets the scores, ordered by score value.
     * 
     * @return lista di Pair(String, Integer)
     */
    public List<Pair<String, Integer>> getScores() {
        Collections.sort(this.scores, (s1, s2) -> s1.getY() - s2.getY());
        return List.copyOf(this.scores);
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
