package ua.tartemchuk.usermanagement.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageWrapper<T> {

    private int totalPages;
    private List<T> items;

}
