package com.booking.show.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class ShowResponseDTO {
    private Long id;
    private Long movieId;
    private Long theaterId;
    private Long screenId;
    private LocalDateTime startTime;
    private BigDecimal price;
    private List<String> seatNumbers;
}

