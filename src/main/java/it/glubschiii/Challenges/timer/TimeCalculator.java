package it.glubschiii.Challenges.timer;

/** @author Glubschiii | https://github.com/glubschiii
 @since 1.0.2
 */
public class TimeCalculator {

    public static String format(int time, String result) {

        if(!result.contains("s")) {

            if (!Timer.isRunning()) {
                result = "Der Timer ist pausiert";
            } else if (time <= 59) {
                result = format(0,result + time + "s </bold></gradient>");
            } else if (time <= 3599) {

                int m = (int) Math.floor(time / 60);
                int rest = time - m * 60;
                result = format(rest,result + m + "m ");

            } else if (time <= 86399) {

                int h = (int) Math.floor(time / (60 * 60));
                int rest = time - h * 60 * 60;
                result = format(rest,result + h + "h ");

            } else {

                int d = (int) Math.floor(time / (24 * 60 * 60));
                int rest = time - d * 24 * 60 * 60;
                result = format(rest,result + d + "d ");

            }
        }
        return result;
    }
}
