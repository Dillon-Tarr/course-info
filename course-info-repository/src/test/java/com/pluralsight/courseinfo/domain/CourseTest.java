package com.pluralsight.courseinfo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseTest {

    @Test
    void rejectNullComponents() {
        assertThrows(IllegalArgumentException.class, () ->
                new Course(null, null, 1, null));
    }

    @Test
    void rejectBlankNotes() {
        assertThrows(IllegalArgumentException.class, () ->
                new Course("1", "", 1, "url"));
        assertThrows(IllegalArgumentException.class, () ->
                new Course("", "Two", 1, "url"));
    }

}
