package io.github.rebirthlee.spinnermvvm.kotlin

object PersonMapper {

    fun map(person: Person, listener: OnClickListener<Person>): SimpleItemViewModel {
        return SimpleItemViewModel(person, listener)
    }
}
