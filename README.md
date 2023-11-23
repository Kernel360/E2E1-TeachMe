<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/69861207/e4536173-ba9a-4c97-9a03-165a2e21ddbe" width="800"/>
</div>

<div align="center">
  
# Teach Me! 📚

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

## 프로젝트 과정 중 기술적 고민

- 개발 초기에 크롤링을 구현하면서 두 개의 사이트(인프런, 패스트캠퍼스)를 각자 구현을 진행하게 되었는데 어느 정도 개발이 
진행되고 나서 해당 비즈니스 로직의 메서드가 중복되는 것을 확인하였고, 인터페이스를 상속받는 형식으로 리팩토링하여 프로젝트
를 조금더 확장성 있는 구조로 개선하였다.

- 로깅 기능을 관점 단위로 분리하여 Spring AOP기술을 활용하여 비즈니스 로직을 분리하였다.

- 배포를 진행하면서 어느 환경에서든 효율적으로 동작할 수 있도록 WAS 와 DB를 Docker container화 하여 배포하였다.

- 배포 이후 운영을 진행하면서 발생하는 문제나 오류를 빠르게 처리할 수 있도록 AWS에서 제공하는 CloudWatch와 SNS, 
Lambda를 활용해 Slack으로 알림을 받아 빠르게 대처할 수 있는 모니터링 환경의 구현을 진행하였다.

## DB ERD

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/7dd7c9f6-9e8b-4b8b-8934-b7c8bf242f9f" width="800">
</div>

## API 명세서

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/8c899cd1-c35a-4afc-ad67-04719ed4f22c" width="800">

https://teach-me.live/swagger-ui/
</div>

## 시연

### 1. 홈

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/139b3938-57dd-465b-8bd7-a813cf113840" width="800">
</div>

### 2. 회원 가입

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/cfdf4f74-20c3-4295-a421-664e76201534" width="800">
</div>

### 3. 로그인

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/397aebcc-b36a-4709-a3aa-268841b9e955" width="800">
</div>

### 4. 리뷰 조회

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/be181434-f3a5-4f09-976d-631929e5c972" width="800">
</div>

### 5. 리뷰 작성

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/ff8bfe4a-748c-4a1b-916b-1b9cbaa71608" width="800">
</div>

### 6. 리뷰 삭제

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/3621d3a6-9435-4ce5-9f2f-c224f6c6602c" width="800">
</div>

### 7. 리뷰 수정

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/da549924-beac-47d0-8806-c4479a7fe6af" width="800">
</div>

### 8. 찜 하기

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/3786b111-a3e4-44f4-8cdf-559e80b2ad20" width="800">
</div>

### 9. 찜 조회
<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/563e0a77-28eb-44b3-afdc-0120fac11ab5" width="800">
</div>

### 10. 강의 리스트 조회

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/887d94d6-d7ab-4758-88c6-a647f6abc601" width="800">
</div>

### 11. 강의 상세 내용 조회

<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/901dd801-0895-4f56-91f7-4b6df474f3bf" width="800">
</div>

### 12. 크롤링 관리 페이지
<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/c4e6b420-4929-47ef-b846-c7e1abfd8aa7" width="800">
</div>

### 13. 통계 페이지 확인
<div align="center">
<img src="https://github.com/Kernel360/E2E1-TeachMe/assets/118032886/bf1a255f-1664-4f4f-9080-95852c33b7d2" width="800">
</div>
