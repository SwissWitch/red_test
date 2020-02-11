package red.test.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import red.test.model.Department;
import red.test.model.Position;

@Route(value = "emp_edit")
public class EmpEditView extends VerticalLayout {
    private TextField name = new TextField("Имя");
    private TextField surname = new TextField("Отчество");
    private TextField familyname = new TextField("Фамилия");
    private Select<Department> department = new Select<>();
    private Select<Position> position = new Select<>();
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    HorizontalLayout actions = new HorizontalLayout(save, cancel);

    private EmpEditView.ChangeHandler changeHandler;

    public TextField getName() {
        return name;
    }
    public TextField getSurname() {
        return surname;
    }
    public TextField getFamilyname() {
        return familyname;
    }
    public Select<Department> getDepartment() {
        return department;
    }
    public Select<Position> getPosition() {
        return position;
    }

    public void setChangeHandler(EmpEditView.ChangeHandler h) {
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
    public EmpEditView(){

        add(name, surname, familyname, department, position, actions);

        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }
}
