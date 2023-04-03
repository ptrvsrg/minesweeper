package ru.nsu.ccfit.petrov.minesweeper.model.score;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The type {@code Score} is class for saving player name and him game time.
 *
 * @author ptrvsrg
 */
@Getter
@AllArgsConstructor
public class Score
{
    private final String playerName;
    private final int gameTime;

    /**
     * Gets comparator for compare objects of type {@code Score}.
     *
     * @return {@code Score} comparator
     */
    public static Comparator<Score> getComparator() {
        return new Comparator<Score>() {
            /**
             * Compares its two arguments for order.  Returns a negative integer, zero,
             * or a positive integer as the first argument is less than, equal to, or
             * greater than the second.<p>
             * <p>
             * The implementor must ensure that
             * {@code sgn(compare(x, y)) == -sgn(compare(y, x))} for all {@code x} and
             * {@code y}.  (This implies that {@code compare(x, y)} must throw an
             * exception if and only if {@code compare(y, x)} throws an exception.)<p>
             * <p>
             * The implementor must also ensure that the relation is transitive:
             * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
             * {@code compare(x, z)>0}.<p>
             * <p>
             * Finally, the implementor must ensure that {@code compare(x, y)==0}
             * implies that {@code sgn(compare(x, z))==sgn(compare(y, z))} for all
             * {@code z}.<p>
             * <p>
             * It is generally the case, but <i>not</i> strictly required that
             * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking, any
             * comparator that violates this condition should clearly indicate this
             * fact.  The recommended language is "Note: this comparator imposes
             * orderings that are inconsistent with equals."<p>
             * <p>
             * In the foregoing description, the notation
             * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
             * <i>signum</i> function, which is defined to return one of {@code -1},
             * {@code 0}, or {@code 1} according to whether the value of
             * <i>expression</i> is negative, zero, or positive, respectively.
             *
             * @param o1 the first object to be compared.
             * @param o2 the second object to be compared.
             * @return a negative integer, zero, or a positive integer as the first
             * argument is less than, equal to, or greater than the second.
             * @throws NullPointerException if an argument is null and this comparator
             *                              does not permit null arguments
             * @throws ClassCastException   if the arguments' types prevent them from
             *                              being compared by this comparator.
             */
            @Override
            public int compare(Score o1, Score o2) {
                if (o1.getGameTime() == o2.getGameTime()) {
                    return o1.getPlayerName().compareTo(o2.getPlayerName());
                }

                return Integer.compare(o1.getGameTime(), o2.getGameTime());
            }
        };
    }
}