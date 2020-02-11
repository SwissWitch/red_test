package red.test.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import red.test.dao.DepartmentDAO;
import red.test.dao.impl.DepartmentDAOImpl;
import red.test.model.Department;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;
import red.test.util.DataSource;

import java.util.*;

@Route(value = "")
@PWA(name = "Red Application", shortName = "Red Application")
public class MainView extends VerticalLayout {
    private DepEditView editVew;
    private DataSource ds;
    private DepartmentDAO depdao;
    private Department selected;
    private boolean isNew;
    private Grid<Department> grid;

    private HorizontalLayout routerLink() {
        HorizontalLayout menu = new HorizontalLayout();
        menu.add(new RouterLink("Отделы", MainView.class));
        menu.add(new RouterLink("Должности", PositionView.class));
        menu.add(new RouterLink("Сотрудники", EmployeeView.class));
        menu.setSpacing(true);
        menu.add();
        return menu;
    }

    public class ChangeHandlerIml implements DepEditView.ChangeHandler {

        public void onSave() {
            if(editVew.getName().getValue().equals("")) {
                Notification.show("Нужно ввести название!");
                return;
            }
            if(editVew.getPhone().getValue().equals("")) {
                Notification.show("Нужно ввести телефон!");
                return;
            }
            if(editVew.getEmail().getValue().equals("")) {
                Notification.show("Нужно ввести email!");
                return;
            }
            editVew.setVisible(false);
            Department department = new Department();
            department.setName(editVew.getName().getValue());
            department.setPhone(editVew.getPhone().getValue());
            department.setEmail(editVew.getEmail().getValue());
            if(isNew) {
                depdao.insertDepartment(department);
            } else {
                department.setDepId(selected.getDepId());
                depdao.updateDepartment(department);
            }
            List<Department> depps = depdao.getAllDepartments();
            grid.setItems(depps);
        }
    }

    public MainView() {

        try {
            ds = new DataSource();

            editVew = new DepEditView();
            ChangeHandlerIml changeHandler = new ChangeHandlerIml();
            editVew.setChangeHandler(changeHandler);

            depdao = new DepartmentDAOImpl();
            depdao.setDataSource(ds);
            List<Department> deps = depdao.getAllDepartments();

            HorizontalLayout menu = routerLink();

            Header h1 = new Header();
            h1.add("Отделы");

            grid = new Grid<>();
            grid.addColumn(Department::getName).setHeader("Название");
            grid.addColumn(Department::getPhone).setHeader("Телефон");
            grid.addColumn(Department::getEmail).setHeader("Email");
            grid.setColumnReorderingAllowed(true);
            grid.setItems(deps);
            grid.setHeightByRows(true);
            grid.setSelectionMode(Grid.SelectionMode.SINGLE);

            Button newBtn = new Button("Новый отдел");
            newBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            newBtn.addClickListener( e-> {
                editVew.getName().setValue("");
                editVew.getPhone().setValue("");
                editVew.getEmail().setValue("");
                isNew = true;
                editVew.setVisible(true);
            });

            Button editBtn = new Button("Редактировать");
            editBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editBtn.setEnabled(false);
            editBtn.addClickListener( e-> {
                Set<Department> sel = grid.getSelectedItems();
                if(sel.iterator().hasNext()) {
                    selected = sel.iterator().next();
                    editVew.getName().setValue(selected.getName());
                    editVew.getPhone().setValue(selected.getPhone());
                    editVew.getEmail().setValue(selected.getEmail());
                    isNew = false;
                    editVew.setVisible(true);
                }
            });

            Button delBtn = new Button("Удалить");
            delBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            delBtn.setEnabled(false);
            delBtn.addClickListener( e-> {
                Set<Department> sel = grid.getSelectedItems();
                if(sel.iterator().hasNext()) {
                    Department seldep = sel.iterator().next();
                    depdao.deleteDepartment(seldep);
                    List<Department> depps = depdao.getAllDepartments();
                    grid.setItems(depps);
                }
            });

            grid.addSelectionListener(e -> {
                delBtn.setEnabled(!grid.getSelectedItems().isEmpty());
                editBtn.setEnabled(!grid.getSelectedItems().isEmpty());
            });

            HorizontalLayout buttons = new HorizontalLayout();
            buttons.setSpacing(true);
            buttons.add(newBtn, editBtn, delBtn);

            add(menu, h1, grid, buttons, editVew);

        } catch (MissingResourceException e) {
            Notification.show("Настройте параметры подключения к базе в файле config.properties");
        }
/*
        Button addBtn = new Button("Добавить отдел",
                event -> Notification.show("Clicked!"));
        add(addBtn);
*/
    }
}
