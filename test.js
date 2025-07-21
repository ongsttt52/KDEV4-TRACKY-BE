// 150 vu

import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend } from 'k6/metrics';

// 사용자 정의 메트릭 추가: API 응답 시간을 추적
const cycleApiTrend = new Trend('cycle_api_duration');

// 테스트 옵션: 150명의 가상 사용자가 10분 동안 테스트를 실행
export const options = {
  vus: 39,
  duration: '1s',
  thresholds: {
    // 요청 실패율은 1% 미만이어야 함
    'http_req_failed': ['rate<0.01'],
  },
};

// setup 함수: 테스트 시작 전 한번만 실행되어 테스트 데이터를 준비합니다.
export function setup() {
    console.log('--- 테스트 데이터 생성 시작 ---');
    const baseTime = '202504301530'; // 기준 시간
    const cList = [];

    // cList 배열 안의 oTime을 ...00초부터 ...59초까지 동적으로 생성
    for (let i = 0; i < 60; i++) {
        const seconds = String(i).padStart(2, '0'); // 0 -> '00', 9 -> '09'
        cList.push({
            "gcd": "A",
            "lat": 37387513 + i,
            "lon": 126978386 + i,
            "ang": (6 * i) % 360,
            "spd": 61 + i,
            "sum": 50.0,
            "oTime": `${baseTime}${seconds}`
        });
    }

    const payloadTemplate = {
        // 1. mdn을 '00000000000'으로 고정
        "mdn": "00000000000",
        "tid": "A001",
        "mid": "6",
        "pv": "5",
        "did": "1",
        "oTime": baseTime,
        "cCnt": 60,
        "cList": cList
    };

    console.log('--- 테스트 데이터 생성 완료 ---');
    // 생성된 데이터를 VUs에 전달
    return { payloadTemplate: payloadTemplate };
}


// 각 가상 사용자(VU)가 실행할 메인 함수
// setup에서 반환된 데이터가 'data' 파라미터로 전달됩니다.
export default function (data) {
  const baseUrl = 'http://hub:8082'; // 실제 테스트 대상 URL
  const headers = { 'Content-Type': 'application/json' };

  // setup에서 만든 완전한 페이로드를 사용
  const payload = data.payloadTemplate;

  // --- 주기정보 API 호출 ---
  const res = http.post(`${baseUrl}/hub/car/cycle`, JSON.stringify(payload), { headers: headers });

  // --- 2. 변경된 응답 구조에 맞게 check 조건 수정 ---
  check(res, {
    '응답 코드 확인 (rstCd 000)': (r) => {
        try {
            return r.json('rstCd') === '000';
        } catch (e) {
            console.error(`JSON 파싱 실패: ${r.body}`);
            return false;
        }
    }
  });

  // 사용자 정의 메트릭에 API 응답 시간 기록
  cycleApiTrend.add(res.timings.duration);

  // 1명의 사용자가 1초에 한번씩 주기정보를 보내는 시나리오
  sleep(1);
}