package joeuncamp.dabombackend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorDto {
    String field;
    String message;
}
