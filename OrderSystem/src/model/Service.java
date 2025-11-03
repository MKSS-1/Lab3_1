package model;

import static resources.Constants.*;
import static resources.Messages.*;

public class Service extends Item {
    private final int persons;
    private final int hours;

    public Service(String name, int persons, int hours) {
        super(name);
        this.persons = persons;
        this.hours = hours;
    }

    @Override
    public int getPrice() {
		return COSTS_PER_HOUR * hours * persons;
	}

    @Override
    public String toString() {
        return persons + PERSONS + hours + HOURS + getName();
    }
}
