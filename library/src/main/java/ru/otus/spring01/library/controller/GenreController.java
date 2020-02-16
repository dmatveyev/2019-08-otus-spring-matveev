package ru.otus.spring01.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring01.library.dto.GenreDto;
import ru.otus.spring01.library.service.GenreService;

import java.util.List;

@Controller
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<GenreDto> allGenres = genreService.getAllGenres();
        model.addAttribute("genres", allGenres);
        return "genres";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        GenreDto genreDto = genreService.findById(id).orElseThrow(NullPointerException::new);
        model.addAttribute("genre", genreDto);
        return "editGenre";
    }

    @PostMapping("/edit")
    public String save(@RequestParam("id") String id, GenreDto genreDtoToSave, Model model) {
        GenreDto save = genreService.save(genreDtoToSave);
        model.addAttribute("genre", save);
        return "redirect:/genres/all";
    }

    @PostMapping("/create")
    public String create(GenreDto genreDtoToSave, Model model) {
        GenreDto genreDto = genreService.create(genreDtoToSave);
        List<GenreDto> allGenres = genreService.getAllGenres();
        model.addAttribute("genres", allGenres);
        return "redirect:/genres/all";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("genre", new GenreDto());
        return "addGenre";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") String id, Model model) {
        genreService.delete(id);
        List<GenreDto> allGenres = genreService.getAllGenres();
        model.addAttribute("genres", allGenres);
        return "redirect:/genres/all";
    }
}
