package joeuncamp.dabombackend.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class RandomStringGenerator {

    final static int ASCII_UPPER_A = 65;
    final static int ASCII_UPPER_Z = 90;
    final static int ASCII_LOWER_A = 97;
    final static int ASCII_LOWER_Z = 122;
    final static String CHARACTER = "!";
    final static int ASCII_POSTFIX_END = 35;
    final static int ZERO = 0;
    final static int NINE = 9;

    /**
     * 임시 비밀번호를 생성합니다.
     * 대문자(2자) + 소문자(8자) + 숫자(3자) + 특수문자(1자) 조합입니다.
     *
     * @return 임시 비밀번호
     */
    public String generatePassword() {
        Random random = new Random();
        String prefix = random.ints(ASCII_UPPER_A, ASCII_UPPER_Z)
                .limit(2)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String middle = random.ints(ASCII_LOWER_A, ASCII_LOWER_Z)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String numbers = random.ints(ZERO, NINE)
                .limit(3)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return prefix + middle + numbers + CHARACTER;
    }
}
