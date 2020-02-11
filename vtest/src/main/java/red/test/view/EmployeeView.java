package red.test.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import red.test.dao.DepartmentDAO;
import red.test.dao.EmployeeDAO;
import red.test.dao.PositionDAO;
import red.test.dao.impl.DepartmentDAOImpl;
import red.test.dao.impl.EmployeeDAOImpl;
import red.test.dao.impl.PositionDAOImpl;
import red.test.model.Department;
import red.test.model.Employee;
import red.test.model.Position;
import red.test.util.DataSource;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;

@Route(value = "employee")
public class EmployeeView extends VerticalLayout {
    private EmpEditView editVew;
    private DataSource ds;
    private EmployeeDAO empdao;
    private Employee selected;
    private boolean isNew;
    private Grid<Employee> grid;

    private HorizontalLayout routerLink() {
        HorizontalLayout menu = new HorizontalLayout();
        menu.add(new RouterLink("Отделы", MainView.class));
        menu.add(new RouterLink("Должности", PositionView.class));
        menu.add(new RouterLink("Сотрудники", EmployeeView.class));
        menu.setSpacing(true);
        menu.add();
        return menu;
    }

    public class ChangeHandlerIml implements EmpEditView.ChangeHandler {

        public void onSave() {
            if(editVew.getPosition().getValue() == null) {
                Notification.show("Нужно выбрать должность!");
                return;
            }
            if(editVew.getDepartment().getValue() == null) {
                Notification.show("Нужно выбрать отдел!");
                return;
            }
            if(editVew.getName().getValue().equals("")) {
                Notification.show("Нужно ввести имя!");
                return;
            }
            if(editVew.getSurname().getValue().equals("")) {
                Notification.show("Нужно ввести отчество!");
                return;
            }
            if(editVew.getFamilyname().getValue().equals("")) {
                Notification.show("Нужно ввести фамилию!");
                return;
            }
            Employee employee = new Employee();
            employee.setName(editVew.getName().getValue());
            employee.setSurname(editVew.getSurname().getValue());
            employee.setFamilyname(editVew.getFamilyname().getValue());
            employee.setPosition(editVew.getPosition().getValue());
            employee.setDepartment(editVew.getDepartment().getValue());
            // chief should be unique for department
            PositionDAO pos = new PositionDAOImpl();
            pos.setDataSource(ds);
            if(pos.checkChief(employee)) {
                Notification.show("Место начальника уже занято!");
            } else {
                editVew.setVisible(false);
                if(isNew) {
                    empdao.insertEmployee(employee);
                } else {
                    employee.setEmpId(selected.getEmpId());
                    empdao.updateEmployee(employee);
                }
                List<Employee> emps = empdao.getAllEmployees();
                grid.setItems(emps);
            }
        }
    }

    private void initSelects(Select<Department> seldep, Select<Position> selpos, boolean isnew) {

        seldep.setLabel("Отдел");
        DepartmentDAO depdao = new DepartmentDAOImpl();
        depdao.setDataSource(ds);
        List<Department> deps = depdao.getAllDepartments();
        seldep.setItemLabelGenerator(Department::getName);
        seldep.setItems(deps);
        seldep.setPlaceholder("Выберите отдел");
        selpos.setLabel("Должность");
        PositionDAO posdao = new PositionDAOImpl();
        posdao.setDataSource(ds);
        List<Position> poss = posdao.getAllPositions();
        selpos.setItemLabelGenerator(Position::getName);
        selpos.setItems(poss);
        selpos.setPlaceholder("Выберите должность");
        if(!isnew) {
            for(Department d:deps) {
                if(d.getDepId() == selected.getDepartment().getDepId()) {
                    seldep.setValue(d);
                }
            }
            for(Position p:poss) {
                if(p.getPosId() == selected.getPosition().getPosId()) {
                    selpos.setValue(p);
                }
            }
        }
    }

    public EmployeeView() {

        try {
            ds = new DataSource();

            editVew = new EmpEditView();
            EmployeeView.ChangeHandlerIml changeHandler = new EmployeeView.ChangeHandlerIml();
            editVew.setChangeHandler(changeHandler);

            empdao = new EmployeeDAOImpl();
            empdao.setDataSource(ds);

            List<Employee> emps = empdao.getAllEmployees();

            HorizontalLayout menu = routerLink();

            Header h1 = new Header();
            h1.add("Сотрудники");

            grid = new Grid<>();
            grid.addColumn(Employee::getName).setHeader("Имя");
            grid.addColumn(Employee::getSurname).setHeader("Отчество");
            grid.addColumn(Employee::getFamilyname).setHeader("Фамилия");
            grid.addColumn(Employee::getDepartmentname).setHeader("Отдел");
            grid.addColumn(Employee::getPositionname).setHeader("Должность");
            grid.setColumnReorderingAllowed(true);
            grid.setItems(emps);
            grid.setHeightByRows(true);
            grid.setSelectionMode(Grid.SelectionMode.SINGLE);

            Button newBtn = new Button("Новый сотрудник");
            newBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            newBtn.addClickListener( e-> {
                editVew.getName().setValue("");
                editVew.getSurname().setValue("");
                editVew.getFamilyname().setValue("");
                Select<Department> seldep = editVew.getDepartment();
                Select<Position> selpos = editVew.getPosition();
                initSelects(seldep, selpos, true);
                isNew = true;
                editVew.setVisible(true);
            });

            Button editBtn = new Button("Редактировать");
            editBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editBtn.setEnabled(false);
            editBtn.addClickListener( e-> {
                Set<Employee> sel = grid.getSelectedItems();
                if(sel.iterator().hasNext()) {
                    selected = sel.iterator().next();
                    editVew.getName().setValue(selected.getName());
                    editVew.getSurname().setValue(selected.getSurname());
                    editVew.getFamilyname().setValue(selected.getFamilyname());

                    Select<Department> seldep = editVew.getDepartment();
                    Select<Position> selpos = editVew.getPosition();
                    initSelects(seldep, selpos, false );

                    isNew = false;
                    editVew.setVisible(true);
                }
            });

            Button delBtn = new Button("Удалить");
            delBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            delBtn.setEnabled(false);
            delBtn.addClickListener( e-> {
                Set<Employee> sel = grid.getSelectedItems();
                if(sel.iterator().hasNext()) {
                    Employee selemp = sel.iterator().next();
                    empdao.deleteEmployee(selemp);
                    List<Employee> empss = empdao.getAllEmployees();
                    grid.setItems(empss);
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
    }
}
