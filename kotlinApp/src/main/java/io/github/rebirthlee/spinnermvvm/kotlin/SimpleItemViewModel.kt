package io.github.rebirthlee.spinnermvvm.kotlin

class SimpleItemViewModel(private val model: Person, private val listener: OnClickListener<Person>) {

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
        listener.onClick(model)
    }
}
