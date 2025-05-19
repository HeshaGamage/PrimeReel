package org.movie.project_001.datastructures;

import org.movie.project_001.models.Movie;

public class MovieStack {
    private Movie[] stack;
    private int top;

    public MovieStack(int capacity) {
        stack = new Movie[capacity];
        top = -1;
    }

    public void push(Movie movie) {
        if (top == stack.length - 1) {
            System.out.println("Stack Overflow: Cannot push " + movie.getTitle());
            return;
        }
        stack[++top] = movie;
    }

    public Movie pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow: Nothing to pop");
            return null;
        }
        return stack[top--];
    }

    public Movie peek() {
        if (isEmpty()) return null;
        return stack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    public Movie[] getAll() {
        Movie[] current = new Movie[size()];
        for (int i = 0; i < current.length; i++) {
            current[i] = stack[i];
        }
        return current;
    }
}
