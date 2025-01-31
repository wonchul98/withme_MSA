<div align="center">
<img src="https://github.com/user-attachments/assets/6689305d-a577-43de-99ea-671584e34883" width="1000"/>

  
### 개발자를 위한 AI 기반 문서 공동 작성 플랫폼 🖍️

[<img src="https://img.shields.io/badge/release-v0.0.0-ㅎㄱㄷ두?style=flat&logo=google-chrome&logoColor=white" />]() 
<br/> [<img src="https://img.shields.io/badge/프로젝트 기간-2024.10.14~2024.11.29-fab2ac?style=flat&logo=&logoColor=white" />]() <br/>
<img src="https://img.shields.io/badge/자율 프로젝트 우수상-FFD700?style=for-the-badge&logo=award&logoColor=white" alt="우수상">

</div> 


## 📝 목차
- [1. 프로젝트 개요](#🚀-프로젝트-개요)
- [2. 화면 구성](#🖥️-화면-구성)
- [3. 기술 스택](#⚙-기술-스택)
- [4. 인프라 설계](#🤔-인프라-설계)
- [5. 팀원](#💁‍♂️-프로젝트-팀원)
- [6. 배포 링크](#📲-링크)

다음과 같은 목차로 구성되어 있습니다.

<br />

## 🚀 프로젝트 개요
💡 **실시간 공동 문서 작성**
- 문서를 최대 10명의 사용자가 실시간으로 분업하여 작성
- liveBlocks 라이브러리를 사용
- 연동된 Git 레포지토리에 실시간으로 Commit 및 Push 기능 제공

💡 **AI 기반 문서초안 생성**
- 레포지토리 파일명을 기반으로 문서 초안을 자동 생성하여 정확성을 향상
- 레포지토리 파일명 탐색 시 비동기 처리와 멀티쓰레드 기술을 사용하여 기존 대비 15배 빠른 속도 제공
- SSE방법으로 AI 답변 응답 속도 16배 향상

💡 **문서 산출물 조회**
- Elastic Search를 사용하여 LIKE문 대비 10배 빠른 검색 성능 제공.
- 구글 검색 엔진 및 SEO 최적화를 통해 검색 효율성을 강화.

💡 **Github, GitLab 소셜로그인**
- Github 및 GitLab 소셜 로그인 제공

<br />



## 🖥️ 화면 구성
|Real-Time 협업 문서 작성과 간단한 AI 문서 생성|
|:---:|
|<img src="https://github.com/user-attachments/assets/92c608c8-563e-46a9-b452-6b5df406c15e" width="450"/>|
|LiveBlocks 라이브러리를 활용한 Real-Time 협업 문서 작성|


|AI 기반 문서 초안 작성|
|:---:|
|<img src="https://github.com/user-attachments/assets/886c1dda-d85c-4135-8c95-dd7e3e4e104a" width="450"/>|
|레포지토리 파일명을 기반으로 정확성 향상| 

|Editor 화면 구성|
|:---:|
|<img src="https://github.com/user-attachments/assets/fc1dc17f-758c-4c66-a085-2a35f1539e52" width="450"/>|
|LiveBlocks 라이브러리를 활용한 분업 제공|

|GitHub/GitLab OAuth로 로그인 및 Repository 연동|
|:---:|
|<img src="https://github.com/user-attachments/assets/e11a3dc6-725e-4e35-a488-7dad5b8251a9" width="450"/>|
|Github, GitLab API 사용|

|Elastic Search를 활용해 본문 내용 검색|
|:---:|
|<img src="https://github.com/user-attachments/assets/df4f600d-ff7c-4e5b-ab7b-fbacc95f7295" width="450"/>|
|Elastic Search로 LIKE 속도 개선|

|구글 검색 엔진 노출|
|:---:|
|<img src="https://github.com/user-attachments/assets/f095304e-192b-4430-a1d1-9fb0cb866a78" width="450"/>|
|SEO 최적화를 통한 구글 검색 엔진 노출|
<br />

## ⚙ 기술 스택
### Backend
<div>
<img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> 
<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white">
<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
<img src="https://img.shields.io/badge/AWS-%23232F3E.svg?style=for-the-badge&logo=amazonaws&logoColor=white">
</div>

### Frontend
<div>
<img src="https://img.shields.io/badge/Next.js-%23000000.svg?style=for-the-badge&logo=nextdotjs&logoColor=white">
<img src="https://img.shields.io/badge/React-%2361DAFB.svg?style=for-the-badge&logo=react&logoColor=white">
<img src="https://img.shields.io/badge/React Query-FF4154?style=for-the-badge&logo=react-query&logoColor=white">
<img src="https://img.shields.io/badge/Tailwind CSS-%2306B6D4.svg?style=for-the-badge&logo=tailwind-css&logoColor=white">
<img src="https://img.shields.io/badge/TypeScript-%233178C6.svg?style=for-the-badge&logo=typescript&logoColor=white">
</div>

### Tools
<div>
<img src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jira&logoColor=white">
<img src="https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/Git-%23F05032.svg?style=for-the-badge&logo=git&logoColor=white">
</div>

<br />

## 🤔 인프라 설계
<img src="https://github.com/user-attachments/assets/75e8a9ae-87bf-48d8-90c8-6bc922fedda1" width="450"/> <br/>
- EC2 비용 문제로 인해, **프론트엔드, 백엔드, DB, Elasticsearch** 서버를 하나의 EC2 인스턴스에 모두 설치.  
- 하나의 서버에서 에러가 발생하더라도 다른 서비스에 영향을 미치지 않도록, 모든 서비스를 **Docker** 환경으로 분리하여 실행.
- **GitHub Actions**를 사용하여 **CI/CD** 파이프라인을 구축. [코드 바로보기](https://github.com/qjatjs123123/withMe/blob/master/.github/workflows/docker-image.yml#L1-L113)
<br />


<br />

## 💁‍♂️ 프로젝트 팀원
| **Frontend** | **Frontend** | **Frontend** | **Backend** | **Backend** | **Backend** |
|:---:|:---:|:---:|:---:|:---:|:---:|
| ![](https://github.com/qjatjs123123.png?width=120&height=120) | ![](https://github.com/gyeongmann.png??width=120&height=120) | ![](https://github.com/Jaeyoung9999.png??width=120&height=120) | ![](https://github.com/DKL1231.png??width=120&height=120) | ![](https://github.com/taegun1011.png??width=120&height=120) | ![](https://github.com/wonchul98.png??width=120&height=120) |
| [홍범선](https://github.com/qjatjs123123) | [현경찬](https://github.com/gyeongmann) | [이재영](https://github.com/Jaeyoung9999) | [이동규](https://github.com/DKL1231) | [황태건](https://github.com/taegun1011) | [신원철](https://github.com/wonchul98) |



## 📲 링크
| :: 배포                                                            |
| :------------------------------------------------------------------------------------- |
| :: [withMe Link](https://www.withme.my/) | 



