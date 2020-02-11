package red.test.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import red.test.dao.PositionDAO;
import red.test.dao.impl.PositionDAOImpl;
import red.test.model.Position;
import red.test.util.DataSource;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;

@Route(value = "position")
public class PositionView extends VerticalLayout {
    private PosEditView editVew;
    private DataSource ds;
    private PositionDAO posdao;
    private Position selected;
    private boolean isNew;
    private Grid<Position> grid;

    private HorizontalLayout routerLink() {
        HorizontalLayout menu = new HorizontalLayout();
        menu.add(new RouterLink("Отделы", MainView.class));
        menu.add(new RouterLink("Должности", PositionView.class));
        menu.add(new RouterLink("Сотрудники", EmployeeView.class));
        menu.setSpacing(true);
        menu.add();
        return menu;
    }

    public class ChangeHandlerIml implements PosEditView.ChangeHandler {

        public void onSave() {
            if(editVew.getName().getValue().equals("")) {
                Notification.show("Нужно ввести название!");
                return;
            }
            if(editVew.getSalary().getValue().equals("")) {
                Notification.show("Нужно ввести зарплату!");
                return;
            }
            editVew.setVisible(false);
            Position position = new Position();
            position.setName(editVew.getName().getValue());
            position.setSalary(Integer.parseInt(editVew.getSalary().getValue()));
            position.setChief(editVew.getChief().getValue());
            if(isNew) {
                posdao.insertPosition(position);
            } else {
                position.setPosId(selected.getPosId());
                posdao.updatePosition(position);
            }
            List<Position> emps = posdao.getAllPositions();
            grid.setItems(emps);
        }
    }

    public PositionView() {

        try {
            ds = new DataSource();

            editVew = new PosEditView();
            PositionView.ChangeHandlerIml changeHandler = new PositionView.ChangeHandlerIml();
            editVew.setChangeHandler(changeHandler);

            posdao = new PositionDAOImpl();
            posdao.setDataSource(ds);
            List<Position> poss = posdao.getAllPositions();

            HorizontalLayout menu = routerLink();

            Header h1 = new Header();
            h1.add("Должности");

            grid = new Grid<>();
            grid.addColumn(Position::getName).setHeader("Название");
            grid.addColumn(Position::getSalary).setHeader("Зарплата");
            grid.setColumnReorderingAllowed(true);
            grid.setItems(poss);
            grid.setHeightByRows(true);
            grid.setSelectionMode(Grid.SelectionMode.SINGLE);

            Button newBtn = new Button("Новая должность");
            newBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            newBtn.addClickListener( e-> {
                editVew.getName().setValue("");
                editVew.getSalary().setValue("");
                isNew = true;
                editVew.setVisible(true);
            });

            Button editBtn = new Button("Редактировать");
            editBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editBtn.setEnabled(false);
            editBtn.addClickListener( e-> {
                Set<Position> sel = grid.getSelectedItems();
                if(sel.iterator().hasNext()) {
                    selected = sel.iterator().next();
                    editVew.getName().setValue(selected.getName());
                    editVew.getSalary().setValue(Integer.toString(selected.getSalary()));
                    editVew.getChief().setValue(selected.isChief());
                    isNew = false;
                    editVew.setVisible(true);
                }
            });

            Button delBtn = new Button("Удалить");
            delBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            delBtn.setEnabled(false);
            delBtn.addClickListener( e-> {
                Set<Position> sel = grid.getSelectedItems();
                if(sel.iterator().hasNext()) {
                    Position selpos = sel.iterator().next();
                    posdao.deletePosition(selpos);
                    List<Position> emps = posdao.getAllPositions();
                    grid.setItems(emps);
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
