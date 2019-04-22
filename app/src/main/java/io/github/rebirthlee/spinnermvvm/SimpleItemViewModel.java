package io.github.rebirthlee.spinnermvvm;

public class SimpleItemViewModel {
    private Person model;
    private OnClickListener<Person> listener;

    public SimpleItemViewModel(Person model, OnClickListener<Person> listener) {
        this.model = model;
        this.listener = listener;
    }

    public void onClick() {
        listener.onClick(model);
    }

    public String getUrl() {
        return model.url;
    }

    public void setUrl(String url) {
        model.url = url;
    }

    public String getTitle() {
        return model.name;
    }

    public void setTitle(String title) {
        model.name = title;
    }
}
