package meli.bootcamp.desafio_spring.entities;

import java.util.ArrayList;
import java.util.List;

public class PaginationResult<T> {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Integer resultCount;
    private List<T> results = new ArrayList<T>();


    public PaginationResult() {}

    public PaginationResult(
            Integer pageNumber, 
            Integer pageSize, 
            Integer totalPages, 
            Integer resultCount,
            List<T> results) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.resultCount = resultCount;
        this.results = results;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public Integer getResultCount() {
        return this.resultCount;
    }

    public List<T> getResults() {
        return this.results;
    }
}
