package com.cicdlectures.menuserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.cicdlectures.menuserver.dto.MenuDto;
import com.cicdlectures.menuserver.service.CreateMenuService;
import com.cicdlectures.menuserver.service.ListMenuService;
import com.cicdlectures.menuserver.repository.MenuRepository;

@RestController
public class MenuController {

  private final CreateMenuService createMenuService;

  private final ListMenuService listMenuService;

  private final MenuRepository menuRepository;

  @JsonProperty("Code")
  private Integer code;

  @Autowired
  MenuController(CreateMenuService createMenuService, ListMenuService listMenuService, MenuRepository menuRepository) {
    this.createMenuService = createMenuService;
    this.listMenuService = listMenuService;
    this.menuRepository = menuRepository;
  }

  @GetMapping(path = "/menus", produces = "application/json")
  public List<MenuDto> listMenus() {
    return listMenuService.listMenus();
  }

  @PostMapping(path = "/menus", produces = "application/json", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public MenuDto createMenu(@RequestBody MenuDto menu) {
    return createMenuService.createMenu(menu);
  }

  @DeleteMapping(path = "/menus/{id}")
  public void deleteMenu(@PathVariable Long id) {
    if(menuRepository.existsById(id)){
      menuRepository.deleteById(id);
    }else{
      throw new IdNotFound(id);
    }
    /*
  if (code.toString() == (HttpStatus.OK).toString()){
      System.out.println("Ok");
    }
  else if (code.toString() == (HttpStatus.NOT_FOUND).toString()){
      System.out.println("Pas trouvé");
    }
  else if (code.toString() == (HttpStatus.INTERNAL_SERVER_ERROR).toString()){
      System.out.println("Aie");
    }*/
  }
}
