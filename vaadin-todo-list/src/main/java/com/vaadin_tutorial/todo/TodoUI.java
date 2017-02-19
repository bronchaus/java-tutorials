package com.vaadin_tutorial.todo;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.converters.ShortcutKeyMapper;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@Theme("valo")
public class TodoUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VerticalLayout layout;

	@Autowired
	TodoList todoList;
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setupLayout();
		addHeader();
		addForm();
		addTodoList();
		addActionButton();
	}

	private void setupLayout() {
		layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		setContent(layout);
	}

	private void addHeader() {
		Label header = new Label("TODO");
		header.addStyleName(ValoTheme.LABEL_H1);
		header.setSizeUndefined();
		layout.addComponent(header);
	}

	private void addForm() {
		HorizontalLayout formLayout = new HorizontalLayout();
		formLayout.setSpacing(true);
		formLayout.setWidth("80%");
		TextField taskField = new TextField();
		taskField.setWidth("100%");
		Button addButton = new Button("");
		addButton.setIcon(FontAwesome.PLUS);
		addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		
		formLayout.addComponents(taskField, addButton);
		formLayout.setExpandRatio(taskField, 1);
		layout.addComponent(formLayout);
		
		addButton.addClickListener(click -> {
			todoList.save(new Todo(taskField.getValue()));
			taskField.clear();
			taskField.focus();
		});
		
		taskField.focus();
		
		addButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	}
	
	private void addTodoList() {
		todoList.setWidth("80%");
		layout.addComponent(todoList);
	}

	private void addActionButton() {
		Button deleteButton = new Button("Delete completed");
		deleteButton.addClickListener(click->todoList.deleteCompleted());
		layout.addComponent(deleteButton);
	}
}
