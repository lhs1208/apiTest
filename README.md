**⏰ File Scheduler & RESTful API Project**

이 프로젝트는 매일 자정 특정 경로의 파일을 읽어 데이터를 데이터베이스에 저장하는 스케줄러와, 

데이터를 조회 및 관리할 수 있는 RESTful API 서버를 구현한 프로젝트입니다.

**🛠 주요 기능**

1. 스케줄러 기능
- CSV 파일 데이터를 읽어 데이터베이스에 저장
- 파일 내 오류 발생 시 데이터 저장 방지
- 시간대별 데이터(가입자 수, 탈퇴자 수, 결제 금액 등) 관리

2. API 서버
- RESTful API로 데이터 조회, 등록, 수정, 삭제 가능
- 요청/응답 시간, 처리 시간 등 로그 기록
- 허용된 IP만 API 접근 가능 (설정 파일 기반)

3. 보안 및 호출 제한
- Client 인증으로 호출 신뢰성 검증
- Rate Limiting 기능으로 과도한 호출 방지

4. 유지보수 및 확장성
- 로그와 설정 파일을 기반으로 운영 및 확장성 보장


**🧪 테스트 방법**
- 프로젝트 실행
- Spring Boot 기반 프로젝트를 import 후 실행 (내장 톰캣 사용)


**📤 1. 데이터 업로드**
- POST 요청으로 파일 업로드
- 경로: http://localhost:8080/transactions/upload?filePath={CSV 파일 경로}
- 예시: http://localhost:8080/transactions/upload?filePath=D:/intelliJ/gitIntelli/apiTestdata/sample.csv


**🔍 2. 데이터 확인**
- GET 요청으로 업로드된 데이터 확인
- 경로: http://localhost:8080/transactions


**✏️ 3. 데이터 수정**
- PUT 요청으로 데이터를 수정
- 경로: http://localhost:8080/transactions/{id}
- 예시 데이터:
json

{
  "id": 1,
  "time": "2024-11-20 12:00",
  "subscribers": 40,
  "unsubscribers": 5,
  "paymentAmount": "50123123000",
  "usageAmount": "30012312300",
  "salesAmount": "100012312300"
}

**❌ 4. 데이터 삭제**
- DELETE 요청으로 데이터를 삭제
- 경로: http://localhost:8080/transactions/{id}
- 예시: http://localhost:8080/transactions/1

**⏳ 5. Rate Limiting 테스트**
- Postman으로 GET 요청 시 아래 스크립트를 추가:
- javascript
pm.test("Status code is 200 or 429", function () {
  pm.response.to.have.status.oneOf([200, 429]);
});

pm.test("Response time is within limit", function () {
  pm.expect(pm.response.responseTime).to.be.below(500); // 응답 시간 500ms 이하
});

- Runner에서 20개 이상의 요청을 실행해 동작 확인

**🔐 6. IP 방화벽 테스트**
- application.properties에서 지정된 IP를 다른 IP로 설정 후 테스트

**📋 7. 로그 확인**
- 프로젝트 실행 후 log 파일이 정상적으로 생성되는지 확인















