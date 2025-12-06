package com.shyam.dto.response;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTotalOrderMonthResponse implements Serializable {
    private Long count;
}
