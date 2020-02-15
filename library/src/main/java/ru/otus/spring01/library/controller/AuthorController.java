package ru.otus.spring01.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring01.library.dto.AuthorDto;
import ru.otus.spring01.library.service.AuthorService;

import java.util.List;

@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<AuthorDto> list = authorService.getAllAuthors();
        model.addAttribute("authors", list);
        return "authors";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        AuthorDto authorDto = authorService.findById(id).orElseThrow(NullPointerException::new);
        model.addAttribute("author", authorDto);
        return "editAuthor";
    }

    @PostMapping("/edit")
    public String save(@RequestParam("id") String id, AuthorDto genreDtoToSave, Model model) {
        AuthorDto save = authorService.save(genreDtoToSave);
        model.addAttribute("author", save);
        return "redirect:/authors/all";
    }

    @PostMapping("/create")
    public String create(AuthorDto genreDtoToSave, Model model) {
        AuthorDto authorDto = authorService.create(genreDtoToSave);
        List<AuthorDto> allGenres = authorService.getAllAuthors();
        model.addAttribute("author", allGenres);
        return "redirect:/authors/all";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new AuthorDto());
        return "addAuthor";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") String id, Model model) {
        authorService.delete(id);
        List<AuthorDto> list = authorService.getAllAuthors();
        model.addAttribute("authors", list);
        return "redirect:/authors/all";
    }
}
