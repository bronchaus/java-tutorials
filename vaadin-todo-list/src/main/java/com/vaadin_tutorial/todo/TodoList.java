package com.vaadin_tutorial.todo;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vaadin.ui.VerticalLayout;

@Component
public class TodoList extends VerticalLayout implements TodoChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Todo> todos;
	@Autowired
	TodoRepository repository;
	
	@PostConstruct
	void init() {
		setSpacing(true);
		
		update();
	}
	
	private void setTodos(List<Todo> todos) {
		this.todos = todos;
		removeAllComponents();
		
		todos.forEach(todo-> {
			addComponent(new TodoLayout(todo, this));
		});
	}

	public void save (Todo todo) {
		repository.save(todo);
		update();
	}
	
	private void update() {
		setTodos(repository.findAll());
	}

	@Override
	public void todoChanged(Todo todo) {
		save(todo);
	}

	public void deleteCompleted() {
		repository.deleteInBatch(
		todos.stream().filter(Todo::isDone).collect(Collectors.toList()));
		
		update();
	}
}
