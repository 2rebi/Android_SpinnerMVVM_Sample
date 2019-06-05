package io.github.rebirthlee.spinnermvvm.kotlin

class SimpleItemViewModel(private val model: Person, private val listener: (model: Person) -> Unit) {

    var url: String
        get() = model.url
        set(url) {
            model.url = url
        }

    var title: String
        get() = model.name
        set(title) {
            model.name = title
        }

    fun onClick() {
        listener(model)
    }
}
