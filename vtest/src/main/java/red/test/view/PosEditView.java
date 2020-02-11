package red.test.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "pos_edit")
public class PosEditView extends VerticalLayout {
    private TextField name = new TextField("Название");
    private TextField salary = new TextField("Зарплата");
    private Checkbox chief = new Checkbox();

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    HorizontalLayout actions = new HorizontalLayout(save, cancel);

    private PosEditView.ChangeHandler changeHandler;

    public TextField getName() {
        return name;
    }
    public TextField getSalary() {
        return salary;
    }
    public Checkbox getChief() {
        return chief;
    }

    public void setChangeHandler(PosEditView.ChangeHandler h) {
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
    public PosEditView(){
        chief.setLabel("Начальник");
        add(name, salary, chief , actions);

        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }
}
