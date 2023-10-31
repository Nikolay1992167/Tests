package utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Constants {
    public static final UUID PRODUCT_UUID = UUID.fromString("76a4a999-92d7-452f-9a7b-34607ecb688e");
    public static final String PRODUCT_NAME = "Printer";
    public static final String PRODUCT_DESCRIPTION = "Provides multi-colored printing.";
    public static final BigDecimal PRODUCT_PRICE = new BigDecimal(5);
    public static final LocalDateTime PRODUCT_CREAT_DATE = LocalDateTime.of(2023, 10, 27, 18, 30, 0);
    public static final String UPDATE_PRODUCT_NAME = "MFU";
    public static final String UPDATE_PRODUCT_DESCRIPTION = "Provides multi-color printing and document scanning.";
    public static final BigDecimal UPDATE_PRODUCT_PRICE = new BigDecimal(7);
}

