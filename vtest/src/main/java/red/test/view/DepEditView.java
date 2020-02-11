package red.test.view;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import red.test.model.Department;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "dep_edit")

public class DepEditView extends VerticalLayout {
    private TextField name = new TextField("Название");
    private TextField phone = new TextField("Телефон");
    private TextField email = new TextField("Email");
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    HorizontalLayout actions = new HorizontalLayout(save, cancel);

    private ChangeHandler changeHandler;

    public TextField getName() {
        return name;
    }
    public TextField getPhone() {
        return phone;
    }
    public TextField getEmail() {
        return email;
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    private void save() {
        changeHandler.onSave();
    }

    private void cancel() {
        setVisible(false);
    }

    public interface ChangeHandler {
        void onSave();
    }
    public DepEditView(){

        add(name, phone, email, actions);

        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }
}
