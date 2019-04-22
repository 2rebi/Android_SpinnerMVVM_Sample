package io.github.rebirthlee.spinnermvvm;

public final class PersonMapper {
    private PersonMapper() {

    }

    public static SimpleItemViewModel map(Person person, OnClickListener<Person> listener) {
        return new SimpleItemViewModel(person, listener);
    }
}
