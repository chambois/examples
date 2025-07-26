package com.example.domain;

public class Course {
    private Long id;
    private String title;

    public Course(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    // equals/hashCode based on ID
    @Override
    public boolean equals(Object o) {
        return o instanceof Course && ((Course) o).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
