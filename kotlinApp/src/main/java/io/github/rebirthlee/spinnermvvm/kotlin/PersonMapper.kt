package io.github.rebirthlee.spinnermvvm.kotlin

object PersonMapper {

    fun map(person: Person, listener: (model: Person) -> Unit): SimpleItemViewModel {
        return SimpleItemViewModel(person, listener)
    }
}
