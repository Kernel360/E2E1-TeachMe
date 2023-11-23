<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/69861207/e4536173-ba9a-4c97-9a03-165a2e21ddbe" width="800"/>
</div>

## Teach Me! 📚

<div align="center">
강의 추천 플랫폼 Teach Me 입니다!<br/>
온라인 강의 플랫폼이 많아 강의를 비교하는 데 어려움이 있어 Teach Me를 개발하게 되었습니다.<br/>
Teach Me를 통해 여러 플랫폼의 강의를 한 곳에 모아 비교할 수 있습니다.<br/>
최신순, 가격순 정렬과 키워드, 강사명 검색 등의 기능을 제공합니다.<br/>

[ -> Teach Me! 바로가기 🍀](https://teach-me.live/)

</div>

## 팀원 구성

<div align="center">
</table>
<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/stoneHee99"><img src="https://avatars.githubusercontent.com/u/101683784?v=4" alt="" width="200" height="200"/><br /><sub><b> 박석희 </b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/yejincode"><img src="https://avatars.githubusercontent.com/u/69861207?v=4" alt="" width="200" height="200"/><br /><sub><b> 송예진 </b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/minson96"><img src="https://avatars.githubusercontent.com/u/118032886?v=4" alt="" width="200" height="200"/><br /><sub><b> 손민우 </b></sub></a><br /></td>
    </tr>
  </tbody>
</table>
</div>

## 기술 스택

<div align="center">
<div>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">
  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
</div>
<div>
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
  <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white">
</div>
<div>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">
  <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">
  <img src="https://img.shields.io/badge/Github Actions-2088FF?style=for-the-badge&logo=Github Actions&logoColor=white">
</div>
<div>
  <img src="https://img.shields.io/badge/Amazon CloudWatch-FF4F8B?style=for-the-badge&logo=Amazon CloudWatch&logoColor=white">
  <img src="https://img.shields.io/badge/AWS Lambda-FF9900?style=for-the-badge&logo=AWS Lambda&logoColor=white">
</div>
</div>

## 개발 환경
- MySQL 8.1.0
- Java 11
- Spring 1.0.15.RELEASE
- Spring Boot 2.7.17
- amazon linux 2023
- Docker

## 시스템 아키텍처

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/215c2b75-8c79-4bf0-82e6-2af8beefd96c" width="800">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/69861207/75c68093-e892-403b-adc2-034159920d13" width="500">
</div>

## DB ERD

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/7dd7c9f6-9e8b-4b8b-8934-b7c8bf242f9f" width="800">
</div>

## API 명세서
https://teach-me.live/swagger-ui/

## 프로젝트 구조


```
./
├── main/
│         ├── generated/
│         ├── java/
│         │         └── kr/
│         │             ├── kernel/
│         │             │         └── teachme/
│         │             │             ├── TeachmeApplication.java
│         │             │             ├── common/
│         │             │             │         ├── annotation/
│         │             │             │         │         └── LogExecutionTime.java
│         │             │             │         ├── aop/
│         │             │             │         │         └── LogAspect.java
│         │             │             │         ├── config/
│         │             │             │         │         ├── Appconfig.java
│         │             │             │         │         ├── InitializeDefaultConfig.java
│         │             │             │         │         ├── PasswordEncoderConfig.java
│         │             │             │         │         ├── QuerydslConfiguration.java
│         │             │             │         │         ├── SpringSecurityConfig.java
│         │             │             │         │         ├── SwaggerConfig.java
│         │             │             │         │         └── UrlConfig.java
│         │             │             │         ├── exception/
│         │             │             │         │         ├── AlreadyRegisteredMemberException.java
│         │             │             │         │         ├── CrawlerException.java
│         │             │             │         │         ├── CustomAuthenticationFailureHandler.java
│         │             │             │         │         ├── GlobalExceptionHandler.java
│         │             │             │         │         ├── MemberNotFoundException.java
│         │             │             │         │         ├── NotOwnerForReviewException.java
│         │             │             │         │         └── ReviewNotFoundException.java
│         │             │             │         ├── jwt/
│         │             │             │         │         ├── JwtAuthenticationFilter.java
│         │             │             │         │         ├── JwtAuthorizationFilter.java
│         │             │             │         │         ├── JwtKey.java
│         │             │             │         │         ├── JwtProperties.java
│         │             │             │         │         ├── JwtUtils.java
│         │             │             │         │         └── SigningKeyResolver.java
│         │             │             │         └── util/
│         │             │             │             ├── DateUtil.java
│         │             │             │             └── StringUtil.java
│         │             │             └── domain/
│         │             │                 ├── crawler/
│         │             │                 │         ├── component/
│         │             │                 │         │         ├── AutoCrawler.java
│         │             │                 │         │         ├── FastcampusAutoCrawler.java
│         │             │                 │         │         └── InflearnAutoCrawler.java
│         │             │                 │         ├── controller/
│         │             │                 │         │         └── CrawlerController.java
│         │             │                 │         ├── dto/
│         │             │                 │         │         ├── CrawlingRequest.java
│         │             │                 │         │         ├── CrawlingResponse.java
│         │             │                 │         │         ├── FastcampusLectureDetailResponse.java
│         │             │                 │         │         ├── FastcampusLectureListResponse.java
│         │             │                 │         │         ├── FastcampusLectureResponse.java
│         │             │                 │         │         ├── FastcampusLectureUpdateResponse.java
│         │             │                 │         │         ├── InflearnLectureDetailResponse.java
│         │             │                 │         │         └── InflearnLectureListResponse.java
│         │             │                 │         └── service/
│         │             │                 │             ├── FastcampusLectureDetailCrawlingService.java
│         │             │                 │             ├── FastcampusLectureListCrawlingService.java
│         │             │                 │             ├── InflearnLectureDetailCrawlingService.java
│         │             │                 │             ├── InflearnLectureListCrawlingService.java
│         │             │                 │             ├── LectureDetailCrawlingService.java
│         │             │                 │             └── LectureListCrawlingService.java
│         │             │                 ├── lecture/
│         │             │                 │         ├── controller/
│         │             │                 │         │         ├── HomeController.java
│         │             │                 │         │         └── LectureController.java
│         │             │                 │         ├── dto/
│         │             │                 │         │         └── SearchRequest.java
│         │             │                 │         ├── entity/
│         │             │                 │         │         └── Lecture.java
│         │             │                 │         ├── repository/
│         │             │                 │         │         ├── LectureRepository.java
│         │             │                 │         │         ├── LectureRepositoryCustom.java
│         │             │                 │         │         └── LectureRepositoryCustomImpl.java
│         │             │                 │         └── service/
│         │             │                 │             └── LectureService.java
│         │             │                 ├── member/
│         │             │                 │         ├── controller/
│         │             │                 │         │         ├── FavorController.java
│         │             │                 │         │         ├── LoginController.java
│         │             │                 │         │         ├── MyPageController.java
│         │             │                 │         │         └── SignUpController.java
│         │             │                 │         ├── dto/
│         │             │                 │         │         ├── FavorRequest.java
│         │             │                 │         │         ├── FavorResponse.java
│         │             │                 │         │         └── MemberRegisterDto.java
│         │             │                 │         ├── entity/
│         │             │                 │         │         ├── Member.java
│         │             │                 │         │         └── MemberFavorLecture.java
│         │             │                 │         ├── repository/
│         │             │                 │         │         ├── MemberFavorRepository.java
│         │             │                 │         │         └── MemberRepository.java
│         │             │                 │         └── service/
│         │             │                 │             ├── MemberFavorService.java
│         │             │                 │             └── MemberService.java
│         │             │                 ├── report/
│         │             │                 │         ├── component/
│         │             │                 │         │         └── ReviewStatisticsScheduler.java
│         │             │                 │         ├── controller/
│         │             │                 │         │         ├── ReportController.java
│         │             │                 │         │         └── ReviewStatisticsController.java
│         │             │                 │         ├── dto/
│         │             │                 │         │         └── ReviewReportResponse.java
│         │             │                 │         ├── entity/
│         │             │                 │         │         └── Report.java
│         │             │                 │         └── repository/
│         │             │                 │             └── ReportRepository.java
│         │             │                 └── review/
│         │             │                     ├── controller/
│         │             │                     │         └── ReviewController.java
│         │             │                     ├── dto/
│         │             │                     │         ├── RemoveRequest.java
│         │             │                     │         ├── ReviewRequest.java
│         │             │                     │         └── ReviewResponse.java
│         │             │                     ├── entity/
│         │             │                     │         └── Review.java
│         │             │                     ├── repository/
│         │             │                     │         └── ReviewRepository.java
│         │             │                     └── service/
│         │             │                         └── ReviewService.java
│         │             └── kernel360/
│         └── resources/
│             ├── META-INF/
│             ├── application.yml
│             ├── log/
│             │         └── application.log
│             ├── logback-spring.xml
│             ├── static/
│             │         ├── css/
│             │         │         ├── home.css
│             │         │         ├── lecture/
│             │         │         │         ├── detail.css
│             │         │         │         └── list.css
│             │         │         ├── member/
│             │         │         │         └── review.css
│             │         │         ├── report/
│             │         │         │         └── report.css
│             │         │         └── signin.css
│             │         ├── images/
│             │         │         ├── banner1.png
│             │         │         ├── banner2.png
│             │         │         ├── banner3.png
│             │         │         ├── logo.png
│             │         │         ├── logo_big.png
│             │         │         ├── main1.png
│             │         │         └── mockup.png
│             │         └── js/
│             │             ├── crawler/
│             │             │         └── crawler.js
│             │             ├── home.js
│             │             ├── lecture/
│             │             │         ├── detail.js
│             │             │         └── list.js
│             │             ├── member/
│             │             │         ├── passwordMatch.js
│             │             │         └── review.js
│             │             └── report/
│             │                 └── report.js
│             ├── templates/
│             │         ├── crawler/
│             │         │         └── crawlerForm.html
│             │         ├── favor/
│             │         │         └── list.html
│             │         ├── fragments/
│             │         │         └── integration.html
│             │         ├── home.html
│             │         ├── layout/
│             │         │         └── defaultLayout.html
│             │         ├── lecture/
│             │         │         ├── detail.html
│             │         │         └── list.html
│             │         ├── member/
│             │         │         ├── login.html
│             │         │         ├── mypage.html
│             │         │         ├── review.html
│             │         │         └── signup.html
│             │         └── report/
│             │             └── report.html
│             └── url.properties
└── test/
          └── java/
              └── kr/
                  ├── kernel/
                  │         └── teachme/
                  │             ├── TeachmeApplicationTests.java
                  │             ├── common/
                  │             │         ├── config/
                  │             │         │         ├── PasswordEncoderConfigTest.java
                  │             │         │         └── SpringSecurityConfigTest.java
                  │             │         ├── exception/
                  │             │         │         └── AlreadyRegisteredMemberExceptionTest.java
                  │             │         ├── jwt/
                  │             │         │         ├── JwtAuthenticationFilterTest.java
                  │             │         │         └── JwtAuthorizationFilterTest.java
                  │             │         └── util/
                  │             │             ├── DateUtilTest.java
                  │             │             └── StringUtilTest.java
                  │             └── domain/
                  │                 ├── crawler/
                  │                 │         ├── component/
                  │                 │         │         ├── FastcampusAutoCrawlerTest.java
                  │                 │         │         └── InflearnAutoCrawlerTest.java
                  │                 │         └── controller/
                  │                 │             └── CrawlerControllerTest.java
                  │                 ├── lecture/
                  │                 │         ├── controller/
                  │                 │         │         ├── HomeControllerTest.java
                  │                 │         │         └── LectureControllerTest.java
                  │                 │         ├── dto/
                  │                 │         │         ├── PaginationResponseTest.java
                  │                 │         │         ├── PaginationTest.java
                  │                 │         │         └── SearchRequestTest.java
                  │                 │         ├── entity/
                  │                 │         │         └── LectureTest.java
                  │                 │         ├── repository/
                  │                 │         │         └── LecutreRepositoryCustomImplTest.java
                  │                 │         └── service/
                  │                 │             └── LectureServiceTest.java
                  │                 └── member/
                  │                     └── service/
                  │                         └── MemberServiceTest.java
                  └── kernel360/

```

## 프로젝트 기능

## 시연

## 가이드라인
