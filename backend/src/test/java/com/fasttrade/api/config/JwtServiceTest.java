package com.fasttrade.api.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwt =
            new JwtService("test-secret-that-is-at-least-32-bytes-long!!", 60_000);

    @Test
    void roundTripsSubject() {
        String token = jwt.generate("ana@x.com", "USER");
        assertEquals("ana@x.com", jwt.subject(token));
    }

    @Test
    void rejectsTamperedToken() {
        String token = jwt.generate("ana@x.com", "USER");
        assertNull(jwt.subject(token + "x"));
    }

    @Test
    void rejectsForeignSignature() {
        String token = new JwtService("another-secret-also-32-bytes-long-yes!!!", 60_000)
                .generate("ana@x.com", "USER");
        assertNull(jwt.subject(token));
    }
}
