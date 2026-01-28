# LivePortrait Worker

AI 비디오 생성을 위한 Python 워커 서비스입니다. RabbitMQ에서 이벤트를 소비하고 LivePortrait를 사용하여 비디오를 생성합니다.

## 왜 별도 워커인가?

- **MPS 가속**: Docker 내부에서는 macOS GPU 가속(MPS)을 사용할 수 없음
- **의존성 관리**: LivePortrait는 복잡한 Python 의존성을 가지고 있어 conda 환경으로 관리하는 것이 효율적
- **아키텍처 분리**: Spring Boot 앱과 AI 처리를 분리하여 각각 최적의 환경에서 실행

## 사전 요구사항

1. **LivePortrait 설치**
   ```bash
   git clone https://github.com/KwaiVGI/LivePortrait.git
   cd LivePortrait
   conda create -n LivePortrait python=3.10
   conda activate LivePortrait
   pip install -r requirements.txt
   ```

2. **모델 다운로드**
   LivePortrait README의 지침에 따라 모델 파일 다운로드

## 설치

```bash
cd apps/backend/services/liveportrait-worker

# 의존성 설치 (LivePortrait conda 환경에서)
conda activate LivePortrait
pip install -r requirements.txt
```

## 설정

`.env` 파일 생성:
```bash
cp .env.example .env
# 필요에 따라 값 수정
```

주요 설정:
- `LIVEPORTRAIT_PATH`: LivePortrait 프로젝트 경로
- `CONDA_ENV`: conda 환경 이름
- RabbitMQ 연결 정보
- 데이터베이스 연결 정보
- AWS S3 인증 정보

## 실행

```bash
# LivePortrait conda 환경 활성화
conda activate LivePortrait

# 워커 실행
python main.py
```

## 아키텍처

```
lifepuzzle-api (Spring Boot)
    │
    │ 이벤트 발행: gallery.ai.video.create
    ↓
RabbitMQ
    │
    │ 이벤트 소비
    ↓
liveportrait-worker (Python)
    │
    ├─ DB에서 PENDING 레코드 조회
    ├─ S3에서 이미지/비디오 다운로드
    ├─ LivePortrait 실행 (MPS 가속)
    ├─ 결과 S3 업로드
    └─ DB 상태 업데이트 (COMPLETED/FAILED)
```

## 이벤트 형식

```json
{
  "heroId": 123,
  "galleryId": 456,
  "drivingVideoId": 789
}
```

## 로그

```bash
# 실시간 로그 확인
python main.py 2>&1 | tee worker.log
```

## 트러블슈팅

### RabbitMQ 연결 실패
- RabbitMQ가 실행 중인지 확인
- VHOST 설정 확인 (`lifepuzzle`)
- 큐 이름 확인 (`gallery.ai.video.create.ai-video-generator`)

### LivePortrait 실행 실패
- conda 환경이 올바르게 활성화되어 있는지 확인
- 모델 파일이 다운로드되어 있는지 확인
- MPS 지원 확인: `python -c "import torch; print(torch.backends.mps.is_available())"`

### 메모리 부족
- 다른 GPU 집약적 앱 종료
- `PYTORCH_ENABLE_MPS_FALLBACK=1` 설정 확인
