package tn.esprit.wellness.entity.utils;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForbiddenWords {
    public static List<String> forbiddenWords = Arrays.asList("xxx","Test","badword");
}
