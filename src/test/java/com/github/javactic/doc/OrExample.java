package com.github.javactic.doc;

import com.github.javactic.Bad;
import com.github.javactic.Good;
import com.github.javactic.Or;

public class OrExample {

    Or<String, String> parseName(String input) {
        String trimmed = input.trim();
        return (!trimmed.isEmpty()) ? Good.of(trimmed) : Bad.ofString("'{}' is not a valid name", input);
    }

    Or<Integer, String> parseAge(String input) {
        try {
            int age = Integer.parseInt(input.trim());
            return (age >= 0) ? Good.of(age) : Bad.ofString("'{}' is not a valid age", age);
        } catch (NumberFormatException e) {
            return Bad.ofString("'{}' is not a valid integer", input);
        }
    }

    Or<Person, String> parsePerson(String inputName, String inputAge) {
        return parseName(inputName).flatMap(name -> parseAge(inputAge).map(age -> new Person(name, age)));
    }

    void print() {
        Or<Person, String> or = parsePerson("Bridget Jones", "29");
        System.out.println(or);
        // Result: Good(Person(Bridget Jones,29))

        or = parsePerson("Bridget Jones", "");
        System.out.println(or);
        // Result: Bad("" is not a valid integer)

        or = parsePerson("Bridget Jones", "-29");
        // Result: Bad("-29" is not a valid age)

        parsePerson("", "");
        System.out.println(or);
        // Result: Bad("" is not a valid name)
    }
    
    public static void main(String[] args) {
        new OrExample().print();
    }

}