# Market
#### 온라인쇼핑몰을 구현해본다.

<hr/>

<div align="center">
  <a href="https://youtu.be/pr5xL9qq5-w"><img src="https://github.com/kimnoin/Market/blob/master/project/front-end/assets/image.jpg" alt="img"></a>
</div>


- 이미지를 클릭시 구현영상으로 이동합니다.


## 프로젝트의 목적
``` bash
여러 기능들을 공부하기 위해 시작하는 프로젝트

```

## 사용하고 싶은 기능
```bash

- 웹소켓을 이용하여 관리자와 채팅
- 파일업로드/다운기능 
- 스프링 시큐리티
- Oauth2
- 차트 라이브러리
- 검색기능
- 메일보내기

```

## 개발환경
``` bash
- JetBrains WebStorm
- IntelliJ ultimate
```

## 사용환경
``` bash
·Front end

Vue.js


·Back end

Spring

```

<hr/>


## 구현기능 설명
```bash
- 회원기능: 로그인/로그아웃,회원등록,회원수정,회원조회
- 상품기능: 상품등록,상품수정,상품조회,상품삭제
- 상품문의/답변: 문의작성,문의답변,문의조회,문의삭제
- 주문기능:상품주문,주문내역조회
- 상품후기:후기작성,후기조회
- 채팅기능,메일보내기,차트 라이브러리
- 기타

- 데이터모델링 및 요청 URL 설명및 자세한 설명은 첨부 ppt 에서 확인

```

<hr/>

## 데이터베이스 스키마

<img src="https://github.com/kimnoin/Market/blob/master/project/front-end/assets/schema.png"/>

## 사용하고자 하는 기능 현황

#### ① 스프링시큐리티 & Oauth2
- UserDetailsService 구현
- PasswordEncoder 사용하여 비밀번호 저장
- 비 로그인자의 특정페이지의 접근 차단
- 로그인유저의 권한에 따른 특정 페이지의 접근가능여부 기능
- Auth & Resource 서버 구성
- grantType 으로 인증요청
- 인증성공시 AccessToken 발급

#### ② 파일업로드/다운로드
- vue.js와 spring boot 간의 파일 업로드/다운로드 처리
- 다운로드처리를 이용해 이미지 전달

#### ③ 검색기능
- 각 관리자 페이지의 검색폼 구현
- 각 관리자 페이지에서 검색 결과에 따른 결과 출력

#### ④ 차트 라이브러리
- D3.js 사용하여 1주일 매출현황 Bar Chart로 표현

#### ⑤ 웹소켓을 이용하여 관리자및 회원간 채팅
- WebSocket + Sock.js + Stomp 를 사용

#### ⑥ 메일보내기
- spring-boot-starter-mail 를 사용

## 기타
```bash
- 이미지는 Pixabay의 무료 이미지와 구글 머테리얼 아이콘사용
- 구현영상의 음원은 프리음원을 사용하여 라이센스 명시
- vue.js 에서 사용한 오픈소스는 vue.js README 파일에 명시

```

