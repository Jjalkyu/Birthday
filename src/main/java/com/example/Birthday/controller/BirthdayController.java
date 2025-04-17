package com.example.Birthday.controller;

import com.example.Birthday.entity.UserBirthday;
import com.example.Birthday.repository.UserBirthdayRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.Model;
import com.example.Birthday.dto.UserBirthdayResponse;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BirthdayController {

    // 홈 화면: 생일 리스트 보여주는 곳 (home.mustache)


    // 생일 등록 폼 페이지 (form.mustache)
    @GetMapping("/new")
    public String showForm() {
        return "form";
    }

    private final UserBirthdayRepository birthdayRepository;

    public BirthdayController(UserBirthdayRepository birthdayRepository) {
        this.birthdayRepository = birthdayRepository;
    }

    // 기존 페이지 이동 메서드 생략 가능

    // ✅ 생일 등록 처리 (POST)
    @PostMapping("/save")
    public RedirectView saveBirthday(@RequestParam String name,
                                     @RequestParam String birthDate) {
        // 문자열 날짜를 LocalDate로 변환
        LocalDate parsedDate = LocalDate.parse(birthDate);

        // 엔티티 생성 및 저장
        UserBirthday user = new UserBirthday(name, parsedDate);
        birthdayRepository.save(user);

        // 저장 후 홈으로 리다이렉트
        return new RedirectView("/");
    }

    @GetMapping("/")
    public String home(Model model) {
        List<UserBirthday> allBirthdays = birthdayRepository.findAll();
        LocalDate today = LocalDate.now();

        // ✅ 전체 목록 가공 (D-Day 포함)
        List<UserBirthdayResponse> birthdayList = allBirthdays.stream()
                .map(user -> {
                    LocalDate nextBirthday = user.getBirthDate().withYear(today.getYear());
                    if (nextBirthday.isBefore(today)) {
                        nextBirthday = nextBirthday.plusYears(1);
                    }
                    long days = ChronoUnit.DAYS.between(today, nextBirthday);
                    String dDayText = (days == 0) ? "D-Day" : "D-" + days;

                    return new UserBirthdayResponse(user.getId(), user.getName(), user.getBirthDate(), dDayText);
                })
                .toList();

        // ✅ 알림용 리스트 생성 (30일 이내만)
        List<String> upcomingBirthdays = allBirthdays.stream()
                .map(user -> {
                    LocalDate nextBirthday = user.getBirthDate().withYear(today.getYear());
                    if (nextBirthday.isBefore(today)) {
                        nextBirthday = nextBirthday.plusYears(1);
                    }
                    long daysLeft = ChronoUnit.DAYS.between(today, nextBirthday);
                    if (daysLeft <= 30) {
                        return "🎉 " + user.getName() + "님의 생일이 " + daysLeft + "일 남았습니다!";
                    } else {
                        return null;
                    }
                })
                .filter(msg -> msg != null)
                .collect(Collectors.toList());

        model.addAttribute("birthdays", birthdayList);         // 테이블용 데이터
        model.addAttribute("alerts", upcomingBirthdays);       // 상단 알림 메시지용 데이터
        return "home";
    }


    @GetMapping("/delete/{id}")
    public String deleteBirthday(@PathVariable Long id) {
        birthdayRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        UserBirthday user = birthdayRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 ID"));
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/update")
    public String updateBirthday(@RequestParam Long id,
                                 @RequestParam String name,
                                 @RequestParam String birthDate) {
        UserBirthday user = birthdayRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 ID"));
        user.setName(name);
        user.setBirthDate(LocalDate.parse(birthDate));
        birthdayRepository.save(user);
        return "redirect:/";
    }

}
