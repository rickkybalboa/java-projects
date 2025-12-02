package bankaccount;

import java.math.*;
import java.util.Objects;

public class Helpers {
    
    // #region BigDecimal utilities
    public static BigDecimal roundToScale(double input, int scale) {
    return roundToScale(BigDecimal.valueOf(input), scale);
}

public static BigDecimal roundToScale(BigDecimal input, int scale) {
    Objects.requireNonNull(input, "input BigDecimal cannot be null");
    return input.setScale(scale, RoundingMode.HALF_UP);
}
    // #endregion

    // #region I/O helpers
    public static void print(String message) {
        System.out.println(message);
    }
    //#endregion
}
