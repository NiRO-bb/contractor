package com.example.Contractor.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents message saved in outbox table.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutboxMessage {

    private String id;
    private String payload;

}
