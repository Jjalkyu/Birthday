# 🎂 Birthday - 생일 관리 웹

> Spring Boot 기반의 간단한 생일 관리 웹앱입니다.  
> 사용자의 생일을 등록하고, 한 달 이내에 다가오는 생일을 **D-Day 형식으로 표시**합니다.

---

## 🚀 프로젝트 소개

**Birthday**는 다음과 같은 기능을 제공합니다:

- ✅ 생일 등록 (이름, 생일 날짜)
- ✅ 전체 생일 목록 보기
- ✅ 생일까지 남은 날짜 `D-`로 표시
- ✅ 생일 수정 & 삭제
- ✅ 한 달 이내 다가오는 생일 상단 알림 표시
- ✅ Bootstrap으로 반응형 UI 구성

---

## 🖼️ 시연 화면

| 생일 목록 페이지 | 생일 등록 폼 |
|------------------|----------------|
| ![list](docs/list.png) | ![form](docs/form.png) |

※ 이미지는 `docs/` 폴더에 넣어 직접 추가해 주세요

---

## 🛠 사용 기술 스택

| 영역 | 기술 |
|------|------|
| Back-end | Spring Boot 3.1, Spring Web, Spring Data JPA |
| Front-end | Mustache, Bootstrap 5 |
| DB | MySQL (PlanetScale 클라우드 연동) |
| 배포 | Render.com |
| 빌드 | Gradle (Java 17) |

---

## 🗂️ 프로젝트 구조

```bash
Birthday
├── src
│   ├── main
│   │   ├── java/com/example/birthday
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   └── resources/
│   │       ├── templates/
│   │       ├── application.properties
├── build.gradle
├── README.md
```

---

## 📦 주요 기능 설명

### 1. 생일 등록

```java
@PostMapping("/save")
public RedirectView saveBirthday(@RequestParam String name,
                                 @RequestParam String birthDate) {
    LocalDate parsedDate = LocalDate.parse(birthDate);
    UserBirthday user = new UserBirthday(name, parsedDate);
    birthdayRepository.save(user);
    return new RedirectView("/");
}
```

---

### 2. 한 달 이내 생일 알림

```java
long daysLeft = ChronoUnit.DAYS.between(today, nextBirthday);
if (daysLeft <= 30) {
    return name + "님의 생일이 D-" + daysLeft + "일 남았습니다!";
}
```

---

## ✅ 실행 방법 (로컬)

```bash
# 1. 프로젝트 클론
git clone https://github.com/Jjalkyu/Birthday.git
cd Birthday

# 2. MySQL 정보 application.properties에 설정

# 3. 실행
./gradlew bootRun
```

---

## 🌍 배포 주소

> 🔗 [https://birthday.onrender.com](https://birthday.onrender.com)  
> (Render 배포 후 주소 등록)

---

## 📌 향후 추가 예정

- 이메일 생일 알림 기능
- REST API 분리 + 모바일 연동
- 사용자 로그인 기능

---

## 👤 개발자

- 정민규 ([@Jjalkyu](https://github.com/Jjalkyu))
- 포트폴리오: [Notion 링크/블로그 주소]

---

## 📄 라이선스

본 프로젝트는 MIT 라이선스를 따릅니다.
