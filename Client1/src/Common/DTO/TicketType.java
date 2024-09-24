package Common.DTO;

import java.io.Serializable;

/**
 * Содержит в себе различные типы билетов
 */
public enum TicketType implements Serializable {
    VIP,
    USUAL,
    BUDGETARY,
    CHEAP;
}
