package com.example.taskManager.infrastructure.task.dtos;

public class TaskSearchFiltersDTO {
    private String  auth0Id;
    private String  title;
    private Boolean completed;
    private int     page;
    private int     size;
    private String  sort;

    public TaskSearchFiltersDTO() {}

    public TaskSearchFiltersDTO(String auth0Id, String title, Boolean completed, int page, int size, String sort) {
        this.auth0Id = auth0Id;
        this.title = title;
        this.completed = completed;
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public String getAuth0Id() { return auth0Id; }
    public void setAuth0Id(String auth0Id) { this.auth0Id = auth0Id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public String getSort() { return sort; }
    public void setSort(String sort) { this.sort = sort; }
    
}